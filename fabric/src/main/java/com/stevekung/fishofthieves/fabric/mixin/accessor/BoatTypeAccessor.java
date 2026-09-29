package com.stevekung.fishofthieves.fabric.mixin.accessor;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;

@Mixin(Boat.Type.class)
public interface BoatTypeAccessor
{
    @Accessor("planks")
    @Mutable
    @Final
    void fishofthieves$setPlanks(Block planks);
}