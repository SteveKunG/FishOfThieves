package com.stevekung.fishofthieves.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;

@Mixin(DispenserBlock.class)
public interface DispenserBlockAccessor
{
    @Accessor("DISPENSER_REGISTRY")
    static Map<Item, DispenseItemBehavior> getDispenserRegistry()
    {
        throw new AssertionError();
    }
}