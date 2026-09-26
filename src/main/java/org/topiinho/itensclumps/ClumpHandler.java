package org.topiinho.itensclumps;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = ItensClumps.MODID)
public class ClumpHandler {

    private static final int LIFETIME = 6000;
    private static final int INFINITE_PICKUP_DELAY = 32767;
    private static final int INFINITE_LIFETIME = -32768;
    private static final int MERGE_INTERVAL = 20;
    private static final double MERGE_RADIUS = 2.0;

    @SubscribeEvent
    public static void onItemJoinLevel(EntityJoinLevelEvent event) {
        if (event.loadedFromDisk() || event.getLevel().isClientSide()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemEntity newItemEntity) || newItemEntity.getType() != EntityType.ITEM || !canClump(newItemEntity)) {
            return;
        }

        ItemEntity existing = findMergeCandidate(newItemEntity);
        if (existing == null) {
            return;
        }

        mergeInto(existing, newItemEntity);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity entity) || entity.level().isClientSide()) {
            return;
        }

        if (!isClumpable(entity) || !canClump(entity)) {
            return;
        }

        if ((entity.tickCount + entity.getId()) % MERGE_INTERVAL != 0) {
            return;
        }

        ItemEntity other = findMergeCandidate(entity);
        if (other == null) {
            return;
        }

        ItemEntity target = entity.getItem().getCount() >= other.getItem().getCount() ? entity : other;
        ItemEntity source = target == entity ? other : entity;

        mergeInto(target, source);
        source.discard();
    }

    private static ItemEntity findMergeCandidate(ItemEntity entity) {
        List<ItemEntity> nearby = entity.level().getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(MERGE_RADIUS));

        for (ItemEntity other : nearby) {
            if (other != entity && canMerge(entity, other)) {
                return other;
            }
        }

        return null;
    }

    private static boolean canMerge(ItemEntity entity, ItemEntity other) {
        if (!isClumpable(other) || !canClump(other)) {
            return false;
        }

        if (!Objects.equals(entity.target, other.target)) {
            return false;
        }

        ItemStack stack = entity.getItem();
        ItemStack otherStack = other.getItem();
        if (!ItemStack.isSameItemSameComponents(stack, otherStack)) {
            return false;
        }

        return (long) stack.getCount() + otherStack.getCount() <= Integer.MAX_VALUE;
    }

    private static void mergeInto(ItemEntity target, ItemEntity source) {
        ItemStack targetStack = target.getItem();
        int total = targetStack.getCount() + source.getItem().getCount();
        ItemStack mergedStack = targetStack.copyWithCount(total);
        int pickupDelay = Math.max(target.pickupDelay, source.pickupDelay);
        int age = Math.min(target.age, source.age);

        if (target instanceof ClumpItemEntity || total <= targetStack.getMaxStackSize()) {
            target.setItem(mergedStack);
            target.pickupDelay = pickupDelay;
            target.age = age;
            return;
        }

        Level level = target.level();
        ClumpItemEntity clump = new ClumpItemEntity(ModEntityTypes.CLUMP_ENTITY.get(), level);
        clump.setPos(target.position());
        clump.setDeltaMovement(target.getDeltaMovement());
        clump.setItem(mergedStack);
        clump.setTarget(target.target);
        clump.pickupDelay = pickupDelay;
        clump.age = age;
        target.discard();
        level.addFreshEntity(clump);
    }

    private static boolean isClumpable(ItemEntity entity) {
        return entity.getType() == EntityType.ITEM || entity.getType() == ModEntityTypes.CLUMP_ENTITY.get();
    }

    private static boolean canClump(ItemEntity entity) {
        return entity.isAlive()
                && entity.pickupDelay != INFINITE_PICKUP_DELAY
                && entity.age != INFINITE_LIFETIME
                && entity.age < LIFETIME;
    }
}
