package com.stevekung.fishofthieves.mixin.worldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.registry.FOTStructures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.world.level.levelgen.structure.Structure;

@Mixin(Structures.class)
public class MixinStructures
{
    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void fishofthieves$bootstrap(BootstapContext<Structure> context, CallbackInfo info)
    {
        FOTStructures.bootstrap(context);
    }
}