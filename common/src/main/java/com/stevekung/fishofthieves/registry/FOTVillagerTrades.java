package com.stevekung.fishofthieves.registry;

import java.util.List;
import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.shoal.Shoal;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.loot.function.TreasuredFishMapFunction;

import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.storage.loot.functions.DiscardItem;
import net.minecraft.world.level.storage.loot.functions.FilteredFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;

public class FOTVillagerTrades
{
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_RAW_SPLASHTAIL_AND_EMERALD_COOKED_SPLASHTAIL = resourceKey("fisherman/1/raw_splashtail_and_emerald_cooked_splashtail");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_RAW_PONDIE_AND_EMERALD_COOKED_PONDIE = resourceKey("fisherman/1/raw_pondie_and_emerald_cooked_pondie");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_RAW_ISLEHOPPER_AND_EMERALD_COOKED_ISLEHOPPER = resourceKey("fisherman/1/raw_islehopper_and_emerald_cooked_islehopper");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_RAW_ANCIENTSCALE_AND_EMERALD_COOKED_ANCIENTSCALE = resourceKey("fisherman/1/raw_ancientscale_and_emerald_cooked_ancientscale");

    public static final ResourceKey<VillagerTrade> FISHERMAN_1_EARTHWORMS_EMERALD = resourceKey("fisherman/1/earthworms_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_GRUBS_EMERALD = resourceKey("fisherman/1/grubs_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_LEECHES_EMERALD = resourceKey("fisherman/1/leeches_emerald");

    public static final ResourceKey<VillagerTrade> FISHERMAN_1_EMERALD_SPLASHTAIL_BUCKET = resourceKey("fisherman/1/emerald_splashtail_bucket");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_EMERALD_PONDIE_BUCKET = resourceKey("fisherman/1/emerald_pondie_bucket");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_EMERALD_ISLEHOPPER_BUCKET = resourceKey("fisherman/1/emerald_islehopper_bucket");
    public static final ResourceKey<VillagerTrade> FISHERMAN_1_EMERALD_ANCIENTSCALE_BUCKET = resourceKey("fisherman/1/emerald_ancientscale_bucket");

    public static final ResourceKey<VillagerTrade> FISHERMAN_2_SPLASHTAIL_EMERALD = resourceKey("fisherman/2/splashtail_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_PONDIE_EMERALD = resourceKey("fisherman/2/pondie_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_ISLEHOPPER_EMERALD = resourceKey("fisherman/2/islehopper_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_ANCIENTSCALE_EMERALD = resourceKey("fisherman/2/ancientscale_emerald");

    public static final ResourceKey<VillagerTrade> FISHERMAN_2_RAW_PLENTIFIN_AND_EMERALD_COOKED_PLENTIFIN = resourceKey("fisherman/2/raw_plentifin_and_emerald_cooked_plentifin");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_RAW_WILDSPLASH_AND_EMERALD_COOKED_WILDSPLASH = resourceKey("fisherman/2/raw_wildsplash_and_emerald_cooked_wildsplash");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_RAW_DEVILFISH_AND_EMERALD_COOKED_DEVILFISH = resourceKey("fisherman/2/raw_devilfish_and_emerald_cooked_devilfish");

    public static final ResourceKey<VillagerTrade> FISHERMAN_2_EMERALD_PLENTIFIN_BUCKET = resourceKey("fisherman/2/emerald_plentifin_bucket");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_EMERALD_WILDSPLASH_BUCKET = resourceKey("fisherman/2/emerald_wildsplash_bucket");
    public static final ResourceKey<VillagerTrade> FISHERMAN_2_EMERALD_DEVILFISH_BUCKET = resourceKey("fisherman/2/emerald_devilfish_bucket");

    public static final ResourceKey<VillagerTrade> FISHERMAN_3_PLENTIFIN_EMERALD = resourceKey("fisherman/3/plentifin_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_3_WILDSPLASH_EMERALD = resourceKey("fisherman/3/wildsplash_emerald");
    public static final ResourceKey<VillagerTrade> FISHERMAN_3_DEVILFISH_EMERALD = resourceKey("fisherman/3/devilfish_emerald");

    public static final ResourceKey<VillagerTrade> FISHERMAN_3_EMERALD_AND_TREASURED_FISH_MAP = resourceKey("fisherman/3/emerald_and_treasured_fish_map");

    public static void bootstrap(BootstrapContext<VillagerTrade> context)
    {
        var itemLookup = context.lookup(Registries.ITEM);

        VillagerTrades.register(context, FISHERMAN_1_RAW_SPLASHTAIL_AND_EMERALD_COOKED_SPLASHTAIL, new VillagerTrade(new TradeCost(FOTItems.SPLASHTAIL, 6), Optional.of(new TradeCost(Items.EMERALD, 1)), new ItemStackTemplate(FOTItems.COOKED_SPLASHTAIL, 6), 8, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_RAW_PONDIE_AND_EMERALD_COOKED_PONDIE, new VillagerTrade(new TradeCost(FOTItems.PONDIE, 6), Optional.of(new TradeCost(Items.EMERALD, 1)), new ItemStackTemplate(FOTItems.COOKED_PONDIE, 6), 8, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_RAW_ISLEHOPPER_AND_EMERALD_COOKED_ISLEHOPPER, new VillagerTrade(new TradeCost(FOTItems.ISLEHOPPER, 2), Optional.of(new TradeCost(Items.EMERALD, 2)), new ItemStackTemplate(FOTItems.COOKED_ISLEHOPPER, 2), 8, 2, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_RAW_ANCIENTSCALE_AND_EMERALD_COOKED_ANCIENTSCALE, new VillagerTrade(new TradeCost(FOTItems.ANCIENTSCALE, 3), Optional.of(new TradeCost(Items.EMERALD, 1)), new ItemStackTemplate(FOTItems.COOKED_ANCIENTSCALE, 3), 5, 3, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_1_EARTHWORMS_EMERALD, new VillagerTrade(new TradeCost(FOTItems.EARTHWORMS, 48), new ItemStackTemplate(Items.EMERALD), 8, 10, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_GRUBS_EMERALD, new VillagerTrade(new TradeCost(FOTItems.GRUBS, 32), new ItemStackTemplate(Items.EMERALD), 8, 12, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_LEECHES_EMERALD, new VillagerTrade(new TradeCost(FOTItems.LEECHES, 24), new ItemStackTemplate(Items.EMERALD), 8, 14, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_1_EMERALD_SPLASHTAIL_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.SPLASHTAIL_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_EMERALD_PONDIE_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.PONDIE_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_EMERALD_ISLEHOPPER_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.ISLEHOPPER_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_1_EMERALD_ANCIENTSCALE_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.ANCIENTSCALE_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_2_SPLASHTAIL_EMERALD, new VillagerTrade(new TradeCost(FOTItems.SPLASHTAIL, 12), new ItemStackTemplate(Items.EMERALD), 8, 12, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_PONDIE_EMERALD, new VillagerTrade(new TradeCost(FOTItems.PONDIE, 12), new ItemStackTemplate(Items.EMERALD), 8, 12, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_ISLEHOPPER_EMERALD, new VillagerTrade(new TradeCost(FOTItems.ISLEHOPPER, 8), new ItemStackTemplate(Items.EMERALD), 8, 15, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_ANCIENTSCALE_EMERALD, new VillagerTrade(new TradeCost(FOTItems.ANCIENTSCALE, 8), new ItemStackTemplate(Items.EMERALD), 9, 15, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_2_RAW_PLENTIFIN_AND_EMERALD_COOKED_PLENTIFIN, new VillagerTrade(new TradeCost(FOTItems.PLENTIFIN, 3), Optional.of(new TradeCost(Items.EMERALD, 2)), new ItemStackTemplate(FOTItems.COOKED_PLENTIFIN, 3), 5, 3, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_RAW_WILDSPLASH_AND_EMERALD_COOKED_WILDSPLASH, new VillagerTrade(new TradeCost(FOTItems.WILDSPLASH, 4), Optional.of(new TradeCost(Items.EMERALD, 2)), new ItemStackTemplate(FOTItems.COOKED_WILDSPLASH, 4), 6, 3, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_RAW_DEVILFISH_AND_EMERALD_COOKED_DEVILFISH, new VillagerTrade(new TradeCost(FOTItems.DEVILFISH, 4), Optional.of(new TradeCost(Items.EMERALD, 3)), new ItemStackTemplate(FOTItems.COOKED_DEVILFISH, 4), 6, 4, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_2_EMERALD_PLENTIFIN_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.PLENTIFIN_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_EMERALD_WILDSPLASH_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.WILDSPLASH_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_2_EMERALD_DEVILFISH_BUCKET, new VillagerTrade(new TradeCost(Items.EMERALD, 3), FOTMobBucketItem.createRandomBucket(FOTItems.DEVILFISH_BUCKET), 16, 1, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_3_PLENTIFIN_EMERALD, new VillagerTrade(new TradeCost(FOTItems.PLENTIFIN, 8), new ItemStackTemplate(Items.EMERALD), 9, 17, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_3_WILDSPLASH_EMERALD, new VillagerTrade(new TradeCost(FOTItems.WILDSPLASH, 8), new ItemStackTemplate(Items.EMERALD), 9, 17, 0.05F, Optional.empty(), List.of()));
        VillagerTrades.register(context, FISHERMAN_3_DEVILFISH_EMERALD, new VillagerTrade(new TradeCost(FOTItems.DEVILFISH, 6), new ItemStackTemplate(Items.EMERALD), 10, 20, 0.05F, Optional.empty(), List.of()));

        VillagerTrades.register(context, FISHERMAN_3_EMERALD_AND_TREASURED_FISH_MAP, new VillagerTrade(new TradeCost(Items.EMERALD, 12), new ItemStackTemplate(Items.MAP), 1, 20, 0.2F, Optional.empty(), List.of(
                TreasuredFishMapFunction.makeTreasuredFishMap()
                        .setZoom((byte) 1)
                        .setMinimumSearchRadius(50)
                        .setMaximumSearchRadius(100)
                        .setMaxAttempt(10)
                        .setTier(1)
                        .build(),
                SetNameFunction.setName(Component.translatable(Shoal.FILLED_MAP_TREASURED_FISH), SetNameFunction.Target.ITEM_NAME).build(),
                FilteredFunction.filtered(new ItemPredicate.Builder().of(itemLookup, Items.FILLED_MAP)
                                .withComponents(DataComponentMatchers.Builder.components().any(DataComponents.MAP_ID).build()).build())
                        .onFail(Optional.of(DiscardItem.discardItem().build())).build())));

    }

    private static ResourceKey<VillagerTrade> resourceKey(String path)
    {
        return ResourceKey.create(Registries.VILLAGER_TRADE, FishOfThieves.id(path));
    }
}