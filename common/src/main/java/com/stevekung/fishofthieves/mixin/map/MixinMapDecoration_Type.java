package com.stevekung.fishofthieves.mixin.map;

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

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;

import net.minecraft.world.level.saveddata.maps.MapDecoration;

@Mixin(MapDecoration.Type.class)
public class MixinMapDecoration_Type
{
    @Shadow
    @Mutable
    @Final
    static MapDecoration.Type[] $VALUES;

    @SuppressWarnings({ "unused", "SameParameterValue" })
    @Invoker(value = "<init>")
    private static MapDecoration.Type fishofthieves$create(String name, int ordinal, boolean renderedOnFrame, boolean trackCount)
    {
        throw new IllegalStateException("Unreachable");
    }

    @Inject(method = "<clinit>", at = @At(
            value = "FIELD",
            target = "net/minecraft/world/level/saveddata/maps/MapDecoration$Type.$VALUES:[Lnet/minecraft/world/level/saveddata/maps/MapDecoration$Type;",
            shift = At.Shift.AFTER,
            opcode = Opcodes.PUTSTATIC,
            ordinal = 0
    ))
    private static void fishofthieves$clinit(CallbackInfo info)
    {
        var entry = fishofthieves$create("FISHOFTHIEVES_TREASURED_FISH", $VALUES.length, true, false);
        $VALUES = ArrayUtils.add($VALUES, entry);

        FishOfThieves.LOGGER.info("Added new enum to {}: {}", MapDecoration.Type.class, FOTMapDecorationTypes.TREASURED_FISH);
    }
}