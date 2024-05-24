package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.registry.variant.*;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import com.stevekung.fishofthieves.registry.variant.SplashtailVariants;
import com.stevekung.fishofthieves.entity.condition.SpawnConditionType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTBuiltInRegistries
{
    public static final Registry<SplashtailVariant> SPLASHTAIL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.SPLASHTAIL_VARIANT, registry -> SplashtailVariants.RUBY);
    public static final Registry<PondieVariant> PONDIE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PONDIE_VARIANT, registry -> PondieVariants.CHARCOAL);
    public static final Registry<IslehopperVariant> ISLEHOPPER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ISLEHOPPER_VARIANT, registry -> IslehopperVariants.STONE);
    public static final Registry<AncientscaleVariant> ANCIENTSCALE_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.ANCIENTSCALE_VARIANT, registry -> AncientscaleVariants.ALMOND);
    public static final Registry<PlentifinVariant> PLENTIFIN_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.PLENTIFIN_VARIANT, registry -> PlentifinVariants.OLIVE);
    public static final Registry<WildsplashVariant> WILDSPLASH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WILDSPLASH_VARIANT, registry -> WildsplashVariants.RUSSET);
    public static final Registry<DevilfishVariant> DEVILFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.DEVILFISH_VARIANT, registry -> DevilfishVariants.ASHEN);
    public static final Registry<BattlegillVariant> BATTLEGILL_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.BATTLEGILL_VARIANT, registry -> BattlegillVariants.JADE);
    public static final Registry<WreckerVariant> WRECKER_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.WRECKER_VARIANT, registry -> WreckerVariants.ROSE);
    public static final Registry<StormfishVariant> STORMFISH_VARIANT = BuiltInRegistries.registerSimple(FOTRegistries.STORMFISH_VARIANT, registry -> StormfishVariants.ANCIENT);

    public static final Registry<SpawnConditionType> SPAWN_CONDITION_TYPE = BuiltInRegistries.registerSimple(FOTRegistries.SPAWN_CONDITION_TYPE, registry -> FOTSpawnConditions.ANY_OF);

    public static void forge()
    {
        IslehopperVariants.init();
        AncientscaleVariants.init();
        PlentifinVariants.init();
        WildsplashVariants.init();
        DevilfishVariants.init();
        BattlegillVariants.init();
        WreckerVariants.init();
        StormfishVariants.init();
    }
}