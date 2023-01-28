package com.stevekung.fishofthieves.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.registry.FOTStructures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;

@Mixin(StructureSets.class)
public interface MixinStructureSets
{
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void fishofthieves$bootstrap(BootstapContext<StructureSet> context, CallbackInfo info)
    {
        FOTStructures.Sets.bootstrap(context);
    }
}