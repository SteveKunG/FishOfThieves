package com.stevekung.fishofthieves.neoforge.mixin.datafix;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.datafixers.DSL;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDataFixTypes;
import com.stevekung.fishofthieves.registry.FOTTypeReferences;

import net.minecraft.util.datafix.DataFixTypes;

@Mixin(value = DataFixTypes.class, priority = 10000)
public class MixinDataFixTypes
{
    @Shadow
    @Mutable
    @Final
    static DataFixTypes[] $VALUES;

    @SuppressWarnings({ "unused", "SameParameterValue" })
    @Invoker("<init>")
    static DataFixTypes fishofthieves$create(String name, int ordinal, DSL.TypeReference type)
    {
        throw new IllegalStateException("Unreachable");
    }

    static
    {
        var entry = fishofthieves$create("FISHOFTHIEVES_SAVED_BAIT_PRESERVE", $VALUES.length, FOTTypeReferences.SAVED_BAIT_PRESERVE);
        $VALUES = ArrayUtils.add($VALUES, entry);

        FishOfThieves.LOGGER.info("Added new enum to {}: {}", DataFixTypes.class, FOTDataFixTypes.SAVED_BAIT_PRESERVE);
    }
}