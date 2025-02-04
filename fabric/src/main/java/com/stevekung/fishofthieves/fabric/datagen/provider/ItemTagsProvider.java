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

    // Forge
    private static final TagKey<Item> FORGE_RAW_FISHES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "raw_fishes"));
    private static final TagKey<Item> FORGE_COOKED_FISHES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "cooked_fishes"));
    private static final TagKey<Item> FORGE_CROPS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "crops"));
    private static final TagKey<Item> FORGE_SEEDS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "seeds"));
    private static final TagKey<Item> FORGE_FRUITS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "fruits"));
    private static final TagKey<Item> FORGE_FRUITS_SWEET = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "fruits/sweet"));

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
        this.getOrCreateTagBuilder(ItemTags.CHICKEN_FOOD).forceAddTag(FOTTags.Items.WORMS).add(FOTItems.PINEAPPLE_SEEDS, FOTItems.POMEGRANATE_SEEDS);
        this.getOrCreateTagBuilder(ItemTags.OCELOT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.getOrCreateTagBuilder(ItemTags.PIG_FOOD).add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.HALF_PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.POMEGRANATE, FOTBlocks.BANANA_BLOSSOM.asItem());
        this.getOrCreateTagBuilder(ItemTags.FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
        this.getOrCreateTagBuilder(ItemTags.SIGNS).add(FOTBlocks.COCONUT_SIGN.asItem());
        this.getOrCreateTagBuilder(ItemTags.HANGING_SIGNS).add(FOTBlocks.COCONUT_HANGING_SIGN.asItem());
        this.getOrCreateTagBuilder(ItemTags.WOODEN_DOORS).add(FOTItems.COCONUT_DOOR);
        this.getOrCreateTagBuilder(ItemTags.WOODEN_TRAPDOORS).add(FOTBlocks.COCONUT_TRAPDOOR.asItem());
        this.getOrCreateTagBuilder(ItemTags.LOGS).forceAddTag(FOTTags.Items.COCONUT_LOGS).add(FOTBlocks.BANANA_STEM.asItem());
        this.getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).forceAddTag(FOTTags.Items.COCONUT_LOGS).add(FOTBlocks.BANANA_STEM.asItem());
        this.getOrCreateTagBuilder(ItemTags.LEAVES).add(FOTBlocks.COCONUT_FRONDS.asItem(), FOTBlocks.BANANA_LEAVES.asItem(),
                FOTBlocks.MANGO_LEAVES.asItem());
        this.getOrCreateTagBuilder(ItemTags.PLANKS).add(FOTBlocks.COCONUT_PLANKS.asItem());
        this.getOrCreateTagBuilder(ItemTags.WOODEN_BUTTONS).add(FOTBlocks.COCONUT_BUTTON.asItem());
        this.getOrCreateTagBuilder(ItemTags.WOODEN_STAIRS).add(FOTBlocks.COCONUT_STAIRS.asItem());
        this.getOrCreateTagBuilder(ItemTags.WOODEN_SLABS).add(FOTBlocks.COCONUT_SLAB.asItem());
        this.getOrCreateTagBuilder(ItemTags.WOODEN_FENCES).add(FOTBlocks.COCONUT_FENCE.asItem());
        this.getOrCreateTagBuilder(ItemTags.FENCE_GATES).add(FOTBlocks.COCONUT_FENCE_GATE.asItem());
        this.getOrCreateTagBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(FOTBlocks.COCONUT_PRESSURE_PLATE.asItem());
        this.getOrCreateTagBuilder(ItemTags.BOATS).add(FOTItems.COCONUT_BOAT);
        this.getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(FOTItems.COCONUT_CHEST_BOAT);
        this.getOrCreateTagBuilder(ItemTags.SAPLINGS).add(FOTBlocks.COCONUT_SAPLING.asItem(), FOTBlocks.BANANA_SHOOTS.asItem(),
                FOTBlocks.MANGO_SAPLING.asItem());
        this.getOrCreateTagBuilder(ItemTags.FLOWERS).add(FOTBlocks.BANANA_BLOSSOM.asItem());
        this.getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS).add(FOTBlocks.PINK_PLUMERIA.asItem(), FOTBlocks.LIGHT_BLUE_PLUMERIA.asItem(), FOTBlocks.WHITE_PLUMERIA.asItem());
        this.getOrCreateTagBuilder(ItemTags.DECORATED_POT_SHERDS).add(FOTItems.STORMFISH_POTTERY_SHERD,
                FOTItems.KRAKEN_POTTERY_SHERD, FOTItems.MEGALODON_POTTERY_SHERD);

        this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH_BUCKET).add(FOTTags.FISH_BUCKETS);
        this.getOrCreateTagBuilder(FOTTags.Items.THIEVES_FISH).add(rawFishes);
        this.getOrCreateTagBuilder(FOTTags.Items.COOKED_THIEVES_FISH).add(cookedFishes);
        this.getOrCreateTagBuilder(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.EARTHWORMS_FOOD).forceAddTag(FOTTags.Items.GRUBS_FOOD).forceAddTag(FOTTags.Items.LEECHES_FOOD);
        this.getOrCreateTagBuilder(FOTTags.Items.EARTHWORMS_FOOD).add(FOTItems.EARTHWORMS);
        this.getOrCreateTagBuilder(FOTTags.Items.GRUBS_FOOD).add(FOTItems.GRUBS);
        this.getOrCreateTagBuilder(FOTTags.Items.LEECHES_FOOD).add(FOTItems.LEECHES);
        this.getOrCreateTagBuilder(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST);
        this.getOrCreateTagBuilder(FOTTags.Items.WOODEN_FISH_PLAQUE).add(FOTBlocks.OAK_FISH_PLAQUE.asItem(), FOTBlocks.SPRUCE_FISH_PLAQUE.asItem(),
                FOTBlocks.BIRCH_FISH_PLAQUE.asItem(), FOTBlocks.JUNGLE_FISH_PLAQUE.asItem(), FOTBlocks.ACACIA_FISH_PLAQUE.asItem(),
                FOTBlocks.DARK_OAK_FISH_PLAQUE.asItem(), FOTBlocks.MANGROVE_FISH_PLAQUE.asItem(), FOTBlocks.CHERRY_FISH_PLAQUE.asItem(),
                FOTBlocks.BAMBOO_FISH_PLAQUE.asItem(), FOTBlocks.COCONUT_FISH_PLAQUE.asItem());
        this.copy(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE, FOTTags.Items.IRON_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE, FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE, FOTTags.Items.GILDED_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.COCONUT_LOGS, FOTTags.Items.COCONUT_LOGS);
        this.copy(FOTTags.Blocks.BANANA_CLUSTERS, FOTTags.Items.BANANA_CLUSTERS);

        // Common
        this.getOrCreateTagBuilder(ConventionalItemTags.RAW_FISH_FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.getOrCreateTagBuilder(ConventionalItemTags.COOKED_FISH_FOODS).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
        this.getOrCreateTagBuilder(ConventionalItemTags.FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH)
                .forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH)
                .forceAddTag(FOTTags.Items.WORMS)
                .add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.HALF_PINEAPPLE, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO,
                        FOTItems.POMEGRANATE);
        this.getOrCreateTagBuilder(ConventionalItemTags.ENTITY_WATER_BUCKETS).add(FOTTags.FISH_BUCKETS);

        // Croptopia compatibility
        this.getOrCreateTagBuilder(CROPTOPIA_FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH);

        // Forge
        this.getOrCreateTagBuilder(FORGE_RAW_FISHES).add(rawFishes);
        this.getOrCreateTagBuilder(FORGE_COOKED_FISHES).add(cookedFishes);
        this.getOrCreateTagBuilder(FORGE_CROPS).add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.POMEGRANATE);
        this.getOrCreateTagBuilder(FORGE_SEEDS).add(FOTItems.MANGO_PIT, FOTItems.PINEAPPLE_SEEDS, FOTItems.POMEGRANATE_SEEDS);
        this.getOrCreateTagBuilder(FORGE_FRUITS).add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.POMEGRANATE);
        this.getOrCreateTagBuilder(FORGE_FRUITS_SWEET).add(FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.MANGO, FOTItems.POMEGRANATE);
    }
}