package com.stevekung.fishofthieves.registry;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.entity.variant.*;
import com.stevekung.fishofthieves.loot.predicate.TrophyFishPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityVariantPredicate;

public class FOTEntitySubPredicate
{
    public static final EntityVariantPredicate<SplashtailVariant> SPLASHTAIL = EntityVariantPredicate.create(FOTRegistry.SPLASHTAIL_VARIANT, entity -> entity instanceof Splashtail splashtail ? Optional.of(splashtail.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<PondieVariant> PONDIE = EntityVariantPredicate.create(FOTRegistry.PONDIE_VARIANT, entity -> entity instanceof Pondie pondie ? Optional.of(pondie.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<IslehopperVariant> ISLEHOPPER = EntityVariantPredicate.create(FOTRegistry.ISLEHOPPER_VARIANT, entity -> entity instanceof Islehopper islehopper ? Optional.of(islehopper.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<AncientscaleVariant> ANCIENTSCALE = EntityVariantPredicate.create(FOTRegistry.ANCIENTSCALE_VARIANT, entity -> entity instanceof Ancientscale ancientscale ? Optional.of(ancientscale.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<PlentifinVariant> PLENTIFIN = EntityVariantPredicate.create(FOTRegistry.PLENTIFIN_VARIANT, entity -> entity instanceof Plentifin plentifin ? Optional.of(plentifin.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<WildsplashVariant> WILDSPLASH = EntityVariantPredicate.create(FOTRegistry.WILDSPLASH_VARIANT, entity -> entity instanceof Wildsplash wildsplash ? Optional.of(wildsplash.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<DevilfishVariant> DEVILFISH = EntityVariantPredicate.create(FOTRegistry.DEVILFISH_VARIANT, entity -> entity instanceof Devilfish devilfish ? Optional.of(devilfish.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<BattlegillVariant> BATTLEGILL = EntityVariantPredicate.create(FOTRegistry.BATTLEGILL_VARIANT, entity -> entity instanceof Battlegill battlegill ? Optional.of(battlegill.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<WreckerVariant> WRECKER = EntityVariantPredicate.create(FOTRegistry.WRECKER_VARIANT, entity -> entity instanceof Wrecker wrecker ? Optional.of(wrecker.getVariant()) : Optional.empty());
    public static final EntityVariantPredicate<StormfishVariant> STORMFISH = EntityVariantPredicate.create(FOTRegistry.STORMFISH_VARIANT, entity -> entity instanceof Stormfish stormfish ? Optional.of(stormfish.getVariant()) : Optional.empty());
    public static final EntitySubPredicate.Type TROPHY = TrophyFishPredicate::fromJson;

    public static void init()
    {
        var newSubPredicates = Maps.<String, EntitySubPredicate.Type>newLinkedHashMap();
        newSubPredicates.put("splashtail", SPLASHTAIL.type());
        newSubPredicates.put("pondie", PONDIE.type());
        newSubPredicates.put("islehopper", ISLEHOPPER.type());
        newSubPredicates.put("ancientscale", ANCIENTSCALE.type());
        newSubPredicates.put("plentifin", PLENTIFIN.type());
        newSubPredicates.put("wildsplash", WILDSPLASH.type());
        newSubPredicates.put("devilfish", DEVILFISH.type());
        newSubPredicates.put("battlegill", BATTLEGILL.type());
        newSubPredicates.put("wrecker", WRECKER.type());
        newSubPredicates.put("stormfish", STORMFISH.type());
        newSubPredicates.put("trophy", TROPHY);

        var modifyMap = Maps.newLinkedHashMap(EntitySubPredicate.Types.TYPES);
        modifyMap.putAll(newSubPredicates.entrySet().stream().collect(Collectors.toMap(e -> FishOfThieves.MOD_RESOURCES + e.getKey(), Map.Entry::getValue)));
        EntitySubPredicate.Types.TYPES = ImmutableBiMap.copyOf(modifyMap);
    }

    public static EntitySubPredicate variant(SplashtailVariant variant)
    {
        return SPLASHTAIL.createPredicate(variant);
    }

    public static EntitySubPredicate variant(PondieVariant variant)
    {
        return PONDIE.createPredicate(variant);
    }

    public static EntitySubPredicate variant(IslehopperVariant variant)
    {
        return ISLEHOPPER.createPredicate(variant);
    }

    public static EntitySubPredicate variant(AncientscaleVariant variant)
    {
        return ANCIENTSCALE.createPredicate(variant);
    }

    public static EntitySubPredicate variant(PlentifinVariant variant)
    {
        return PLENTIFIN.createPredicate(variant);
    }

    public static EntitySubPredicate variant(WildsplashVariant variant)
    {
        return WILDSPLASH.createPredicate(variant);
    }

    public static EntitySubPredicate variant(DevilfishVariant variant)
    {
        return DEVILFISH.createPredicate(variant);
    }

    public static EntitySubPredicate variant(BattlegillVariant variant)
    {
        return BATTLEGILL.createPredicate(variant);
    }

    public static EntitySubPredicate variant(WreckerVariant variant)
    {
        return WRECKER.createPredicate(variant);
    }

    public static EntitySubPredicate variant(StormfishVariant variant)
    {
        return STORMFISH.createPredicate(variant);
    }
}