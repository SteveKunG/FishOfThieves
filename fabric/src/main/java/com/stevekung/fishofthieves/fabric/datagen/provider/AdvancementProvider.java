package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.BananaLeavesBlock;
import com.stevekung.fishofthieves.block.CoconutFrondsBlock;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.item.FOTMobBucketItem;
import com.stevekung.fishofthieves.item.predicate.BucketNbtPredicate;
import com.stevekung.fishofthieves.item.predicate.ItemBucketEntityDataPredicate;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import com.stevekung.fishofthieves.registry.variant.StormfishVariants;
import com.stevekung.fishofthieves.trigger.*;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.predicates.*;
import net.minecraft.advancements.predicates.entity.*;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.JukeboxPlayablePredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

public class AdvancementProvider extends FabricAdvancementProvider
{
    private static final Map<Item, ResourceKey<? extends Registry<? extends AbstractFishVariant>>> BUCKET_TO_VARIANTS_MAP = Map.of(
            FOTItems.SPLASHTAIL_BUCKET, FOTRegistries.SPLASHTAIL_VARIANT,
            FOTItems.PONDIE_BUCKET, FOTRegistries.PONDIE_VARIANT,
            FOTItems.ISLEHOPPER_BUCKET, FOTRegistries.ISLEHOPPER_VARIANT,
            FOTItems.ANCIENTSCALE_BUCKET, FOTRegistries.ANCIENTSCALE_VARIANT,
            FOTItems.PLENTIFIN_BUCKET, FOTRegistries.PLENTIFIN_VARIANT,
            FOTItems.WILDSPLASH_BUCKET, FOTRegistries.WILDSPLASH_VARIANT,
            FOTItems.DEVILFISH_BUCKET, FOTRegistries.DEVILFISH_VARIANT,
            FOTItems.BATTLEGILL_BUCKET, FOTRegistries.BATTLEGILL_VARIANT,
            FOTItems.WRECKER_BUCKET, FOTRegistries.WRECKER_VARIANT,
            FOTItems.STORMFISH_BUCKET, FOTRegistries.STORMFISH_VARIANT
    );
    private static final Item[] FRUITS = new Item[] {
            FOTItems.BANANA,
            FOTItems.COCONUT,
            FOTItems.POMEGRANATE,
            FOTItems.MANGO,
            FOTItems.PINEAPPLE
    };

    public AdvancementProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer)
    {
        var sallyName = Component.literal("Sally");
        var entityLookup = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        var blockLookup = provider.lookupOrThrow(Registries.BLOCK);
        var itemLookup = provider.lookupOrThrow(Registries.ITEM);
        var lootTableLookup = provider.lookupOrThrow(Registries.LOOT_TABLE);
        var battlegillLookup = provider.lookupOrThrow(FOTRegistries.BATTLEGILL_VARIANT);
        var stormfishLookup = provider.lookupOrThrow(FOTRegistries.STORMFISH_VARIANT);

        var advancement = Advancement.Builder.advancement()
                .display(FOTItems.SPLASHTAIL,
                        Component.translatable("advancements.fishofthieves.root.title"),
                        Component.translatable("advancements.fishofthieves.root.description"),
                        FishOfThieves.id("gui/advancements/backgrounds/main"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("in_water", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setFluid(FluidPredicate.Builder.fluid()
                                        .of(Fluids.WATER))))
                .save(consumer, this.mod("root"));

        var fishCollectors = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement), itemLookup)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fishofthieves.fish_collectors.title"),
                        Component.translatable("advancements.fishofthieves.fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(250).addLootTable(lootTableLookup.getOrThrow(FOTLootTables.Advancements.FISH_COLLECTORS)))
                .save(consumer, this.mod("fish_collectors"));

        this.addFishVariantsBuckets(provider, Advancement.Builder.advancement().parent(fishCollectors), itemLookup, false)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fishofthieves.master_fish_collectors.title"),
                        Component.translatable("advancements.fishofthieves.master_fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(1000).addLootTable(lootTableLookup.getOrThrow(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS)))
                .save(consumer, this.mod("master_fish_collectors"));

        this.addFishVariantsBuckets(provider, Advancement.Builder.advancement().parent(fishCollectors), itemLookup, true)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fishofthieves.legendary_fish_collectors.title"),
                        Component.translatable("advancements.fishofthieves.legendary_fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(2000).addLootTable(lootTableLookup.getOrThrow(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS)))
                .save(consumer, this.mod("legendary_fish_collectors"));

        this.addTreasuredFishVariantsBuckets(provider, Advancement.Builder.advancement().parent(fishCollectors), itemLookup)
                .display(FOTMobBucketItem.advancementTemplate(FOTItems.STORMFISH_BUCKET, stormfishLookup.getOrThrow(StormfishVariants.STARSHINE)),
                        Component.translatable("advancements.fishofthieves.treasured_fish_collectors.title"),
                        Component.translatable("advancements.fishofthieves.treasured_fish_collectors.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(3000).addLootTable(lootTableLookup.getOrThrow(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS)))
                .save(consumer, this.mod("treasured_fish_collectors"));

        Advancement.Builder.advancement().parent(advancement).addCriterion(this.getItemName(FOTItems.DEVILFISH_BUCKET),
                        PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                                ItemPredicate.Builder.item().of(itemLookup, FOTItems.DEVILFISH_BUCKET)
                                        .withComponents(
                                                DataComponentMatchers.Builder.components()
                                                        .exact(DataComponentExactPredicate.builder()
                                                                .expect(DataComponents.BUCKET_ENTITY_DATA, CustomData.of(Util.make(
                                                                        new CompoundTag(), compoundTag -> compoundTag
                                                                                .putString(FOTRegistries.DEVILFISH_VARIANT.identifier().getPath(), DevilfishVariants.LAVA.identifier().toString())))).build())
                                                        .build()
                                        ), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityLookup, EntityTypes.AXOLOTL).build()))))
                .display(FOTItem.advancementTemplate(FOTItems.DEVILFISH, "devilfish_variant", "fishofthieves:lava"),
                        Component.translatable("advancements.fishofthieves.feed_axolotl_with_lava_devilfish.title"),
                        Component.translatable("advancements.fishofthieves.feed_axolotl_with_lava_devilfish.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("feed_axolotl_with_lava_devilfish"));

        var battlegill = this.getItemName(FOTItems.BATTLEGILL);
        var battlegillAdvancement = Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion(battlegill + "_village",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setStructures(provider.lookupOrThrow(Registries.STRUCTURE).getOrThrow(StructureTags.VILLAGE))).build()), Optional.of(ItemPredicate.Builder.item().of(itemLookup, FOTItems.BATTLEGILL).build())))
                .display(FOTItems.BATTLEGILL,
                        Component.translatable("advancements.fishofthieves.so_chill.title"),
                        Component.translatable("advancements.fishofthieves.so_chill.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("so_chill"));

        Advancement.Builder.advancement().parent(advancement)
                .display(FOTItems.STORMFISH,
                        Component.translatable("advancements.fishofthieves.lightning_straight_to_my_fish.title"),
                        Component.translatable("advancements.fishofthieves.lightning_straight_to_my_fish.description"),
                        null, AdvancementType.TASK, true, true, false)
                .addCriterion("lightning_strike_at_stormfish", LightningStrikeTrigger.TriggerInstance.lightningStrike(Optional.of(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).put(LightningBoltPredicate.CODEC, LightningBoltPredicate.blockSetOnFire(MinMaxBounds.Ints.exactly(0))).build()), Optional.of(EntityPredicate.Builder.entity().of(entityLookup, FOTEntities.STORMFISH).build())))
                .save(consumer, this.mod("lightning_straight_to_my_fish"));

        Advancement.Builder.advancement().parent(advancement)
                .display(Items.SPYGLASS,
                        Component.translatable("advancements.fishofthieves.spyglass_at_plentifins.title"),
                        Component.translatable("advancements.fishofthieves.spyglass_at_plentifins.description"),
                        null, AdvancementType.TASK, true, true, false)
                .addCriterion("spyglass_at_plentifins", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().put(PlayerPredicate.CODEC, PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(entityLookup, FOTEntities.PLENTIFIN)).build()), ItemPredicate.Builder.item().of(itemLookup, Items.SPYGLASS)))
                .save(consumer, this.mod("spyglass_at_plentifins"));

        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .display(Items.JUKEBOX,
                        Component.translatable("advancements.fishofthieves.play_jukebox_near_fish.title"),
                        Component.translatable("advancements.fishofthieves.play_jukebox_near_fish.description"),
                        null, AdvancementType.TASK, true, true, true)
                .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(blockLookup, Blocks.JUKEBOX)),
                        ItemPredicate.Builder.item().withComponents(
                                DataComponentMatchers.Builder.components()
                                        .partial(DataComponentPredicates.JUKEBOX_PLAYABLE, JukeboxPlayablePredicate.any())
                                        .build()
                        ),
                        EntityPredicate.Builder.entity().of(entityLookup, FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE)))
                .addCriterion("play_jukebox_near_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(blockLookup, Blocks.JUKEBOX)),
                        ItemPredicate.Builder.item().withComponents(
                                DataComponentMatchers.Builder.components()
                                        .partial(DataComponentPredicates.JUKEBOX_PLAYABLE, JukeboxPlayablePredicate.any())
                                        .build()
                        ),
                        EntityPredicate.Builder.entity().of(entityLookup, EntityTypeTags.AXOLOTL_HUNT_TARGETS)))
                .save(consumer, this.mod("play_jukebox_near_fish"));

        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion(BuiltInRegistries.ITEM.getKey(Items.NAME_TAG).getPath(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                        ItemPredicate.Builder.item().of(itemLookup, Items.NAME_TAG).withComponents(
                                DataComponentMatchers.Builder.components()
                                        .exact(DataComponentExactPredicate.builder().expect(DataComponents.CUSTOM_NAME, sallyName)
                                                .build())
                                        .build()
                        ),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(entityLookup, EntityTypes.SALMON)))))
                .addCriterion(BuiltInRegistries.ITEM.getKey(Items.SALMON_BUCKET).getPath(), ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(blockLookup, Blocks.WATER)),
                        ItemPredicate.Builder.item().of(itemLookup, Items.SALMON_BUCKET)
                                .withComponents(
                                        DataComponentMatchers.Builder.components()
                                                .exact(DataComponentExactPredicate.builder().expect(DataComponents.CUSTOM_NAME, sallyName)
                                                        .build())
                                                .build()
                                )))
                .display(Items.SALMON,
                        Component.translatable("advancements.fishofthieves.lost_sally.title"),
                        Component.translatable("advancements.fishofthieves.lost_sally.description"),
                        null, AdvancementType.TASK, true, true, true)
                .save(consumer, this.mod("lost_sally"));

        var isTropicalIsland = LocationPredicate.Builder.inBiome(provider.lookupOrThrow(Registries.BIOME).getOrThrow(FOTBiomes.TROPICAL_ISLAND));

        var tropicalIsland = Advancement.Builder.advancement().parent(advancement)
                .addCriterion("explore_tropical_island", PlayerTrigger.TriggerInstance.located(isTropicalIsland))
                .display(FOTBlocks.TROPICAL_MONSTERA,
                        Component.translatable("advancements.fishofthieves.explore_tropical_island.title"),
                        Component.translatable("advancements.fishofthieves.explore_tropical_island.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("explore_tropical_island"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("mango_gravity", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                        DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType()
                                .tag(TagPredicate.is(provider.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(FOTTags.DamageTypes.IS_MANGO)))
                                .source(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityLookup, EntityTypes.FALLING_BLOCK)))
                        ))
                )
                .display(FOTItems.MANGO,
                        Component.translatable("advancements.fishofthieves.mango_gravity.title"),
                        Component.translatable("advancements.fishofthieves.mango_gravity.description"),
                        null, AdvancementType.TASK, true, true, true)
                .save(consumer, this.mod("mango_gravity"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("equip_pineapple_block", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                        .equipment(EntityEquipmentPredicate.Builder.equipment()
                                .head(ItemPredicate.Builder.item()
                                        .of(itemLookup, FOTBlocks.RIPE_PINEAPPLE_BLOCK, FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK)))))
                .display(FOTBlocks.RIPE_PINEAPPLE_BLOCK,
                        Component.translatable("advancements.fishofthieves.equip_pineapple_block.title"),
                        Component.translatable("advancements.fishofthieves.equip_pineapple_block.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("equip_pineapple_block"));

        this.addFruits(Advancement.Builder.advancement(), itemLookup).parent(tropicalIsland)
                .display(FOTItems.BANANA,
                        Component.translatable("advancements.fishofthieves.fruit_diet.title"),
                        Component.translatable("advancements.fishofthieves.fruit_diet.description"),
                        null, AdvancementType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, this.mod("fruit_diet"));

        Advancement.Builder.advancement().parent(tropicalIsland).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("water_drip_from_coconut_fronds", WaterDripOnBlockTrigger.TriggerInstance.waterDrip(FOTBlocks.COCONUT_FRONDS, StatePropertiesPredicate.Builder.properties().hasProperty(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL), isTropicalIsland))
                .addCriterion("water_drip_from_banana_leaves", WaterDripOnBlockTrigger.TriggerInstance.waterDrip(FOTBlocks.BANANA_LEAVES, StatePropertiesPredicate.Builder.properties().hasProperty(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL), isTropicalIsland))
                .display(FOTBlocks.COCONUT_FRONDS,
                        Component.translatable("advancements.fishofthieves.island_rainwater.title"),
                        Component.translatable("advancements.fishofthieves.island_rainwater.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("island_rainwater"));

        Advancement.Builder.advancement().parent(tropicalIsland).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("has_ancient_sherds", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                        .of(itemLookup, FOTItems.KRAKEN_POTTERY_SHERD, FOTItems.MEGALODON_POTTERY_SHERD)
                        .build()))
                .display(FOTItems.MEGALODON_POTTERY_SHERD,
                        Component.translatable("advancements.fishofthieves.ancient_myth.title"),
                        Component.translatable("advancements.fishofthieves.ancient_myth.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("ancient_myth"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("crush_pomegranate", FallingAnvilCrushItemTrigger.TriggerInstance.crushItem(ItemPredicate.Builder.item()
                        .of(itemLookup, FOTItems.POMEGRANATE).withCount(MinMaxBounds.Ints.atLeast(8))))
                .display(Items.DYE.red(),
                        Component.translatable("advancements.fishofthieves.crush_pomegranate.title"),
                        Component.translatable("advancements.fishofthieves.crush_pomegranate.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("crush_pomegranate"));

        Advancement.Builder.advancement().parent(advancement)
                .addCriterion("taste_the_deep", ConsumeItemTrigger.TriggerInstance.usedItem(itemLookup, FOTItems.GUARDIAN_FRUIT))
                .display(FOTItems.GUARDIAN_FRUIT,
                        Component.translatable("advancements.fishofthieves.taste_the_deep.title"),
                        Component.translatable("advancements.fishofthieves.taste_the_deep.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("taste_the_deep"));

        Advancement.Builder.advancement().parent(advancement)
                .addCriterion("shoal_hunter", ParticipateShoalTrigger.TriggerInstance.participateShoal())
                .display(Items.FISHING_ROD,
                        Component.translatable("advancements.fishofthieves.shoal_hunter.title"),
                        Component.translatable("advancements.fishofthieves.shoal_hunter.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("shoal_hunter"));

        Advancement.Builder.advancement().parent(battlegillAdvancement)
                .addCriterion("drunken_sailor", FollowLivingWithEffectTrigger.TriggerInstance.entityWithEffect(EntityPredicate.Builder.entity().of(entityLookup, FOTEntities.BATTLEGILL).components(
                        DataComponentMatchers.Builder.components()
                                .exact(DataComponentExactPredicate.expect(FOTDataComponentTypes.BATTLEGILL_VARIANT, battlegillLookup.getOrThrow(BattlegillVariants.RUM)))
                                .build()
                ), MobEffectsPredicate.Builder.effects().and(MobEffects.NAUSEA)))
                .display(FOTItem.advancementTemplate(FOTItems.BATTLEGILL, "battlegill_variant", "fishofthieves:rum"),
                        Component.translatable("advancements.fishofthieves.drunken_sailor.title"),
                        Component.translatable("advancements.fishofthieves.drunken_sailor.description"),
                        null, AdvancementType.TASK, true, true, false)
                .save(consumer, this.mod("drunken_sailor"));
    }

    private Identifier mod(String name)
    {
        return FishOfThieves.id(name);
    }

    private Advancement.Builder addFishBuckets(Advancement.Builder builder, HolderGetter<Item> itemLookup)
    {
        for (var item : FOTTags.FISH_BUCKETS)
        {
            builder.addCriterion(this.getItemName(item), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(itemLookup, item)));
        }
        return builder;
    }

    private Advancement.Builder addFishVariantsBuckets(HolderLookup.Provider provider, Advancement.Builder builder, HolderGetter<Item> itemLookup, boolean trophy)
    {
        for (var bucket : Arrays.stream(FOTTags.FISH_BUCKETS).map(FOTMobBucketItem.class::cast).toList())
        {
            var resourceKey = BUCKET_TO_VARIANTS_MAP.get(bucket);

            for (var holder : provider.lookupOrThrow(resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList())
            {
                if (holder.value().treasured().isPresent())
                {
                    continue;
                }

                var compoundTag = new CompoundTag();
                compoundTag.putString(holder.key().registry().getPath(), holder.key().identifier().toString());

                if (trophy)
                {
                    compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
                    compoundTag.putBoolean(ThievesFish.HAS_FED_TAG, false);
                }
                builder.addCriterion(holder.key().identifier().getPath() + "_" + BuiltInRegistries.ITEM.getKey(bucket).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item()
                        .of(itemLookup, bucket)
                        .withComponents(
                                DataComponentMatchers.Builder.components()
                                        .partial(FOTDataComponentPredicates.BUCKET_ENTITY_DATA, ItemBucketEntityDataPredicate.bucketEntityData(new BucketNbtPredicate(compoundTag)))
                                        .build()
                        )));
            }
        }
        return builder;
    }

    private Advancement.Builder addTreasuredFishVariantsBuckets(HolderLookup.Provider provider, Advancement.Builder builder, HolderGetter<Item> itemLookup)
    {
        for (var bucket : Arrays.stream(FOTTags.FISH_BUCKETS).map(FOTMobBucketItem.class::cast).toList())
        {
            var resourceKey = BUCKET_TO_VARIANTS_MAP.get(bucket);

            for (var holder : provider.lookupOrThrow(resourceKey).listElements().sorted(AbstractFishVariant.COMPARATOR).toList())
            {
                if (holder.value().treasured().isEmpty())
                {
                    continue;
                }

                var compoundTag = new CompoundTag();
                compoundTag.putString(holder.key().registry().getPath(), holder.key().identifier().toString());
                compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
                compoundTag.putBoolean(ThievesFish.HAS_FED_TAG, false);

                builder.addCriterion(holder.key().identifier().getPath() + "_" + BuiltInRegistries.ITEM.getKey(bucket).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item()
                        .of(itemLookup, bucket)
                        .withComponents(
                                DataComponentMatchers.Builder.components()
                                        .partial(FOTDataComponentPredicates.BUCKET_ENTITY_DATA, ItemBucketEntityDataPredicate.bucketEntityData(new BucketNbtPredicate(compoundTag)))
                                        .build()
                        )));
            }
        }
        return builder;
    }

    private Advancement.Builder addFruits(Advancement.Builder builder, HolderGetter<Item> itemRegistry)
    {
        for (var item : FRUITS)
        {
            builder.addCriterion(this.getItemName(item), ConsumeItemTrigger.TriggerInstance.usedItem(itemRegistry, item));
        }
        return builder;
    }

    private String getItemName(Item item)
    {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }
}