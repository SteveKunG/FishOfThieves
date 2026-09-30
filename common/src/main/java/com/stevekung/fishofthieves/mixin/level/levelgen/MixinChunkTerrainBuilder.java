package com.stevekung.fishofthieves.mixin.level.levelgen;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.levelgen.ChunkAccessExtender;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.ChunkTerrainBuilder;
import net.minecraft.world.level.levelgen.material.MaterialRuleContext;

@Mixin(ChunkTerrainBuilder.class)
public class MixinChunkTerrainBuilder implements ChunkAccessExtender
{
    @Shadow
    @Final
    MaterialRuleContext ruleContext;

    @Unique
    private ChunkAccess chunkAccess;

    @Inject(method = "<init>*",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/levelgen/material/rule/MaterialRule.compile(Lnet/minecraft/world/level/levelgen/material/MaterialRuleContext;)Lnet/minecraft/world/level/levelgen/material/rule/RuleEvaluator;"))
    private void fishofthieves$setChunkAccess(CallbackInfo info)
    {
        this.ruleContext.setChunkAccess(this.chunkAccess);
    }

    @Override
    public void setChunkAccess(ChunkAccess chunkAccess)
    {
        this.chunkAccess = chunkAccess;
    }
}