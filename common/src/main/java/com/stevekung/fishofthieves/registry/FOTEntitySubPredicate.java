package com.stevekung.fishofthieves.registry;

import java.util.Optional;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTEntitySubPredicate
{
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<SplashtailVariant> SPLASHTAIL = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.SPLASHTAIL_VARIANT, entity -> entity instanceof Splashtail splashtail ? Optional.of(splashtail.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<PondieVariant> PONDIE = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.PONDIE_VARIANT, entity -> entity instanceof Pondie pondie ? Optional.of(pondie.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<IslehopperVariant> ISLEHOPPER = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.ISLEHOPPER_VARIANT, entity -> entity instanceof Islehopper islehopper ? Optional.of(islehopper.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<AncientscaleVariant> ANCIENTSCALE = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.ANCIENTSCALE_VARIANT, entity -> entity instanceof Ancientscale ancientscale ? Optional.of(ancientscale.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<PlentifinVariant> PLENTIFIN = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.PLENTIFIN_VARIANT, entity -> entity instanceof Plentifin plentifin ? Optional.of(plentifin.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<WildsplashVariant> WILDSPLASH = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.WILDSPLASH_VARIANT, entity -> entity instanceof Wildsplash wildsplash ? Optional.of(wildsplash.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<DevilfishVariant> DEVILFISH = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.DEVILFISH_VARIANT, entity -> entity instanceof Devilfish devilfish ? Optional.of(devilfish.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<BattlegillVariant> BATTLEGILL = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.BATTLEGILL_VARIANT, entity -> entity instanceof Battlegill battlegill ? Optional.of(battlegill.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<WreckerVariant> WRECKER = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.WRECKER_VARIANT, entity -> entity instanceof Wrecker wrecker ? Optional.of(wrecker.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<StormfishVariant> STORMFISH = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.STORMFISH_VARIANT, entity -> entity instanceof Stormfish stormfish ? Optional.of(stormfish.getVariant()) : Optional.empty());
    public static final MapCodec<TrophyFishPredicate> TROPHY = TrophyFishPredicate.CODEC;

    public static void init()
    {
        registerEntitySubPredicates("splashtail", SPLASHTAIL);
        registerEntitySubPredicates("pondie", PONDIE);
        registerEntitySubPredicates("islehopper", ISLEHOPPER);
        registerEntitySubPredicates("ancientscale", ANCIENTSCALE);
        registerEntitySubPredicates("plentifin", PLENTIFIN);
        registerEntitySubPredicates("wildsplash", WILDSPLASH);
        registerEntitySubPredicates("devilfish", DEVILFISH);
        registerEntitySubPredicates("battlegill", BATTLEGILL);
        registerEntitySubPredicates("wrecker", WRECKER);
        registerEntitySubPredicates("stormfish", STORMFISH);
        registerEntitySubPredicates("trophy", TROPHY);
    }

    private static <T extends EntitySubPredicate> void registerEntitySubPredicates(String key, MapCodec<T> codec)
    {
        Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, FishOfThieves.id(key), codec);
    }

    private static <V> void registerEntitySubPredicates(String key, EntitySubPredicates.EntityHolderVariantPredicateType<V> predicateType)
    {
        Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, FishOfThieves.id(key), predicateType.codec);
    }

    public static EntitySubPredicate splashtail(HolderSet<SplashtailVariant> variant)
    {
        return SPLASHTAIL.createPredicate(variant);
    }

    public static EntitySubPredicate pondie(HolderSet<PondieVariant> variant)
    {
        return PONDIE.createPredicate(variant);
    }

    public static EntitySubPredicate islehopper(HolderSet<IslehopperVariant> variant)
    {
        return ISLEHOPPER.createPredicate(variant);
    }

    public static EntitySubPredicate ancientscale(HolderSet<AncientscaleVariant> variant)
    {
        return ANCIENTSCALE.createPredicate(variant);
    }

    public static EntitySubPredicate plentifin(HolderSet<PlentifinVariant> variant)
    {
        return PLENTIFIN.createPredicate(variant);
    }

    public static EntitySubPredicate wildsplash(HolderSet<WildsplashVariant> variant)
    {
        return WILDSPLASH.createPredicate(variant);
    }

    public static EntitySubPredicate devilfish(HolderSet<DevilfishVariant> variant)
    {
        return DEVILFISH.createPredicate(variant);
    }

    public static EntitySubPredicate battlegill(HolderSet<BattlegillVariant> variant)
    {
        return BATTLEGILL.createPredicate(variant);
    }

    public static EntitySubPredicate wrecker(HolderSet<WreckerVariant> variant)
    {
        return WRECKER.createPredicate(variant);
    }

    public static EntitySubPredicate stormfish(HolderSet<StormfishVariant> variant)
    {
        return STORMFISH.createPredicate(variant);
    }
}