package com.stevekung.fishofthieves.registry;

import java.util.function.UnaryOperator;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.*;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;

public class FOTDataComponentTypes
{
    public static final DataComponentType<Holder<SplashtailVariant>> SPLASHTAIL_VARIANT = register("splashtail/variant", builder -> builder.persistent(SplashtailVariant.CODEC).networkSynchronized(SplashtailVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<PondieVariant>> PONDIE_VARIANT = register("pondie/variant", builder -> builder.persistent(PondieVariant.CODEC).networkSynchronized(PondieVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<IslehopperVariant>> ISLEHOPPER_VARIANT = register("islehopper/variant", builder -> builder.persistent(IslehopperVariant.CODEC).networkSynchronized(IslehopperVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<AncientscaleVariant>> ANCIENTSCALE_VARIANT = register("ancientscale/variant", builder -> builder.persistent(AncientscaleVariant.CODEC).networkSynchronized(AncientscaleVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<PlentifinVariant>> PLENTIFIN_VARIANT = register("plentifin/variant", builder -> builder.persistent(PlentifinVariant.CODEC).networkSynchronized(PlentifinVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<WildsplashVariant>> WILDSPLASH_VARIANT = register("wildsplash/variant", builder -> builder.persistent(WildsplashVariant.CODEC).networkSynchronized(WildsplashVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<DevilfishVariant>> DEVILFISH_VARIANT = register("devilfish/variant", builder -> builder.persistent(DevilfishVariant.CODEC).networkSynchronized(DevilfishVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<BattlegillVariant>> BATTLEGILL_VARIANT = register("battlegill/variant", builder -> builder.persistent(BattlegillVariant.CODEC).networkSynchronized(BattlegillVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<WreckerVariant>> WRECKER_VARIANT = register("wrecker/variant", builder -> builder.persistent(WreckerVariant.CODEC).networkSynchronized(WreckerVariant.STREAM_CODEC));
    public static final DataComponentType<Holder<StormfishVariant>> STORMFISH_VARIANT = register("stormfish/variant", builder -> builder.persistent(StormfishVariant.CODEC).networkSynchronized(StormfishVariant.STREAM_CODEC));
    public static final DataComponentType<Integer> TREASURED_FISH_MAP_COST = register("treasured_fish_map_cost", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Data Component Type");
    }

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder)
    {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, FishOfThieves.id(name), builder.apply(DataComponentType.builder()).build());
    }
}