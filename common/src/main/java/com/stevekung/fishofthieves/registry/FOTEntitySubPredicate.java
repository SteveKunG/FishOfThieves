package com.stevekung.fishofthieves.registry;

import java.util.Optional;

import com.mojang.serialization.MapCodec;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import com.stevekung.fishofthieves.entity.variant.SplashtailVariant;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FOTEntitySubPredicate
{
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<SplashtailVariant> SPLASHTAIL = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.SPLASHTAIL_VARIANT, entity -> entity instanceof Splashtail splashtail ? Optional.of(splashtail.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<PondieVariant> PONDIE = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.PONDIE_VARIANT, entity -> entity instanceof Pondie pondie ? Optional.of(pondie.getVariant()) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<IslehopperVariant> ISLEHOPPER = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.ISLEHOPPER_VARIANT, entity -> entity instanceof Islehopper islehopper ? Optional.of(Holder.direct(islehopper.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<AncientscaleVariant> ANCIENTSCALE = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.ANCIENTSCALE_VARIANT, entity -> entity instanceof Ancientscale ancientscale ? Optional.of(Holder.direct(ancientscale.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<PlentifinVariant> PLENTIFIN = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.PLENTIFIN_VARIANT, entity -> entity instanceof Plentifin plentifin ? Optional.of(Holder.direct(plentifin.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<WildsplashVariant> WILDSPLASH = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.WILDSPLASH_VARIANT, entity -> entity instanceof Wildsplash wildsplash ? Optional.of(Holder.direct(wildsplash.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<DevilfishVariant> DEVILFISH = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.DEVILFISH_VARIANT, entity -> entity instanceof Devilfish devilfish ? Optional.of(Holder.direct(devilfish.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<BattlegillVariant> BATTLEGILL = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.BATTLEGILL_VARIANT, entity -> entity instanceof Battlegill battlegill ? Optional.of(Holder.direct(battlegill.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<WreckerVariant> WRECKER = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.WRECKER_VARIANT, entity -> entity instanceof Wrecker wrecker ? Optional.of(Holder.direct(wrecker.getVariant())) : Optional.empty());
    public static final EntitySubPredicates.EntityHolderVariantPredicateType<StormfishVariant> STORMFISH = EntitySubPredicates.EntityHolderVariantPredicateType.create(FOTRegistries.STORMFISH_VARIANT, entity -> entity instanceof Stormfish stormfish ? Optional.of(Holder.direct(stormfish.getVariant())) : Optional.empty());
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

    //TODO Move to FOTPlatform
    private static <T extends EntitySubPredicate> void registerEntitySubPredicates(String key, MapCodec<T> codec)
    {
        Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, FishOfThieves.res(key), codec);
    }

    //TODO Move to FOTPlatform
    private static <V> void registerEntitySubPredicates(String key, EntitySubPredicates.EntityHolderVariantPredicateType<V> predicateType)
    {
        Registry.register(BuiltInRegistries.ENTITY_SUB_PREDICATE_TYPE, FishOfThieves.res(key), predicateType.codec);
    }

    public static EntitySubPredicate variant(HolderSet<SplashtailVariant> variant)
    {
        return SPLASHTAIL.createPredicate(variant);
    }

    public static EntitySubPredicate variant(PondieVariant variant)
    {
        return PONDIE.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(IslehopperVariant variant)
    {
        return ISLEHOPPER.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(AncientscaleVariant variant)
    {
        return ANCIENTSCALE.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(PlentifinVariant variant)
    {
        return PLENTIFIN.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(WildsplashVariant variant)
    {
        return WILDSPLASH.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(DevilfishVariant variant)
    {
        return DEVILFISH.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(BattlegillVariant variant)
    {
        return BATTLEGILL.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(WreckerVariant variant)
    {
        return WRECKER.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }

    public static EntitySubPredicate variant(StormfishVariant variant)
    {
        return STORMFISH.createPredicate(HolderSet.direct(Holder.direct(variant)));
    }
}