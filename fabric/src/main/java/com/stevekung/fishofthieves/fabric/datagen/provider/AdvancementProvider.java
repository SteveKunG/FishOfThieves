package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.common.collect.BiMap;
import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.item.predicate.BucketNbtPredicate;
import com.stevekung.fishofthieves.item.predicate.ItemBucketEntityDataPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import com.stevekung.fishofthieves.trigger.FallingAnvilCrushItemTrigger;
import com.stevekung.fishofthieves.trigger.ItemUsedOnLocationWithNearbyEntityTrigger;
import com.stevekung.fishofthieves.trigger.WaterDripOnBlockTrigger;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.Util;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

public class AdvancementProvider extends FabricAdvancementProvider
{
    private static final Map<Item, BiMap<String, Integer>> BUCKET_TO_VARIANTS_MAP = Util.make(Maps.newHashMap(), map ->
    {
        map.put(FOTItems.SPLASHTAIL_BUCKET, Splashtail.VARIANT_TO_INT);
        map.put(FOTItems.PONDIE_BUCKET, Pondie.VARIANT_TO_INT);
        map.put(FOTItems.ISLEHOPPER_BUCKET, Islehopper.VARIANT_TO_INT);
        map.put(FOTItems.ANCIENTSCALE_BUCKET, Ancientscale.VARIANT_TO_INT);
        map.put(FOTItems.PLENTIFIN_BUCKET, Plentifin.VARIANT_TO_INT);
        map.put(FOTItems.WILDSPLASH_BUCKET, Wildsplash.VARIANT_TO_INT);
        map.put(FOTItems.DEVILFISH_BUCKET, Devilfish.VARIANT_TO_INT);
        map.put(FOTItems.BATTLEGILL_BUCKET, Battlegill.VARIANT_TO_INT);
        map.put(FOTItems.WRECKER_BUCKET, Wrecker.VARIANT_TO_INT);
        map.put(FOTItems.STORMFISH_BUCKET, Stormfish.VARIANT_TO_INT);
    });
    private static final Item[] FRUITS = new Item[] {
            FOTItems.BANANA,
            FOTItems.COCONUT,
            FOTItems.POMEGRANATE,
            FOTItems.MANGO,
            FOTItems.PINEAPPLE
    };

    public AdvancementProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    //@formatter:off
    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer)
    {
        var sallyName = Component.literal("Sally");
        var entityLookup = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        var blockLookup = provider.lookupOrThrow(Registries.BLOCK);
        var itemLookup = provider.lookupOrThrow(Registries.ITEM);

        var advancement = Advancement.Builder.advancement()
                .display(FOTItems.SPLASHTAIL,
                        Component.translatable("advancements.fot.root.title"),
                        Component.translatable("advancements.fot.root.description"),
                        ResourceLocation.withDefaultNamespace("textures/block/tube_coral_block.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("in_water", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setFluid(FluidPredicate.Builder.fluid()
                                        .of(Fluids.WATER))))
                .save(consumer, this.mod("root"));

        var fishCollectors = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement), itemLookup)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.fish_collectors.title"),
                        Component.translatable("advancements.fot.fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(250).addLootTable(FOTLootTables.Advancements.FISH_COLLECTORS))
                .save(consumer, this.mod("fish_collectors"));

        this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(fishCollectors), itemLookup, false)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.master_fish_collectors.title"),
                        Component.translatable("advancements.fot.master_fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(1000).addLootTable(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS))
                .save(consumer, this.mod("master_fish_collectors"));

        this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(fishCollectors), itemLookup, true)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.legendary_fish_collectors.title"),
                        Component.translatable("advancements.fot.legendary_fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(2000).addLootTable(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS))
                .save(consumer, this.mod("legendary_fish_collectors"));

        Advancement.Builder.advancement().parent(advancement).addCriterion(this.getItemName(FOTItems.DEVILFISH_BUCKET),
                        PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                                ItemPredicate.Builder.item().of(itemLookup, FOTItems.DEVILFISH_BUCKET).hasComponents(DataComponentPredicate.builder()
                                        .expect(DataComponents.BUCKET_ENTITY_DATA, CustomData.of(Util.make(new CompoundTag(), compoundTag -> compoundTag.putString(ThievesFish.VARIANT_TAG, DevilfishVariants.LAVA.location().toString())))).build()
                                ), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityLookup, EntityType.AXOLOTL).build()))))
                .display(FOTItems.DEVILFISH,
                        Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.title"),
                        Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("feed_axolotl_with_lava_devilfish"));

        var battlegill = this.getItemName(FOTItems.BATTLEGILL);
        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion(battlegill + "_village",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setStructures(provider.lookupOrThrow(Registries.STRUCTURE).getOrThrow(StructureTags.VILLAGE))).build()), Optional.of(ItemPredicate.Builder.item().of(itemLookup, FOTItems.BATTLEGILL).build())))
                .display(FOTItems.BATTLEGILL,
                        Component.translatable("advancements.fot.so_chill.title"),
                        Component.translatable("advancements.fot.so_chill.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("so_chill"));

        Advancement.Builder.advancement().parent(advancement)
                .display(FOTItems.STORMFISH,
                        Component.translatable("advancements.fot.lightning_straight_to_my_fish.title"),
                        Component.translatable("advancements.fot.lightning_straight_to_my_fish.description"),
                        null, AdvancementType.TASK, true, true, false)
                .addCriterion("lightning_strike_at_stormfish", LightningStrikeTrigger.TriggerInstance.lightningStrike(Optional.of(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).subPredicate(LightningBoltPredicate.blockSetOnFire(MinMaxBounds.Ints.exactly(0))).build()), Optional.of(EntityPredicate.Builder.entity().of(entityLookup, FOTEntities.STORMFISH).build())))
                .save(consumer, this.mod("lightning_straight_to_my_fish"));

        Advancement.Builder.advancement().parent(advancement)
                .display(Items.SPYGLASS,
                        Component.translatable("advancements.fot.spyglass_at_plentifins.title"),
                        Component.translatable("advancements.fot.spyglass_at_plentifins.description"),
                        null, AdvancementType.TASK, true, true, false)
                .addCriterion("spyglass_at_plentifins", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(entityLookup, FOTEntities.PLENTIFIN)).build()), ItemPredicate.Builder.item().of(itemLookup, Items.SPYGLASS)))
                .save(consumer, this.mod("spyglass_at_plentifins"));

        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .display(Items.JUKEBOX,
                        Component.translatable("advancements.fot.play_jukebox_near_fish.title"),
                        Component.translatable("advancements.fot.play_jukebox_near_fish.description"),
                        null, AdvancementType.TASK, true, true, true)
                .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(blockLookup, Blocks.JUKEBOX)),
                        ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.JUKEBOX_PLAYABLE, ItemJukeboxPlayablePredicate.any()),
                        EntityPredicate.Builder.entity().of(entityLookup, FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE)))
                .addCriterion("play_jukebox_near_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(blockLookup, Blocks.JUKEBOX)),
                        ItemPredicate.Builder.item().withSubPredicate(ItemSubPredicates.JUKEBOX_PLAYABLE, ItemJukeboxPlayablePredicate.any()),
                        EntityPredicate.Builder.entity().of(entityLookup, EntityTypeTags.AXOLOTL_HUNT_TARGETS)))
                .save(consumer, this.mod("play_jukebox_near_fish"));

        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion(BuiltInRegistries.ITEM.getKey(Items.NAME_TAG).getPath(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                        ItemPredicate.Builder.item().of(itemLookup, Items.NAME_TAG).hasComponents(DataComponentPredicate.builder().expect(DataComponents.CUSTOM_NAME, sallyName).build()),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityLookup, EntityType.SALMON)))))
                .addCriterion(BuiltInRegistries.ITEM.getKey(Items.SALMON_BUCKET).getPath(), ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockLookup, Blocks.WATER)), ItemPredicate.Builder.item().of(itemLookup, Items.SALMON_BUCKET).hasComponents(DataComponentPredicate.builder().expect(DataComponents.CUSTOM_NAME, sallyName).build())))
                .display(Items.SALMON,
                        Component.translatable("advancements.fot.lost_sally.title"),
                        Component.translatable("advancements.fot.lost_sally.description"),
                        null, AdvancementType.TASK, true, true, true)
                .save(consumer, this.mod("lost_sally"));

        var isTropicalIsland = LocationPredicate.Builder.inBiome(provider.lookupOrThrow(Registries.BIOME).getOrThrow(FOTBiomes.TROPICAL_ISLAND));

        var tropicalIsland = Advancement.Builder.advancement().parent(advancement)
                .addCriterion("explore_tropical_island", PlayerTrigger.TriggerInstance.located(isTropicalIsland))
                .display(FOTBlocks.TROPICAL_MONSTERA,
                        Component.translatable("advancements.fot.explore_tropical_island.title"),
                        Component.translatable("advancements.fot.explore_tropical_island.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("explore_tropical_island"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("mango_gravity", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType()
                                .tag(TagPredicate.is(FOTTags.DamageTypes.IS_MANGO))
                                .source(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.FALLING_BLOCK)))
                        ))
                )
                .display(FOTItems.MANGO,
                        Component.translatable("advancements.fot.mango_gravity.title"),
                        Component.translatable("advancements.fot.mango_gravity.description"),
                        null, AdvancementType.TASK, true, true, true)
                .save(consumer, this.mod("mango_gravity"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("equip_pineapple_block", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                        .equipment(EntityEquipmentPredicate.Builder.equipment()
                                .head(ItemPredicate.Builder.item()
                                        .of(FOTBlocks.RIPE_PINEAPPLE_BLOCK, FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK)))))
                .display(FOTBlocks.RIPE_PINEAPPLE_BLOCK,
                        Component.translatable("advancements.fot.equip_pineapple_block.title"),
                        Component.translatable("advancements.fot.equip_pineapple_block.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("equip_pineapple_block"));

        this.addFruits(Advancement.Builder.advancement()).parent(tropicalIsland)
                .display(FOTItems.BANANA,
                        Component.translatable("advancements.fot.fruit_diet.title"),
                        Component.translatable("advancements.fot.fruit_diet.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, this.mod("fruit_diet"));

        Advancement.Builder.advancement().parent(tropicalIsland).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("water_drip_from_coconut_fronds", WaterDripOnBlockTrigger.TriggerInstance.waterDrip(FOTBlocks.COCONUT_FRONDS))
                .addCriterion("water_drip_from_banana_leaves", WaterDripOnBlockTrigger.TriggerInstance.waterDrip(FOTBlocks.BANANA_LEAVES, isTropicalIsland))
                .display(FOTBlocks.COCONUT_FRONDS,
                        Component.translatable("advancements.fot.island_rainwater.title"),
                        Component.translatable("advancements.fot.island_rainwater.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("island_rainwater"));

        Advancement.Builder.advancement().parent(tropicalIsland).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("has_ancient_sherds", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                        .of(FOTItems.KRAKEN_POTTERY_SHERD, FOTItems.MEGALODON_POTTERY_SHERD)
                        .build()))
                .display(FOTItems.MEGALODON_POTTERY_SHERD,
                        Component.translatable("advancements.fot.ancient_myth.title"),
                        Component.translatable("advancements.fot.ancient_myth.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("ancient_myth"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("crush_pomegranate", FallingAnvilCrushItemTrigger.TriggerInstance.crushItem(ItemPredicate.Builder.item()
                        .of(FOTItems.POMEGRANATE).withCount(MinMaxBounds.Ints.atLeast(8))))
                .display(Items.RED_DYE,
                        Component.translatable("advancements.fot.crush_pomegranate.title"),
                        Component.translatable("advancements.fot.crush_pomegranate.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("crush_pomegranate"));
    }
    //@formatter:on

    private String mod(String name)
    {
        return FishOfThieves.MOD_RESOURCES + name;
    }

    private Advancement.Builder addFishBuckets(Advancement.Builder builder, HolderGetter<Item> itemLookup)
    {
        for (var item : FOTTags.FISH_BUCKETS)
        {
            builder.addCriterion(this.getItemName(item), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(itemLookup, item)));
        }
        return builder;
    }

    private Advancement.Builder addFishVariantsBuckets(Advancement.Builder builder, HolderGetter<Item> itemLookup, boolean trophy)
    {
        for (var bucket : FOTTags.FISH_BUCKETS)
        {
            var variants = BUCKET_TO_VARIANTS_MAP.get(bucket);

            for (var variant : variants.keySet().stream().map(ResourceLocation::parse).toList())
            {
                var compoundTag = new CompoundTag();
                compoundTag.putString(ThievesFish.VARIANT_TAG, variant.toString());

                if (trophy)
                {
                    compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
                    compoundTag.putBoolean(ThievesFish.HAS_FED_TAG, false);
                }
                builder.addCriterion(variant.getPath() + "_" + BuiltInRegistries.ITEM.getKey(bucket).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(itemLookup, bucket).withSubPredicate(FOTItemSubPredicates.BUCKET_ENTITY_DATA, ItemBucketEntityDataPredicate.bucketEntityData(new BucketNbtPredicate(compoundTag)))));
            }
        }
        return builder;
    }

    private Advancement.Builder addFruits(Advancement.Builder builder)
    {
        for (var item : FRUITS)
        {
            builder.addCriterion(this.getItemName(item), ConsumeItemTrigger.TriggerInstance.usedItem(item));
        }
        return builder;
    }

    private String getItemName(Item item)
    {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }
}