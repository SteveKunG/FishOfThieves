package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import com.stevekung.fishofthieves.trigger.ItemUsedOnLocationWithNearbyEntityTrigger;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.Util;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class AdvancementProvider extends FabricAdvancementProvider
{
    private static final Map<Item, Registry<?>> BUCKET_TO_VARIANTS_MAP = Util.make(Maps.newHashMap(), map ->
    {
        map.put(FOTItems.SPLASHTAIL_BUCKET, FOTRegistry.SPLASHTAIL_VARIANT);
        map.put(FOTItems.PONDIE_BUCKET, FOTRegistry.PONDIE_VARIANT);
        map.put(FOTItems.ISLEHOPPER_BUCKET, FOTRegistry.ISLEHOPPER_VARIANT);
        map.put(FOTItems.ANCIENTSCALE_BUCKET, FOTRegistry.ANCIENTSCALE_VARIANT);
        map.put(FOTItems.PLENTIFIN_BUCKET, FOTRegistry.PLENTIFIN_VARIANT);
        map.put(FOTItems.WILDSPLASH_BUCKET, FOTRegistry.WILDSPLASH_VARIANT);
        map.put(FOTItems.DEVILFISH_BUCKET, FOTRegistry.DEVILFISH_VARIANT);
        map.put(FOTItems.BATTLEGILL_BUCKET, FOTRegistry.BATTLEGILL_VARIANT);
        map.put(FOTItems.WRECKER_BUCKET, FOTRegistry.WRECKER_VARIANT);
        map.put(FOTItems.STORMFISH_BUCKET, FOTRegistry.STORMFISH_VARIANT);
    });

    public AdvancementProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    //@formatter:off
    @Override
    public void generateAdvancement(Consumer<AdvancementHolder> consumer)
    {
        var sallyName = Util.make(new CompoundTag(), compound ->
        {
            var displayCompound = new CompoundTag();
            displayCompound.putString(ItemStack.TAG_DISPLAY_NAME, Component.Serializer.toJson(Component.literal("Sally")));
            compound.put(ItemStack.TAG_DISPLAY, displayCompound);
        });

        var advancement = Advancement.Builder.advancement()
                .display(FOTItems.SPLASHTAIL,
                        Component.translatable("advancements.fot.root.title"),
                        Component.translatable("advancements.fot.root.description"),
                        new ResourceLocation("textures/block/tube_coral_block.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("in_water", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setFluid(FluidPredicate.Builder.fluid()
                                        .of(FluidTags.WATER))))
                .save(consumer, this.mod("root"));

        var advancement2 = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement))
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.fish_collectors.title"),
                        Component.translatable("advancements.fot.fish_collectors.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(250).addLootTable(FOTLootTables.Advancements.FISH_COLLECTORS))
                .save(consumer, this.mod("fish_collectors"));

        this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), false)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.master_fish_collectors.title"),
                        Component.translatable("advancements.fot.master_fish_collectors.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(1000).addLootTable(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS))
                .save(consumer, this.mod("master_fish_collectors"));

        this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(advancement2), true)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.legendary_fish_collectors.title"),
                        Component.translatable("advancements.fot.legendary_fish_collectors.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(2000).addLootTable(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS))
                .save(consumer, this.mod("legendary_fish_collectors"));

        Advancement.Builder.advancement().parent(advancement).addCriterion(BuiltInRegistries.ITEM.getKey(FOTItems.DEVILFISH_BUCKET).getPath(),
                        PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                                ItemPredicate.Builder.item().of(FOTItems.DEVILFISH_BUCKET).hasNbt(Util.make(new CompoundTag(), compound -> compound.putString(ThievesFish.VARIANT_TAG, FOTRegistry.DEVILFISH_VARIANT.getKey(DevilfishVariants.LAVA).toString()))),
                                Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.AXOLOTL).build()))))
                .display(FOTItems.DEVILFISH,
                        Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.title"),
                        Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("feed_axolotl_with_lava_devilfish"));

        var battlegill = BuiltInRegistries.ITEM.getKey(FOTItems.BATTLEGILL).getPath();
        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion(battlegill + "_village_plains",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inStructure(BuiltinStructures.VILLAGE_PLAINS)).build()), Optional.of(ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build())))
                .addCriterion(battlegill + "_village_desert",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inStructure(BuiltinStructures.VILLAGE_DESERT)).build()), Optional.of(ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build())))
                .addCriterion(battlegill + "_village_savanna",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inStructure(BuiltinStructures.VILLAGE_SAVANNA)).build()), Optional.of(ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build())))
                .addCriterion(battlegill + "_village_snowy",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inStructure(BuiltinStructures.VILLAGE_SNOWY)).build()), Optional.of(ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build())))
                .addCriterion(battlegill + "_village_taiga",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inStructure(BuiltinStructures.VILLAGE_TAIGA)).build()), Optional.of(ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build())))
                .display(FOTItems.BATTLEGILL,
                        Component.translatable("advancements.fot.so_chill.title"),
                        Component.translatable("advancements.fot.so_chill.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("so_chill"));

        Advancement.Builder.advancement().parent(advancement)
                .display(FOTItems.STORMFISH,
                        Component.translatable("advancements.fot.lightning_straight_to_my_fish.title"),
                        Component.translatable("advancements.fot.lightning_straight_to_my_fish.description"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("lightning_strike_at_stormfish", LightningStrikeTrigger.TriggerInstance.lightningStrike(Optional.of(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).subPredicate(LightningBoltPredicate.blockSetOnFire(MinMaxBounds.Ints.exactly(0))).build()), Optional.of(EntityPredicate.Builder.entity().of(FOTEntities.STORMFISH).build())))
                .save(consumer, this.mod("lightning_straight_to_my_fish"));

        Advancement.Builder.advancement().parent(advancement)
                .display(Items.SPYGLASS,
                        Component.translatable("advancements.fot.spyglass_at_plentifins.title"),
                        Component.translatable("advancements.fot.spyglass_at_plentifins.description"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("spyglass_at_plentifins", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(FOTEntities.PLENTIFIN)).build()), ItemPredicate.Builder.item().of(Items.SPYGLASS)))
                .save(consumer, this.mod("spyglass_at_plentifins"));

        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .display(Items.JUKEBOX,
                        Component.translatable("advancements.fot.play_jukebox_near_fish.title"),
                        Component.translatable("advancements.fot.play_jukebox_near_fish.description"),
                        null, FrameType.TASK, true, true, true)
                .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX)),
                        ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                        EntityPredicate.Builder.entity().of(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE)))
                .addCriterion("play_jukebox_near_fish", ItemUsedOnLocationWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX)),
                        ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                        EntityPredicate.Builder.entity().of(EntityTypeTags.AXOLOTL_HUNT_TARGETS)))
                .save(consumer, this.mod("play_jukebox_near_fish"));

        Advancement.Builder.advancement().parent(advancement).requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion(BuiltInRegistries.ITEM.getKey(Items.NAME_TAG).getPath(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(),
                        ItemPredicate.Builder.item().of(Items.NAME_TAG).hasNbt(sallyName),
                        Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.SALMON)))))
                .addCriterion(BuiltInRegistries.ITEM.getKey(Items.SALMON_BUCKET).getPath(), ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(Blocks.WATER)), ItemPredicate.Builder.item().of(Items.SALMON_BUCKET).hasNbt(sallyName)))
                .display(Items.SALMON,
                        Component.translatable("advancements.fot.lost_sally.title"),
                        Component.translatable("advancements.fot.lost_sally.description"),
                        null, FrameType.TASK, true, true, true)
                .save(consumer, this.mod("lost_sally"));
    }
    //@formatter:on

    private String mod(String name)
    {
        return FishOfThieves.MOD_RESOURCES + name;
    }

    private Advancement.Builder addFishBuckets(Advancement.Builder builder)
    {
        for (var item : FOTTags.FISH_BUCKETS)
        {
            builder.addCriterion(BuiltInRegistries.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item)));
        }
        return builder;
    }

    private Advancement.Builder addFishVariantsBuckets(Advancement.Builder builder, boolean trophy)
    {
        for (var item : FOTTags.FISH_BUCKETS)
        {
            for (var variant : Sets.newTreeSet(BUCKET_TO_VARIANTS_MAP.get(item).keySet()))
            {
                builder.addCriterion(variant.getPath() + "_" + BuiltInRegistries.ITEM.getKey(item).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).hasNbt(Util.make(new CompoundTag(), compound ->
                {
                    compound.putString(ThievesFish.VARIANT_TAG, variant.toString());

                    if (trophy)
                    {
                        compound.putBoolean(ThievesFish.TROPHY_TAG, true);
                        compound.putBoolean(ThievesFish.HAS_FED_TAG, false);
                    }
                }))));
            }
        }
        return builder;
    }
}