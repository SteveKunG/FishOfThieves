package com.stevekung.fishofthieves.mixin.level.levelgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;
import net.minecraft.world.level.levelgen.material.MaterialSystem;

@Mixin(MaterialSystem.class)
public class MixinMaterialSystem
{
    @ModifyVariable(method = { "buildSurface", "topMaterial" }, at = @At("STORE"))
    private MaterialRuleContext fishofthieves$setChunkAccess(MaterialRuleContext context, @Local(argsOnly = true) ChunkAccess chunkAccess)
    {
        context.setChunkAccess(chunkAccess);
        return context;
    }
}