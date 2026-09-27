package org.topiinho.itensclumps;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ClumpItemEntity extends ItemEntity {
    public ClumpItemEntity(EntityType<? extends ItemEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        ItemStack stack = getItem();
        int realCount = stack.getCount();
        stack.setCount(Math.min(realCount, 99));

        try {
            super.addAdditionalSaveData(output);
        } finally {
            stack.setCount(realCount);
        }

        output.putInt("ClumpCount", realCount);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.getInt("ClumpCount").ifPresent(count -> setItem(getItem().copyWithCount(count)));
    }
}
