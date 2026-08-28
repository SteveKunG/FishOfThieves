package com.stevekung.fishofthieves.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.levelgen.material.MaterialRuleContext;

@Mixin(MaterialRuleContext.class)
public interface MaterialRuleContextAccessor
{
    @Accessor
    int getBlockX();

    @Accessor
    int getBlockY();

    @Accessor
    int getBlockZ();
}