package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.references.FOTBlockItemIds;
import com.stevekung.fishofthieves.references.FOTItemIds;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagsProvider extends FabricTagsProvider.ItemTagsProvider
{
    // Croptopia
    private static final TagKey<Item> CROPTOPIA_FISHES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("croptopia", "fishes"));

    // Common
    private static final TagKey<Item> C_SEEDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "seeds"));
    private static final TagKey<Item> C_FRUITS_SWEET = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "fruits/sweet"));

    public ItemTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider, FabricTagsProvider.BlockTagsProvider blockTagProvider)
    {
        super(dataOutput, provider, blockTagProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        var rawFishes = new ResourceKey<?>[] { FOTItemIds.SPLASHTAIL, FOTItemIds.PONDIE, FOTItemIds.ISLEHOPPER, FOTItemIds.ANCIENTSCALE, FOTItemIds.PLENTIFIN, FOTItemIds.WILDSPLASH, FOTItemIds.DEVILFISH, FOTItemIds.BATTLEGILL, FOTItemIds.WRECKER, FOTItemIds.STORMFISH };
        var cookedFishes = new ResourceKey<?>[] { FOTItemIds.COOKED_SPLASHTAIL, FOTItemIds.COOKED_PONDIE, FOTItemIds.COOKED_ISLEHOPPER, FOTItemIds.COOKED_ANCIENTSCALE, FOTItemIds.COOKED_PLENTIFIN, FOTItemIds.COOKED_WILDSPLASH, FOTItemIds.COOKED_DEVILFISH, FOTItemIds.COOKED_BATTLEGILL, FOTItemIds.COOKED_WRECKER, FOTItemIds.COOKED_STORMFISH };

        this.builder(ItemTags.AXOLOTL_FOOD).forceAddTag(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.THIEVES_FISH_BUCKET);
        this.builder(ItemTags.CAT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.builder(ItemTags.CHICKEN_FOOD).forceAddTag(FOTTags.Items.WORMS).add(FOTItemIds.PINEAPPLE_SEEDS, FOTItemIds.POMEGRANATE_SEEDS);
        this.builder(ItemTags.OCELOT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.builder(ItemTags.PIG_FOOD).add(FOTItemIds.COCONUT, FOTItemIds.BANANA, FOTItemIds.PINEAPPLE, FOTItemIds.HALF_PINEAPPLE,
                FOTItemIds.CROWNLESS_PINEAPPLE, FOTItemIds.MANGO, FOTItemIds.RAW_MANGO, FOTItemIds.POMEGRANATE,
                FOTItemIds.GUARDIAN_FRUIT).add(FOTBlockItemIds.BANANA_BLOSSOM);
        this.builder(ItemTags.BEE_FOOD).add(FOTBlockItemIds.BANANA_BLOSSOM).add(FOTBlockItemIds.PINK_PLUMERIA, FOTBlockItemIds.LIGHT_BLUE_PLUMERIA, FOTBlockItemIds.WHITE_PLUMERIA);
        this.builder(ItemTags.FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
        this.builder(ItemTags.SIGNS).add(FOTBlockItemIds.COCONUT_SIGN);
        this.builder(ItemTags.HANGING_SIGNS).add(FOTBlockItemIds.COCONUT_HANGING_SIGN);
        this.builder(ItemTags.WOODEN_DOORS).add(FOTBlockItemIds.COCONUT_DOOR);
        this.builder(ItemTags.WOODEN_TRAPDOORS).add(FOTBlockItemIds.COCONUT_TRAPDOOR);
        this.builder(ItemTags.LOGS).add(FOTBlockItemIds.BANANA_STEM).forceAddTag(FOTTags.Items.COCONUT_LOGS);
        this.builder(ItemTags.LOGS_THAT_BURN).add(FOTBlockItemIds.BANANA_STEM).forceAddTag(FOTTags.Items.COCONUT_LOGS);
        this.builder(ItemTags.LEAVES).add(FOTBlockItemIds.COCONUT_FRONDS, FOTBlockItemIds.BANANA_LEAVES,
                FOTBlockItemIds.MANGO_LEAVES);
        this.builder(ItemTags.PLANKS).add(FOTBlockItemIds.COCONUT_PLANKS);
        this.builder(ItemTags.WOODEN_BUTTONS).add(FOTBlockItemIds.COCONUT_BUTTON);
        this.builder(ItemTags.WOODEN_STAIRS).add(FOTBlockItemIds.COCONUT_STAIRS);
        this.builder(ItemTags.WOODEN_SLABS).add(FOTBlockItemIds.COCONUT_SLAB);
        this.builder(ItemTags.WOODEN_FENCES).add(FOTBlockItemIds.COCONUT_FENCE);
        this.builder(ItemTags.FENCE_GATES).add(FOTBlockItemIds.COCONUT_FENCE_GATE);
        this.builder(ItemTags.WOODEN_PRESSURE_PLATES).add(FOTBlockItemIds.COCONUT_PRESSURE_PLATE);
        this.builder(ItemTags.BOATS).add(FOTItemIds.COCONUT_BOAT);
        this.builder(ItemTags.CHEST_BOATS).add(FOTItemIds.COCONUT_CHEST_BOAT);
        this.builder(ItemTags.SAPLINGS).add(FOTItemIds.COCONUT).add(FOTBlockItemIds.BANANA_SHOOTS,
                FOTBlockItemIds.MANGO_SAPLING);
        this.builder(ItemTags.DECORATED_POT_SHERDS).add(FOTItemIds.STORMFISH_POTTERY_SHERD,
                FOTItemIds.KRAKEN_POTTERY_SHERD, FOTItemIds.MEGALODON_POTTERY_SHERD, FOTItemIds.GREAT_MOUTH_POTTERY_SHERD);
        this.builder(ItemTags.NAUTILUS_TAMING_ITEMS).add(FOTItemIds.ISLEHOPPER, FOTItemIds.ISLEHOPPER_BUCKET);
        this.builder(ItemTags.NAUTILUS_BUCKET_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH_BUCKET);

        this.builder(FOTTags.Items.THIEVES_FISH_BUCKET).add(FOTItemIds.FISH_BUCKETS.toArray(ResourceKey[]::new));
        this.builder(FOTTags.Items.THIEVES_FISH).add(Arrays.stream(rawFishes).toArray(ResourceKey[]::new));
        this.builder(FOTTags.Items.COOKED_THIEVES_FISH).add(Arrays.stream(cookedFishes).toArray(ResourceKey[]::new));
        this.builder(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.EARTHWORMS_FOOD).forceAddTag(FOTTags.Items.GRUBS_FOOD).forceAddTag(FOTTags.Items.LEECHES_FOOD);
        this.builder(FOTTags.Items.EARTHWORMS_FOOD).add(FOTItemIds.EARTHWORMS);
        this.builder(FOTTags.Items.GRUBS_FOOD).add(FOTItemIds.GRUBS);
        this.builder(FOTTags.Items.LEECHES_FOOD).add(FOTItemIds.LEECHES);
        this.builder(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST);
        this.builder(FOTTags.Items.WOODEN_FISH_PLAQUE).add(FOTBlockItemIds.OAK_FISH_PLAQUE, FOTBlockItemIds.SPRUCE_FISH_PLAQUE,
                FOTBlockItemIds.BIRCH_FISH_PLAQUE, FOTBlockItemIds.JUNGLE_FISH_PLAQUE, FOTBlockItemIds.ACACIA_FISH_PLAQUE,
                FOTBlockItemIds.DARK_OAK_FISH_PLAQUE, FOTBlockItemIds.MANGROVE_FISH_PLAQUE, FOTBlockItemIds.CHERRY_FISH_PLAQUE, FOTBlockItemIds.PALE_OAK_FISH_PLAQUE, FOTBlockItemIds.POPLAR_FISH_PLAQUE,
                FOTBlockItemIds.BAMBOO_FISH_PLAQUE, FOTBlockItemIds.COCONUT_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE, FOTTags.Items.IRON_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.COPPER_FRAME_FISH_PLAQUE, FOTTags.Items.COPPER_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE, FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE, FOTTags.Items.GILDED_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.COCONUT_LOGS, FOTTags.Items.COCONUT_LOGS);
        this.copy(FOTTags.Blocks.BANANA_CLUSTERS, FOTTags.Items.BANANA_CLUSTERS);

        // Common
//        this.builder(ConventionalItemTags.RAW_FISH_FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH);TODO
//        this.builder(ConventionalItemTags.COOKED_FISH_FOODS).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
//        this.builder(ConventionalItemTags.FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH)
//                .forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH)
//                .forceAddTag(FOTTags.Items.WORMS)
//                .add(FOTItemIds.COCONUT, FOTItemIds.BANANA, FOTItemIds.HALF_PINEAPPLE, FOTItemIds.PINEAPPLE, FOTItemIds.CROWNLESS_PINEAPPLE, FOTItemIds.MANGO, FOTItemIds.RAW_MANGO,
//                        FOTItemIds.POMEGRANATE, FOTItemIds.GUARDIAN_FRUIT);
//        this.builder(ConventionalItemTags.ENTITY_WATER_BUCKETS).add(FOTItemIds.FISH_BUCKETS.toArray(ResourceKey[]::new));

        // Croptopia compatibility
        this.builder(CROPTOPIA_FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH);

        // NeoForge
//        this.builder(ConventionalItemTags.CROPS).add(FOTItemIds.COCONUT, FOTItemIds.BANANA, FOTItemIds.PINEAPPLE, FOTItemIds.CROWNLESS_PINEAPPLE, FOTItemIds.MANGO, FOTItemIds.RAW_MANGO, FOTItemIds.POMEGRANATE, FOTItemIds.GUARDIAN_FRUIT);TODO
        this.builder(C_SEEDS).add(FOTItemIds.PINEAPPLE_SEEDS, FOTItemIds.POMEGRANATE_SEEDS).add(FOTBlockItemIds.MANGO_PIT);
//        this.builder(ConventionalItemTags.FRUIT_FOODS).add(FOTItemIds.COCONUT, FOTItemIds.BANANA, FOTItemIds.PINEAPPLE, FOTItemIds.CROWNLESS_PINEAPPLE, FOTItemIds.MANGO, FOTItemIds.RAW_MANGO, FOTItemIds.POMEGRANATE, FOTItemIds.GUARDIAN_FRUIT);TODO
        this.builder(C_FRUITS_SWEET).add(FOTItemIds.BANANA, FOTItemIds.PINEAPPLE, FOTItemIds.MANGO, FOTItemIds.POMEGRANATE, FOTItemIds.GUARDIAN_FRUIT);

        this.builder(FOTTags.Items.SERENE_SEASONS_YEAR_ROUND_CROPS).add(FOTItemIds.BANANA, FOTItemIds.GUARDIAN_FRUIT)
                .add(FOTBlockItemIds.BANANA_SHOOTS)
                .forceAddTag(FOTTags.Items.BANANA_CLUSTERS);
        this.builder(FOTTags.Items.SERENE_SEASONS_SPRING_CROPS).add(FOTItemIds.PINEAPPLE, FOTItemIds.CROWNLESS_PINEAPPLE, FOTItemIds.HALF_PINEAPPLE,
                FOTItemIds.PINEAPPLE_SEEDS, FOTItemIds.PINEAPPLE_CROWN,
                FOTItemIds.MANGO, FOTItemIds.RAW_MANGO, FOTItemIds.POMEGRANATE,
                FOTItemIds.POMEGRANATE_SEEDS)
                .add(FOTBlockItemIds.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.RIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.MANGO_PIT, FOTBlockItemIds.MANGO_SAPLING, FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT);
        this.builder(FOTTags.Items.SERENE_SEASONS_SUMMER_CROPS).add(FOTItemIds.COCONUT, FOTItemIds.PINEAPPLE, FOTItemIds.CROWNLESS_PINEAPPLE,
                FOTItemIds.HALF_PINEAPPLE, FOTItemIds.PINEAPPLE_SEEDS,
                FOTItemIds.PINEAPPLE_CROWN,
                FOTItemIds.MANGO, FOTItemIds.RAW_MANGO, FOTItemIds.POMEGRANATE,
                FOTItemIds.POMEGRANATE_SEEDS)
                .add(FOTBlockItemIds.UNDERRIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.RIPE_PINEAPPLE_BLOCK, FOTBlockItemIds.MANGO_PIT, FOTBlockItemIds.MANGO_SAPLING, FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT);
        this.builder(FOTTags.Items.SERENE_SEASONS_AUTUMN_CROPS).add(FOTItemIds.POMEGRANATE,
                FOTItemIds.POMEGRANATE_SEEDS).add(FOTBlockItemIds.POMEGRANATE_PLANT, FOTBlockItemIds.TALL_POMEGRANATE_PLANT);
    }
}