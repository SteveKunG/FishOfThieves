package com.stevekung.fishofthieves.fabric.mixin.datafix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.datafixers.DSL;
import com.stevekung.fishofthieves.registry.FOTTypeReferences;

import net.minecraft.util.datafix.DataFixTypes;

@Mixin(DataFixTypes.class)
public enum MixinDataFixTypes
{
    FISHOFTHIEVES_SAVED_BAIT_PRESERVE(FOTTypeReferences.SAVED_BAIT_PRESERVE);

    @Shadow
    MixinDataFixTypes(DSL.TypeReference type) {}
}