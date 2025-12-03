package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.Map;
import java.util.TreeSet;
import java.util.function.Consumer;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.BananaLeavesBlock;
import com.stevekung.fishofthieves.block.CoconutFrondsBlock;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.item.FOTItem;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;
import com.stevekung.fishofthieves.registry.variant.DevilfishVariants;
import com.stevekung.fishofthieves.trigger.*;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class AdvancementProvider extends FabricAdvancementProvider
{
    private static final Map<Item, Registry<? extends FishData>> BUCKET_TO_VARIANTS_MAP = Map.of(
            FOTItems.SPLASHTAIL_BUCKET, FOTRegistry.SPLASHTAIL_VARIANT,
            FOTItems.PONDIE_BUCKET, FOTRegistry.PONDIE_VARIANT,
            FOTItems.ISLEHOPPER_BUCKET, FOTRegistry.ISLEHOPPER_VARIANT,
            FOTItems.ANCIENTSCALE_BUCKET, FOTRegistry.ANCIENTSCALE_VARIANT,
            FOTItems.PLENTIFIN_BUCKET, FOTRegistry.PLENTIFIN_VARIANT,
            FOTItems.WILDSPLASH_BUCKET, FOTRegistry.WILDSPLASH_VARIANT,
            FOTItems.DEVILFISH_BUCKET, FOTRegistry.DEVILFISH_VARIANT,
            FOTItems.BATTLEGILL_BUCKET, FOTRegistry.BATTLEGILL_VARIANT,
            FOTItems.WRECKER_BUCKET, FOTRegistry.WRECKER_VARIANT,
            FOTItems.STORMFISH_BUCKET, FOTRegistry.STORMFISH_VARIANT);

    private static final Item[] FRUITS = new Item[] {
            FOTItems.BANANA,
            FOTItems.COCONUT,
            FOTItems.POMEGRANATE,
            FOTItems.MANGO,
            FOTItems.PINEAPPLE
    };

    public AdvancementProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer)
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
                                        .of(FluidTags.WATER).build()).build()))
                .save(consumer, this.mod("root"));

        var fishCollectors = this.addFishBuckets(Advancement.Builder.advancement().parent(advancement))
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.fish_collectors.title"),
                        Component.translatable("advancements.fot.fish_collectors.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(250).addLootTable(FOTLootTables.Advancements.FISH_COLLECTORS))
                .save(consumer, this.mod("fish_collectors"));

        this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(fishCollectors), false)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.master_fish_collectors.title"),
                        Component.translatable("advancements.fot.master_fish_collectors.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(1000).addLootTable(FOTLootTables.Advancements.MASTER_FISH_COLLECTORS))
                .save(consumer, this.mod("master_fish_collectors"));

        this.addFishVariantsBuckets(Advancement.Builder.advancement().parent(fishCollectors), true)
                .display(FOTItems.SPLASHTAIL_BUCKET,
                        Component.translatable("advancements.fot.legendary_fish_collectors.title"),
                        Component.translatable("advancements.fot.legendary_fish_collectors.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(2000).addLootTable(FOTLootTables.Advancements.LEGENDARY_FISH_COLLECTORS))
                .save(consumer, this.mod("legendary_fish_collectors"));

        Advancement.Builder.advancement().parent(advancement).addCriterion(this.getItemName(FOTItems.DEVILFISH_BUCKET),
                        PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ContextAwarePredicate.ANY,
                                ItemPredicate.Builder.item().of(FOTItems.DEVILFISH_BUCKET).hasNbt(Util.make(new CompoundTag(), compound -> compound.putString(ThievesFish.VARIANT_TAG, FOTRegistry.DEVILFISH_VARIANT.getKey(DevilfishVariants.LAVA).toString()))),
                                EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.AXOLOTL).build())))
                .display(FOTItems.DEVILFISH,
                        Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.title"),
                        Component.translatable("advancements.fot.feed_axolotl_with_lava_devilfish.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("feed_axolotl_with_lava_devilfish"));

        var battlegill = this.getItemName(FOTItems.BATTLEGILL);
        var battlegillAdvancement = Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                .addCriterion(battlegill + "_village_plains",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_PLAINS)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                .addCriterion(battlegill + "_village_desert",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_DESERT)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                .addCriterion(battlegill + "_village_savanna",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_SAVANNA)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                .addCriterion(battlegill + "_village_snowy",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_SNOWY)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
                .addCriterion(battlegill + "_village_taiga",
                        FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.Builder.entity().located(LocationPredicate.inStructure(BuiltinStructures.VILLAGE_TAIGA)).build(), ItemPredicate.Builder.item().of(FOTItems.BATTLEGILL).build()))
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
                .addCriterion("lightning_strike_at_stormfish", LightningStrikeTrigger.TriggerInstance.lighthingStrike(EntityPredicate.Builder.entity().distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(16.0))).subPredicate(LighthingBoltPredicate.blockSetOnFire(MinMaxBounds.Ints.exactly(0))).build(), EntityPredicate.Builder.entity().of(FOTEntities.STORMFISH).build()))
                .save(consumer, this.mod("lightning_straight_to_my_fish"));

        Advancement.Builder.advancement().parent(advancement)
                .display(Items.SPYGLASS,
                        Component.translatable("advancements.fot.spyglass_at_plentifins.title"),
                        Component.translatable("advancements.fot.spyglass_at_plentifins.description"),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("spyglass_at_plentifins", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(FOTEntities.PLENTIFIN).build()).build()), ItemPredicate.Builder.item().of(Items.SPYGLASS)))
                .save(consumer, this.mod("spyglass_at_plentifins"));

        Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                .display(Items.JUKEBOX,
                        Component.translatable("advancements.fot.play_jukebox_near_fish.title"),
                        Component.translatable("advancements.fot.play_jukebox_near_fish.description"),
                        null, FrameType.TASK, true, true, true)
                .addCriterion("play_jukebox_near_thieves_fish", ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX).build()),
                        ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                        EntityPredicate.wrap(EntityPredicate.Builder.entity().of(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).build())))
                .addCriterion("play_jukebox_near_fish", ItemUsedOnBlockWithNearbyEntityTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location()
                                .setBlock(BlockPredicate.Builder.block().of(Blocks.JUKEBOX).build()),
                        ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS),
                        EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityTypeTags.AXOLOTL_HUNT_TARGETS).build())))
                .save(consumer, this.mod("play_jukebox_near_fish"));

        Advancement.Builder.advancement().parent(advancement).requirements(RequirementsStrategy.OR)
                .addCriterion(this.getItemName(Items.NAME_TAG), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ContextAwarePredicate.ANY,
                        ItemPredicate.Builder.item().of(Items.NAME_TAG).hasNbt(sallyName),
                        EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.SALMON).build())))
                .addCriterion(this.getItemName(Items.SALMON_BUCKET), ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(Blocks.WATER).build()), ItemPredicate.Builder.item().of(Items.SALMON_BUCKET).hasNbt(sallyName)))
                .display(Items.SALMON,
                        Component.translatable("advancements.fot.lost_sally.title"),
                        Component.translatable("advancements.fot.lost_sally.description"),
                        null, FrameType.TASK, true, true, true)
                .save(consumer, this.mod("lost_sally"));

        var tropicalIsland = Advancement.Builder.advancement().parent(advancement)
                .addCriterion("explore_tropical_island", PlayerTrigger.TriggerInstance.located(LocationPredicate.inBiome(FOTBiomes.TROPICAL_ISLAND)))
                .display(FOTBlocks.TROPICAL_MONSTERA,
                        Component.translatable("advancements.fot.explore_tropical_island.title"),
                        Component.translatable("advancements.fot.explore_tropical_island.description"),
                        null, FrameType.TASK, true, true, false)
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
                        null, FrameType.TASK, true, true, true)
                .save(consumer, this.mod("mango_gravity"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("equip_pineapple_block", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                        .equipment(EntityEquipmentPredicate.Builder.equipment()
                                .head(ItemPredicate.Builder.item()
                                        .of(FOTBlocks.RIPE_PINEAPPLE_BLOCK, FOTBlocks.CROWNLESS_RIPE_PINEAPPLE_BLOCK, FOTBlocks.UNDERRIPE_PINEAPPLE_BLOCK)
                                        .build())
                                .build())
                        .build()))
                .display(FOTBlocks.RIPE_PINEAPPLE_BLOCK,
                        Component.translatable("advancements.fot.equip_pineapple_block.title"),
                        Component.translatable("advancements.fot.equip_pineapple_block.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("equip_pineapple_block"));

        this.addFruits(Advancement.Builder.advancement()).parent(tropicalIsland)
                .display(FOTItems.BANANA,
                        Component.translatable("advancements.fot.fruit_diet.title"),
                        Component.translatable("advancements.fot.fruit_diet.description"),
                        null, FrameType.CHALLENGE, true, true, false)
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, this.mod("fruit_diet"));

        var isTropicalIsland = LocationPredicate.Builder.location().setBiome(FOTBiomes.TROPICAL_ISLAND);

        Advancement.Builder.advancement().parent(tropicalIsland).requirements(RequirementsStrategy.OR)
                .addCriterion("water_drip_from_coconut_fronds", WaterDripOnBlockTrigger.TriggerInstance.waterDrip(FOTBlocks.COCONUT_FRONDS, StatePropertiesPredicate.Builder.properties().hasProperty(CoconutFrondsBlock.PART, CoconutFrondsBlock.Part.TAIL), isTropicalIsland))
                .addCriterion("water_drip_from_banana_leaves", WaterDripOnBlockTrigger.TriggerInstance.waterDrip(FOTBlocks.BANANA_LEAVES, StatePropertiesPredicate.Builder.properties().hasProperty(BananaLeavesBlock.PART, BananaLeavesBlock.Part.TAIL), isTropicalIsland))
                .display(FOTBlocks.COCONUT_FRONDS,
                        Component.translatable("advancements.fot.island_rainwater.title"),
                        Component.translatable("advancements.fot.island_rainwater.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("island_rainwater"));

        Advancement.Builder.advancement().parent(tropicalIsland).requirements(RequirementsStrategy.OR)
                .addCriterion("has_ancient_sherds", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
                        .of(FOTItems.KRAKEN_POTTERY_SHERD, FOTItems.MEGALODON_POTTERY_SHERD)
                        .build()))
                .display(FOTItems.MEGALODON_POTTERY_SHERD,
                        Component.translatable("advancements.fot.ancient_myth.title"),
                        Component.translatable("advancements.fot.ancient_myth.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("ancient_myth"));

        Advancement.Builder.advancement().parent(tropicalIsland)
                .addCriterion("crush_pomegranate", FallingAnvilCrushItemTrigger.TriggerInstance.crushItem(ItemPredicate.Builder.item()
                        .of(FOTItems.POMEGRANATE).withCount(MinMaxBounds.Ints.atLeast(8))))
                .display(Items.RED_DYE,
                        Component.translatable("advancements.fot.crush_pomegranate.title"),
                        Component.translatable("advancements.fot.crush_pomegranate.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("crush_pomegranate"));

        Advancement.Builder.advancement().parent(advancement)
                .addCriterion("taste_the_deep", ConsumeItemTrigger.TriggerInstance.usedItem(FOTItems.GUARDIAN_FRUIT))
                .display(FOTItems.GUARDIAN_FRUIT,
                        Component.translatable("advancements.fot.taste_the_deep.title"),
                        Component.translatable("advancements.fot.taste_the_deep.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("taste_the_deep"));

        Advancement.Builder.advancement().parent(advancement)
                .addCriterion("shoal_hunter", ParticipateShoalTrigger.TriggerInstance.participateShoal())
                .display(Items.FISHING_ROD,
                        Component.translatable("advancements.fot.shoal_hunter.title"),
                        Component.translatable("advancements.fot.shoal_hunter.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("shoal_hunter"));

        Advancement.Builder.advancement().parent(battlegillAdvancement)
                .addCriterion("drunken_sailor", FollowLivingWithEffectTrigger.TriggerInstance.entityWithEffect(EntityPredicate.Builder.entity().of(FOTEntities.BATTLEGILL).subPredicate(FOTEntitySubPredicate.variant(BattlegillVariants.RUM)).build(), MobEffectsPredicate.effects().and(MobEffects.CONFUSION)))
                .display(FOTItem.create(FOTItems.BATTLEGILL, Battlegill.VARIANT_TO_INT.get("fishofthieves:rum")),
                        Component.translatable("advancements.fot.drunken_sailor.title"),
                        Component.translatable("advancements.fot.drunken_sailor.description"),
                        null, FrameType.TASK, true, true, false)
                .save(consumer, this.mod("drunken_sailor"));
    }

    private String mod(String name)
    {
        return FishOfThieves.MOD_RESOURCES + name;
    }

    private Advancement.Builder addFishBuckets(Advancement.Builder builder)
    {
        for (var item : FOTTags.FISH_BUCKETS)
        {
            builder.addCriterion(this.getItemName(item), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).build()));
        }
        return builder;
    }

    private Advancement.Builder addFishVariantsBuckets(Advancement.Builder builder, boolean trophy)
    {
        for (var item : FOTTags.FISH_BUCKETS)
        {
            for (var variant : new TreeSet<>(BUCKET_TO_VARIANTS_MAP.get(item).keySet()))
            {
                if (BUCKET_TO_VARIANTS_MAP.get(item).getOptional(variant).orElseThrow().isTreasured().isPresent())
                {
                    continue;
                }

                builder.addCriterion(variant.getPath() + "_" + this.getItemName(item), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(item).hasNbt(Util.make(new CompoundTag(), compound ->
                {
                    compound.putString(ThievesFish.VARIANT_TAG, variant.toString());

                    if (trophy)
                    {
                        compound.putBoolean(ThievesFish.TROPHY_TAG, true);
                        compound.putBoolean(ThievesFish.HAS_FED_TAG, false);
                    }
                })).build()));
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