package org.topiinho.itensclumps;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClumpItemEntity extends ItemEntity {
    public ClumpItemEntity(EntityType<? extends ItemEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        ItemStack stack = getItem();
        int realCount = stack.getCount();
        stack.setCount(Math.min(realCount, 99));

        try {
            super.addAdditionalSaveData(tag);
        } finally {
            stack.setCount(realCount);
        }

        tag.putInt("ClumpCount", realCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ClumpCount")) {
            setItem(getItem().copyWithCount(tag.getInt("ClumpCount")));
        }
    }
}
