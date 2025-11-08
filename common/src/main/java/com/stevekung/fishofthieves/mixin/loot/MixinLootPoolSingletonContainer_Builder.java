package com.stevekung.fishofthieves.mixin.loot;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.stevekung.fishofthieves.loot.function.LootPoolSingletonContainerWeightAdder;

import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;

@Mixin(LootPoolSingletonContainer.Builder.class)
public class MixinLootPoolSingletonContainer_Builder implements LootPoolSingletonContainerWeightAdder
{
    @Shadow
    int weight;

    @Override
    public void fishofthieves$addWeight(int weight)
    {
        this.weight += weight;
    }
}