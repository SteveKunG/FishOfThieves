package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagsProvider extends FabricTagProvider.ItemTagProvider
{
    // Croptopia
    private static final TagKey<Item> CROPTOPIA_FISHES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("croptopia", "fishes"));

    // Common
    private static final TagKey<Item> C_SEEDS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "seeds"));
    private static final TagKey<Item> C_FRUITS_SWEET = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "fruits/sweet"));

    public ItemTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider, FabricTagProvider.BlockTagProvider blockTagProvider)
    {
        super(dataOutput, provider, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        var rawFishes = new Item[] { FOTItems.SPLASHTAIL, FOTItems.PONDIE, FOTItems.ISLEHOPPER, FOTItems.ANCIENTSCALE, FOTItems.PLENTIFIN, FOTItems.WILDSPLASH, FOTItems.DEVILFISH, FOTItems.BATTLEGILL, FOTItems.WRECKER, FOTItems.STORMFISH };
        var cookedFishes = new Item[] { FOTItems.COOKED_SPLASHTAIL, FOTItems.COOKED_PONDIE, FOTItems.COOKED_ISLEHOPPER, FOTItems.COOKED_ANCIENTSCALE, FOTItems.COOKED_PLENTIFIN, FOTItems.COOKED_WILDSPLASH, FOTItems.COOKED_DEVILFISH, FOTItems.COOKED_BATTLEGILL, FOTItems.COOKED_WRECKER, FOTItems.COOKED_STORMFISH };

        this.valueLookupBuilder(ItemTags.AXOLOTL_FOOD).forceAddTag(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.THIEVES_FISH_BUCKET);
        this.valueLookupBuilder(ItemTags.CAT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.valueLookupBuilder(ItemTags.CHICKEN_FOOD).forceAddTag(FOTTags.Items.WORMS).add(FOTItems.PINEAPPLE_SEEDS, FOTItems.POMEGRANATE_SEEDS);
        this.valueLookupBuilder(ItemTags.OCELOT_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.valueLookupBuilder(ItemTags.PIG_FOOD).add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.HALF_PINEAPPLE,
                FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.POMEGRANATE, FOTItems.BANANA_BLOSSOM,
                FOTItems.GUARDIAN_FRUIT);
        this.valueLookupBuilder(ItemTags.BEE_FOOD).add(FOTItems.BANANA_BLOSSOM, FOTItems.PINK_PLUMERIA, FOTItems.LIGHT_BLUE_PLUMERIA, FOTItems.WHITE_PLUMERIA);
        this.valueLookupBuilder(ItemTags.FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
        this.valueLookupBuilder(ItemTags.SIGNS).add(FOTItems.COCONUT_SIGN);
        this.valueLookupBuilder(ItemTags.HANGING_SIGNS).add(FOTItems.COCONUT_HANGING_SIGN);
        this.valueLookupBuilder(ItemTags.WOODEN_DOORS).add(FOTItems.COCONUT_DOOR);
        this.valueLookupBuilder(ItemTags.WOODEN_TRAPDOORS).add(FOTItems.COCONUT_TRAPDOOR);
        this.valueLookupBuilder(ItemTags.LOGS).forceAddTag(FOTTags.Items.COCONUT_LOGS).add(FOTItems.BANANA_STEM);
        this.valueLookupBuilder(ItemTags.LOGS_THAT_BURN).forceAddTag(FOTTags.Items.COCONUT_LOGS).add(FOTItems.BANANA_STEM);
        this.valueLookupBuilder(ItemTags.LEAVES).add(FOTItems.COCONUT_FRONDS, FOTItems.BANANA_LEAVES,
                FOTItems.MANGO_LEAVES);
        this.valueLookupBuilder(ItemTags.PLANKS).add(FOTItems.COCONUT_PLANKS);
        this.valueLookupBuilder(ItemTags.WOODEN_BUTTONS).add(FOTItems.COCONUT_BUTTON);
        this.valueLookupBuilder(ItemTags.WOODEN_STAIRS).add(FOTItems.COCONUT_STAIRS);
        this.valueLookupBuilder(ItemTags.WOODEN_SLABS).add(FOTItems.COCONUT_SLAB);
        this.valueLookupBuilder(ItemTags.WOODEN_FENCES).add(FOTItems.COCONUT_FENCE);
        this.valueLookupBuilder(ItemTags.FENCE_GATES).add(FOTItems.COCONUT_FENCE_GATE);
        this.valueLookupBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(FOTItems.COCONUT_PRESSURE_PLATE);
        this.valueLookupBuilder(ItemTags.BOATS).add(FOTItems.COCONUT_BOAT);
        this.valueLookupBuilder(ItemTags.CHEST_BOATS).add(FOTItems.COCONUT_CHEST_BOAT);
        this.valueLookupBuilder(ItemTags.SAPLINGS).add(FOTItems.COCONUT, FOTItems.BANANA_SHOOTS,
                FOTItems.MANGO_SAPLING);
        this.valueLookupBuilder(ItemTags.SMALL_FLOWERS).add(FOTItems.BANANA_BLOSSOM, FOTItems.PINK_PLUMERIA, FOTItems.LIGHT_BLUE_PLUMERIA, FOTItems.WHITE_PLUMERIA);
        this.valueLookupBuilder(ItemTags.DECORATED_POT_SHERDS).add(FOTItems.STORMFISH_POTTERY_SHERD,
                FOTItems.KRAKEN_POTTERY_SHERD, FOTItems.MEGALODON_POTTERY_SHERD, FOTItems.GREAT_MOUTH_POTTERY_SHERD);
        this.valueLookupBuilder(ItemTags.NAUTILUS_TAMING_ITEMS).add(FOTItems.ISLEHOPPER, FOTItems.ISLEHOPPER_BUCKET);
        this.valueLookupBuilder(ItemTags.NAUTILUS_BUCKET_FOOD).forceAddTag(FOTTags.Items.THIEVES_FISH_BUCKET);

        this.valueLookupBuilder(FOTTags.Items.THIEVES_FISH_BUCKET).add(FOTTags.FISH_BUCKETS);
        this.valueLookupBuilder(FOTTags.Items.THIEVES_FISH).add(rawFishes);
        this.valueLookupBuilder(FOTTags.Items.COOKED_THIEVES_FISH).add(cookedFishes);
        this.valueLookupBuilder(FOTTags.Items.WORMS).forceAddTag(FOTTags.Items.EARTHWORMS_FOOD).forceAddTag(FOTTags.Items.GRUBS_FOOD).forceAddTag(FOTTags.Items.LEECHES_FOOD);
        this.valueLookupBuilder(FOTTags.Items.EARTHWORMS_FOOD).add(FOTItems.EARTHWORMS);
        this.valueLookupBuilder(FOTTags.Items.GRUBS_FOOD).add(FOTItems.GRUBS);
        this.valueLookupBuilder(FOTTags.Items.LEECHES_FOOD).add(FOTItems.LEECHES);
        this.valueLookupBuilder(FOTTags.Items.FISH_PLAQUE_BUCKET_BLACKLIST);
        this.valueLookupBuilder(FOTTags.Items.WOODEN_FISH_PLAQUE).add(FOTItems.OAK_FISH_PLAQUE, FOTItems.SPRUCE_FISH_PLAQUE,
                FOTItems.BIRCH_FISH_PLAQUE, FOTItems.JUNGLE_FISH_PLAQUE, FOTItems.ACACIA_FISH_PLAQUE,
                FOTItems.DARK_OAK_FISH_PLAQUE, FOTItems.MANGROVE_FISH_PLAQUE, FOTItems.CHERRY_FISH_PLAQUE, FOTItems.PALE_OAK_FISH_PLAQUE,
                FOTItems.BAMBOO_FISH_PLAQUE, FOTItems.COCONUT_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.IRON_FRAME_FISH_PLAQUE, FOTTags.Items.IRON_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.COPPER_FRAME_FISH_PLAQUE, FOTTags.Items.COPPER_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GOLDEN_FRAME_FISH_PLAQUE, FOTTags.Items.GOLDEN_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.GILDED_FRAME_FISH_PLAQUE, FOTTags.Items.GILDED_FRAME_FISH_PLAQUE);
        this.copy(FOTTags.Blocks.COCONUT_LOGS, FOTTags.Items.COCONUT_LOGS);
        this.copy(FOTTags.Blocks.BANANA_CLUSTERS, FOTTags.Items.BANANA_CLUSTERS);

        // Common
        this.valueLookupBuilder(ConventionalItemTags.RAW_FISH_FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH);
        this.valueLookupBuilder(ConventionalItemTags.COOKED_FISH_FOODS).forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH);
        this.valueLookupBuilder(ConventionalItemTags.FOODS).forceAddTag(FOTTags.Items.THIEVES_FISH)
                .forceAddTag(FOTTags.Items.COOKED_THIEVES_FISH)
                .forceAddTag(FOTTags.Items.WORMS)
                .add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.HALF_PINEAPPLE, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO,
                        FOTItems.POMEGRANATE, FOTItems.GUARDIAN_FRUIT);
        this.valueLookupBuilder(ConventionalItemTags.ENTITY_WATER_BUCKETS).add(FOTTags.FISH_BUCKETS);

        // Croptopia compatibility
        this.valueLookupBuilder(CROPTOPIA_FISHES).forceAddTag(FOTTags.Items.THIEVES_FISH);

        // NeoForge
        this.valueLookupBuilder(ConventionalItemTags.CROPS).add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.POMEGRANATE, FOTItems.GUARDIAN_FRUIT);
        this.valueLookupBuilder(C_SEEDS).add(FOTItems.MANGO_PIT, FOTItems.PINEAPPLE_SEEDS, FOTItems.POMEGRANATE_SEEDS);
        this.valueLookupBuilder(ConventionalItemTags.FRUIT_FOODS).add(FOTItems.COCONUT, FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.POMEGRANATE, FOTItems.GUARDIAN_FRUIT);
        this.valueLookupBuilder(C_FRUITS_SWEET).add(FOTItems.BANANA, FOTItems.PINEAPPLE, FOTItems.MANGO, FOTItems.POMEGRANATE, FOTItems.GUARDIAN_FRUIT);

        this.valueLookupBuilder(FOTTags.Items.SERENE_SEASONS_YEAR_ROUND_CROPS).add(FOTItems.BANANA, FOTItems.BANANA_SHOOTS, FOTItems.GUARDIAN_FRUIT)
                .forceAddTag(FOTTags.Items.BANANA_CLUSTERS);
        this.valueLookupBuilder(FOTTags.Items.SERENE_SEASONS_SPRING_CROPS).add(FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE, FOTItems.HALF_PINEAPPLE,
                FOTItems.PINEAPPLE_SEEDS, FOTItems.PINEAPPLE_CROWN, FOTItems.UNDERRIPE_PINEAPPLE_BLOCK, FOTItems.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTItems.RIPE_PINEAPPLE_BLOCK,
                FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.MANGO_PIT, FOTItems.MANGO_SAPLING, FOTItems.POMEGRANATE,
                FOTItems.POMEGRANATE_SEEDS, FOTItems.POMEGRANATE_PLANT, FOTItems.TALL_POMEGRANATE_PLANT);
        this.valueLookupBuilder(FOTTags.Items.SERENE_SEASONS_SUMMER_CROPS).add(FOTItems.COCONUT, FOTItems.PINEAPPLE, FOTItems.CROWNLESS_PINEAPPLE,
                FOTItems.HALF_PINEAPPLE, FOTItems.PINEAPPLE_SEEDS,
                FOTItems.PINEAPPLE_CROWN, FOTItems.UNDERRIPE_PINEAPPLE_BLOCK, FOTItems.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTItems.RIPE_PINEAPPLE_BLOCK,
                FOTItems.MANGO, FOTItems.RAW_MANGO, FOTItems.MANGO_PIT, FOTItems.MANGO_SAPLING, FOTItems.POMEGRANATE,
                FOTItems.POMEGRANATE_SEEDS, FOTItems.POMEGRANATE_PLANT, FOTItems.TALL_POMEGRANATE_PLANT);
        this.valueLookupBuilder(FOTTags.Items.SERENE_SEASONS_AUTUMN_CROPS).add(FOTItems.POMEGRANATE,
                FOTItems.POMEGRANATE_SEEDS, FOTItems.POMEGRANATE_PLANT, FOTItems.TALL_POMEGRANATE_PLANT);
    }
}