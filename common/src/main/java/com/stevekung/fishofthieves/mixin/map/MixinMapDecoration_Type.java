package com.stevekung.fishofthieves.mixin.map;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.registry.FOTMapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapDecoration;

@Mixin(value = MapDecoration.Type.class, priority = 10000)
public class MixinMapDecoration_Type
{
    @Shadow
    @Mutable
    @Final
    static MapDecoration.Type[] $VALUES;

    @SuppressWarnings({ "unused", "SameParameterValue" })
    @Invoker("<init>")
    static MapDecoration.Type fishofthieves$create(String name, int ordinal, boolean renderedOnFrame, boolean trackCount)
    {
        throw new IllegalStateException("Unreachable");
    }

    static
    {
        var entry = fishofthieves$create("FISHOFTHIEVES_TREASURED_FISH", $VALUES.length, true, false);
        $VALUES = ArrayUtils.add($VALUES, entry);

        FishOfThieves.LOGGER.info("Added new enum to {}: {}", MapDecoration.Type.class, FOTMapDecorationTypes.TREASURED_FISH);
    }
}