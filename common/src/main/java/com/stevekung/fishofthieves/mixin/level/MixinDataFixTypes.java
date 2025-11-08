package com.stevekung.fishofthieves.mixin.level;

import org.apache.commons.lang3.ArrayUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.datafixers.DSL;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTDataFixTypes;
import com.stevekung.fishofthieves.registry.FOTTypeReferences;

import net.minecraft.util.datafix.DataFixTypes;

@Mixin(DataFixTypes.class)
public class MixinDataFixTypes
{
    @Shadow
    @Mutable
    @Final
    static DataFixTypes[] $VALUES;

    @Invoker(value = "<init>")
    static DataFixTypes fishofthieves$create(String name, int ordinal, DSL.TypeReference type)
    {
        throw new IllegalStateException("Unreachable");
    }

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            target = "net/minecraft/util/datafix/DataFixTypes.$VALUES:[Lnet/minecraft/util/datafix/DataFixTypes;",
            shift = At.Shift.AFTER,
            opcode = Opcodes.PUTSTATIC,
            ordinal = 0
    ))
    private static void fishofthieves$clinit(CallbackInfo info)
    {
        var entry = fishofthieves$create("FISHOFTHIEVES_SAVED_BAIT_PRESERVE", $VALUES.length, FOTTypeReferences.SAVED_BAIT_PRESERVE);
        $VALUES = ArrayUtils.add($VALUES, entry);

        FishOfThieves.LOGGER.info("Added new enum to {}: {}", DataFixTypes.class, FOTDataFixTypes.SAVED_BAIT_PRESERVE);
    }
}