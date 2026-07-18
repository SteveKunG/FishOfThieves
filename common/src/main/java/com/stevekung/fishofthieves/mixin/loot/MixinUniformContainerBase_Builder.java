package com.stevekung.fishofthieves.mixin.loot;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.loot.function.UniformContainerBaseWeightAdder;

import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;

@Mixin(UniformContainerBase.Builder.class)
public class MixinUniformContainerBase_Builder implements UniformContainerBaseWeightAdder
{
    @Shadow
    int weight;

    @Override
    public void fishofthieves$addWeight(int weight)
    {
        this.weight += weight;
    }
}