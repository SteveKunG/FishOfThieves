package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagsProvider extends FabricTagProvider.ItemTagProvider
{
    // Croptopia
    private static final TagKey<Item> CROPTOPIA_FISHES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("croptopia", "fishes"));

    public ItemTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider, FabricTagProvider.BlockTagProvider blockTagProvider)
    {
        super(dataOutput, provider, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        var rawFishes = new Item[] { FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH };
        var cookedFishes = new Item[] { FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH };

        this.getOrCreateTagBuilder(ItemTags.AXOLOTL_FOOD).forceAddTag(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.THIEVES_FISH_BUCKET);
        this.getOrCreateTagBuilder(ItemTags.CAT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.getOrCreateTagBuilder(ItemTags.CHICKEN_FOOD).forceAddTag(FOTTags.Items.WORMS);
        this.getOrCreateTagBuilder(ItemTags.OCELOT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.getOrCreateTagBuilder(ItemTags.FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);

        this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH_BUCKET).add(FOTTags.FISH_BUCKETS);
        this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH).add(rawFishes);
        this.getOrCreateTagBuilder(FOTTags.Items.COOKED_THIEVES_FISH).add(cookedFishes);
        this.getOrCreateTagBuilder(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.EARTHWORMS_FOOD).forceAddTag(FOTTags.Items.GRUBS_FOOD).forceAddTag(FOTTags.Items.LEECHES_FOOD);
        this.getOrCreateTagBuilder(FOTTags.Items.EARTHWORMS_FOOD).add(FOTItems.EARTHWORMS);
        this.getOrCreateTagBuilder(FOTTags.Items.GRUBS_FOOD).add(FOTItems.GRUBS);
        this.getOrCreateTagBuilder(FOTTags.Items.LEECHES_FOOD).add(FOTItems.LEECHES);
        this.getOrCreateTagBuilder(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST);
        this.getOrCreateTagBuilder(FOTTags.Items.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE.asItem(), FOTBlocks.SPRUCE_FISH_PLAQUE.asItem(), FOTBlocks.BIRCH_FISH_PLAQUE.asItem(), FOTBlocks.JUNGLE_FISH_PLAQUE.asItem(), FOTBlocks.ACACIA_FISH_PLAQUE.asItem(), FOTBlocks.DARK_OAK_FISH_PLAQUE.asItem(), FOTBlocks.MANGROVE_FISH_PLAQUE.asItem(), FOTBlocks.CHERRY_FISH_PLAQUE.asItem(), FOTBlocks.BAMBOO_FISH_PLAQUE.asItem());
        this.copy(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE, FOTTags.Items.IRON_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE, FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE, FOTTags.Items.GILDED_FRAME_FISH_PLAQUE);

        // Common
        this.getOrCreateTagBuilder(ConventionalItemTags.RAW_FISHES_FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.getOrCreateTagBuilder(ConventionalItemTags.COOKED_FISHES_FOODS).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
        this.getOrCreateTagBuilder(ConventionalItemTags.FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH).forceAddTag(FOTTags.Items.WORMS);
        this.getOrCreateTagBuilder(ConventionalItemTags.ENTITY_WATER_BUCKETS).add(FOTTags.FISH_BUCKETS);

        // Croptopia compatibility
        this.getOrCreateTagBuilder(CROPTOPIA_FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH);
    }
}