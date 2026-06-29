package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.registry.FOTVillagerTrades;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

public class VillagerTradesTagsProvider extends FabricTagsProvider<VillagerTrade>
{
    public VillagerTradesTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.VILLAGER_TRADE, provider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(VillagerTradeTags.FISHERMAN_LEVEL_1).add(
                FOTVillagerTrades.FISHERMAN_1_RAW_SPLASHTAIL_AND_EMERALD_COOKED_SPLASHTAIL,
                FOTVillagerTrades.FISHERMAN_1_RAW_PONDIE_AND_EMERALD_COOKED_PONDIE,
                FOTVillagerTrades.FISHERMAN_1_RAW_ISLEHOPPER_AND_EMERALD_COOKED_ISLEHOPPER,
                FOTVillagerTrades.FISHERMAN_1_RAW_ANCIENTSCALE_AND_EMERALD_COOKED_ANCIENTSCALE,

                FOTVillagerTrades.FISHERMAN_1_EARTHWORMS_EMERALD,
                FOTVillagerTrades.FISHERMAN_1_GRUBS_EMERALD,
                FOTVillagerTrades.FISHERMAN_1_LEECHES_EMERALD,

                FOTVillagerTrades.FISHERMAN_1_EMERALD_SPLASHTAIL_BUCKET,
                FOTVillagerTrades.FISHERMAN_1_EMERALD_PONDIE_BUCKET,
                FOTVillagerTrades.FISHERMAN_1_EMERALD_ISLEHOPPER_BUCKET,
                FOTVillagerTrades.FISHERMAN_1_EMERALD_ANCIENTSCALE_BUCKET
        );

        this.builder(VillagerTradeTags.FISHERMAN_LEVEL_2).add(
                FOTVillagerTrades.FISHERMAN_2_SPLASHTAIL_EMERALD,
                FOTVillagerTrades.FISHERMAN_2_PONDIE_EMERALD,
                FOTVillagerTrades.FISHERMAN_2_ISLEHOPPER_EMERALD,
                FOTVillagerTrades.FISHERMAN_2_ANCIENTSCALE_EMERALD,

                FOTVillagerTrades.FISHERMAN_2_RAW_PLENTIFIN_AND_EMERALD_COOKED_PLENTIFIN,
                FOTVillagerTrades.FISHERMAN_2_RAW_WILDSPLASH_AND_EMERALD_COOKED_WILDSPLASH,
                FOTVillagerTrades.FISHERMAN_2_RAW_DEVILFISH_AND_EMERALD_COOKED_DEVILFISH,

                FOTVillagerTrades.FISHERMAN_2_EMERALD_PLENTIFIN_BUCKET,
                FOTVillagerTrades.FISHERMAN_2_EMERALD_WILDSPLASH_BUCKET,
                FOTVillagerTrades.FISHERMAN_2_EMERALD_DEVILFISH_BUCKET
        );

        this.builder(VillagerTradeTags.FISHERMAN_LEVEL_3).add(
                FOTVillagerTrades.FISHERMAN_3_PLENTIFIN_EMERALD,
                FOTVillagerTrades.FISHERMAN_3_WILDSPLASH_EMERALD,
                FOTVillagerTrades.FISHERMAN_3_DEVILFISH_EMERALD,

                FOTVillagerTrades.FISHERMAN_3_EMERALD_AND_TREASURED_FISH_MAP
        );

        this.builder(VillagerTradeTags.FISHERMAN_LEVEL_4).add(
                FOTVillagerTrades.FISHERMAN_4_BATTLEGILL_EMERALD,
                FOTVillagerTrades.FISHERMAN_4_WRECKER_EMERALD,
                FOTVillagerTrades.FISHERMAN_4_STORMFISH_EMERALD,

                FOTVillagerTrades.FISHERMAN_4_EMERALD_AND_TREASURED_FISH_MAP
        );

        this.builder(VillagerTradeTags.FISHERMAN_LEVEL_5).add(
                FOTVillagerTrades.FISHERMAN_5_RAW_BATTLEGILL_AND_EMERALD_COOKED_BATTLEGILL,
                FOTVillagerTrades.FISHERMAN_5_RAW_WRECKER_AND_EMERALD_COOKED_WRECKER,
                FOTVillagerTrades.FISHERMAN_5_RAW_STORMFISH_AND_EMERALD_COOKED_STORMFISH,

                FOTVillagerTrades.FISHERMAN_5_EMERALD_BATTLEGILL_BUCKET,
                FOTVillagerTrades.FISHERMAN_5_EMERALD_WRECKER_BUCKET,
                FOTVillagerTrades.FISHERMAN_5_EMERALD_STORMFISH_BUCKET
        );

        this.builder(VillagerTradeTags.FARMER_LEVEL_1).add(
                FOTVillagerTrades.FARMER_1_BANANA_EMERALD,
                FOTVillagerTrades.FARMER_1_COCONUT_EMERALD,
                FOTVillagerTrades.FARMER_1_POMEGRANATE_EMERALD,
                FOTVillagerTrades.FARMER_1_RAW_MANGO_EMERALD
        );

        this.builder(VillagerTradeTags.FARMER_LEVEL_2).add(
                FOTVillagerTrades.FARMER_2_MANGO_EMERALD
        );

        this.builder(VillagerTradeTags.FARMER_LEVEL_3).add(
                FOTVillagerTrades.FARMER_3_PINEAPPLE_EMERALD,

                FOTVillagerTrades.FARMER_3_EMERALD_MANGO
        );

        this.builder(VillagerTradeTags.FARMER_LEVEL_4).add(
                FOTVillagerTrades.FARMER_4_EMERALD_PINEAPPLE
        );

        this.builder(FOTTags.VillagerTrades.TREASURED_FISH_MAP).add(
                FOTVillagerTrades.FISHERMAN_3_EMERALD_AND_TREASURED_FISH_MAP,
                FOTVillagerTrades.FISHERMAN_4_EMERALD_AND_TREASURED_FISH_MAP
        );
    }
}