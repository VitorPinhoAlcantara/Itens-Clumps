package org.topiinho.itensclumps;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ItensClumps.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<ClumpItemEntity>> CLUMP_ENTITY = ENTITY_TYPES.register(
            "clump_item", () -> EntityType.Builder.<ClumpItemEntity>of(ClumpItemEntity::new, MobCategory.MISC)
                                        .sized(EntityType.ITEM.getWidth(), EntityType.ITEM.getHeight())
                                        .eyeHeight(EntityType.ITEM.getDimensions().eyeHeight())
                                        .clientTrackingRange(EntityType.ITEM.clientTrackingRange())
                                        .updateInterval(EntityType.ITEM.updateInterval())
                                        .build("clump_item"));
}
