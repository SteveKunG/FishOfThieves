package com.stevekung.fishofthieves.fabric.gametest;

import com.stevekung.fishofthieves.blockentity.FishPlaqueBlockEntity;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.fabric.gametest.core.FOTGameTest;
import com.stevekung.fishofthieves.registry.FOTDataComponentTypes;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTRegistries;
import com.stevekung.fishofthieves.registry.variant.SplashtailVariants;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestAssertPosException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.fish.Salmon;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;

public class FishPlaqueTestSuite implements FOTGameTest
{
    @GameTest(structure = FISH_PLAQUE)
    public void putSplashtailInFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var level = helper.getLevel();
        var itemStack = new ItemStack(FOTItems.SPLASHTAIL_BUCKET);

        var entityTypeRegistry = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);
        var ruby = SplashtailVariants.RUBY.identifier().toString();

        itemStack.set(FOTDataComponentTypes.SPLASHTAIL_VARIANT, level.registryAccess().lookupOrThrow(FOTRegistries.SPLASHTAIL_VARIANT).getOrThrow(SplashtailVariants.RUBY));
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true));

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        var fishPlaque = helper.getBlockEntity(blockPos, FishPlaqueBlockEntity.class);

        if (fishPlaque.hasPlaqueData())
        {
            var plaqueData = fishPlaque.getPlaqueData();
            var matchId = plaqueData.getString("id").orElseThrow().equals(entityTypeRegistry.getKey(FOTEntities.SPLASHTAIL).toString());
            var matchVariant = plaqueData.getString(ThievesFish.VARIANT_TAG).orElseThrow().equals(ruby);

            if (matchId && matchVariant)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("EntityType id or variant tag is not matched!"));
            }
        }
    }

    @GameTest(structure = FISH_PLAQUE)
    public void splashtailMustSpawnWhenDestroyFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(FOTItems.SPLASHTAIL_BUCKET);

        var ruby = SplashtailVariants.RUBY.identifier().toString();

        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag ->
        {
            compoundTag.putString(FOTRegistries.SPLASHTAIL_VARIANT.identifier().getPath(), ruby);
            compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
        });

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.destroyBlock(blockPos.south(1));

        helper.succeedWhenEntityPresent(FOTEntities.SPLASHTAIL, blockPos);
    }

    @GameTest(structure = FISH_PLAQUE)
    public void putSalmonInFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var level = helper.getLevel();
        var itemStack = new ItemStack(Items.SALMON_BUCKET);

        itemStack.set(DataComponents.SALMON_SIZE, Salmon.Variant.LARGE);

        var entityTypeRegistry = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        var fishPlaque = helper.getBlockEntity(blockPos, FishPlaqueBlockEntity.class);

        if (fishPlaque.hasPlaqueData())
        {
            var plaqueData = fishPlaque.getPlaqueData();
            var matchId = plaqueData.getString("id").orElseThrow().equals(entityTypeRegistry.getKey(EntityTypes.SALMON).toString());
            var matchVariant = plaqueData.getString("type").orElseThrow().equals(Salmon.Variant.LARGE.getSerializedName());

            if (matchId && matchVariant)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("EntityType id is not a salmon!"));
            }
        }
    }

    @GameTest(structure = FISH_PLAQUE)
    public void salmonMustSpawnWhenDestroyFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.SALMON_BUCKET);

        itemStack.set(DataComponents.SALMON_SIZE, Salmon.Variant.LARGE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.destroyBlock(blockPos.south(1));

        helper.succeedWhenEntityPresent(EntityTypes.SALMON, blockPos);
    }

    @GameTest(structure = FISH_PLAQUE)
    public void putTropicalFishInFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var level = helper.getLevel();
        var itemStack = new ItemStack(Items.TROPICAL_FISH_BUCKET);

        itemStack.set(DataComponents.TROPICAL_FISH_PATTERN, TropicalFish.Pattern.KOB);
        itemStack.set(DataComponents.TROPICAL_FISH_BASE_COLOR, DyeColor.ORANGE);
        itemStack.set(DataComponents.TROPICAL_FISH_PATTERN_COLOR, DyeColor.WHITE);

        var entityTypeRegistry = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        var fishPlaque = helper.getBlockEntity(blockPos, FishPlaqueBlockEntity.class);

        if (fishPlaque.hasPlaqueData())
        {
            var plaqueData = fishPlaque.getPlaqueData();
            var matchId = plaqueData.getString("id").orElseThrow().equals(entityTypeRegistry.getKey(EntityTypes.TROPICAL_FISH).toString());
            var matchVariant = plaqueData.getIntOr("Variant", 0) == 65536;

            if (matchId && matchVariant)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("EntityType id is not a tropical fish!"));
            }
        }
    }

    @GameTest(structure = FISH_PLAQUE)
    public void tropicalFishMustSpawnWhenDestroyFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.TROPICAL_FISH_BUCKET);

        itemStack.set(DataComponents.TROPICAL_FISH_PATTERN, TropicalFish.Pattern.KOB);
        itemStack.set(DataComponents.TROPICAL_FISH_BASE_COLOR, DyeColor.ORANGE);
        itemStack.set(DataComponents.TROPICAL_FISH_PATTERN_COLOR, DyeColor.WHITE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.destroyBlock(blockPos.south(1));

        helper.succeedWhen(() ->
        {
            var pos = helper.absolutePos(blockPos);
            var list = helper.getLevel().getEntities(EntityTypes.TROPICAL_FISH, new AABB(pos), entity -> entity.isAlive() && entity.getPackedVariant() == 65536);

            if (list.isEmpty())
            {
                throw new GameTestAssertPosException(Component.literal("Expected " + EntityTypes.TROPICAL_FISH.toShortString()), pos, blockPos, (int) helper.getTick());
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void putAxolotlInFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var level = helper.getLevel();
        var itemStack = new ItemStack(Items.AXOLOTL_BUCKET);

        itemStack.set(DataComponents.AXOLOTL_VARIANT, Axolotl.Variant.CYAN);

        var entityTypeRegistry = level.registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        var fishPlaque = helper.getBlockEntity(blockPos, FishPlaqueBlockEntity.class);

        if (fishPlaque.hasPlaqueData())
        {
            var plaqueData = fishPlaque.getPlaqueData();
            var matchId = plaqueData.getString("id").orElseThrow().equals(entityTypeRegistry.getKey(EntityTypes.AXOLOTL).toString());
            var matchVariant = plaqueData.getIntOr("Variant", 0) == Axolotl.Variant.CYAN.getId();

            if (matchId && matchVariant)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("EntityType id is not an axolotl!"));
            }
        }
    }

    @GameTest(structure = FISH_PLAQUE)
    public void axolotlMustSpawnWhenDestroyFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.AXOLOTL_BUCKET);

        itemStack.set(DataComponents.AXOLOTL_VARIANT, Axolotl.Variant.CYAN);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.destroyBlock(blockPos.south(1));

        helper.succeedWhen(() ->
        {
            var pos = helper.absolutePos(blockPos);
            var list = helper.getLevel().getEntities(EntityTypes.AXOLOTL, new AABB(pos), entity -> entity.isAlive() && entity.getVariant() == Axolotl.Variant.CYAN);

            if (list.isEmpty())
            {
                throw new GameTestAssertPosException(Component.literal("Expected " + EntityTypes.AXOLOTL.toShortString()), pos, blockPos, (int) helper.getTick());
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void checkSplashtailBucketFromFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(FOTItems.SPLASHTAIL_BUCKET);

        itemStack.set(FOTDataComponentTypes.SPLASHTAIL_VARIANT, helper.getLevel().registryAccess().lookupOrThrow(FOTRegistries.SPLASHTAIL_VARIANT).getOrThrow(SplashtailVariants.RUBY));
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemStack, compoundTag -> compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true));

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.runAtTickTime(20, () ->
        {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
            helper.useBlock(blockPos, player);

            var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (mainHandItem.is(FOTItems.SPLASHTAIL_BUCKET) && mainHandItem.has(FOTDataComponentTypes.SPLASHTAIL_VARIANT) && mainHandItem.get(FOTDataComponentTypes.SPLASHTAIL_VARIANT).is(SplashtailVariants.RUBY))
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("Item is not a splashtail bucket with their variant"));
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void checkSalmonBucketFromFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.SALMON_BUCKET);

        itemStack.set(DataComponents.SALMON_SIZE, Salmon.Variant.LARGE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.runAtTickTime(20, () ->
        {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
            helper.useBlock(blockPos, player);

            var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (mainHandItem.is(Items.SALMON_BUCKET) && mainHandItem.get(DataComponents.SALMON_SIZE) == Salmon.Variant.LARGE)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("Item is not a salmon bucket"));
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void checkTropicalFishBucketFromFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.TROPICAL_FISH_BUCKET);

        itemStack.set(DataComponents.TROPICAL_FISH_PATTERN, TropicalFish.Pattern.KOB);
        itemStack.set(DataComponents.TROPICAL_FISH_BASE_COLOR, DyeColor.ORANGE);
        itemStack.set(DataComponents.TROPICAL_FISH_PATTERN_COLOR, DyeColor.WHITE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.runAtTickTime(20, () ->
        {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
            helper.useBlock(blockPos, player);

            var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            var tropicalFishPattern = mainHandItem.get(DataComponents.TROPICAL_FISH_PATTERN);
            var tropicalFishBaseColor = mainHandItem.get(DataComponents.TROPICAL_FISH_BASE_COLOR);
            var tropicalFishPatternColor = mainHandItem.get(DataComponents.TROPICAL_FISH_PATTERN_COLOR);

            if (mainHandItem.is(Items.TROPICAL_FISH_BUCKET) && tropicalFishPattern == TropicalFish.Pattern.KOB &&
                    tropicalFishBaseColor == DyeColor.ORANGE && tropicalFishPatternColor == DyeColor.WHITE)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("Item is not a tropical fish bucket with their variant"));
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void checkAxolotlBucketFromFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.AXOLOTL_BUCKET);

        itemStack.set(DataComponents.AXOLOTL_VARIANT, Axolotl.Variant.CYAN);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        helper.runAtTickTime(20, () ->
        {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.WATER_BUCKET));
            helper.useBlock(blockPos, player);

            var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            var axolotlVariant = mainHandItem.get(DataComponents.AXOLOTL_VARIANT);

            if (mainHandItem.is(Items.AXOLOTL_BUCKET) && axolotlVariant == Axolotl.Variant.CYAN)
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("Item is not an axolotl bucket with their variant"));
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void waxFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.SALMON_BUCKET);

        itemStack.set(DataComponents.SALMON_SIZE, Salmon.Variant.LARGE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        var fishPlaque = helper.getBlockEntity(blockPos, FishPlaqueBlockEntity.class);

        helper.runAtTickTime(20, () ->
        {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.HONEYCOMB));
            helper.useBlock(blockPos, player);

            if (fishPlaque.isWaxed())
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("Fish Plaque is not waxed!"));
            }
        });
    }

    @GameTest(structure = FISH_PLAQUE)
    public void unwaxFishPlaqueTest(GameTestHelper helper)
    {
        var blockPos = new BlockPos(1, 1, 1);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var itemStack = new ItemStack(Items.SALMON_BUCKET);

        itemStack.set(DataComponents.SALMON_SIZE, Salmon.Variant.LARGE);

        player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
        helper.useBlock(blockPos, player);

        var fishPlaque = helper.getBlockEntity(blockPos, FishPlaqueBlockEntity.class);

        helper.runAtTickTime(20, () ->
        {
            fishPlaque.setWaxed(true);

            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_AXE));
            helper.useBlock(blockPos, player);

            if (!fishPlaque.isWaxed())
            {
                helper.succeed();
            }
            else
            {
                helper.fail(Component.literal("Fish Plaque is still waxed!"));
            }
        });
    }
}