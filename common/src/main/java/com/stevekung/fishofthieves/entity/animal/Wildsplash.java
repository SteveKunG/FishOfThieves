package com.stevekung.fishofthieves.entity.animal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.Continentalness;
import com.stevekung.fishofthieves.utils.PlatformConfig;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class Wildsplash extends AbstractSchoolingThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.CORAL, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wildsplash/coral_glow.png"));

    public Wildsplash(EntityType<? extends Wildsplash> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.WILDSPLASH_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.WILDSPLASH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.WILDSPLASH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.WILDSPLASH_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 4;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.3F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.38F : 0.2F;
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.CORAL;
    }

    @Override
    public Variant getVariant()
    {
        return Variant.BY_ID[Mth.positiveModulo(this.entityData.get(TYPE), Variant.BY_ID.length)];
    }

    @Override
    public int getSpawnVariantId(boolean bucket)
    {
        return ThievesFish.getSpawnVariant(this, Variant.BY_ID, Variant[]::new, bucket);
    }

    @Override
    public Map<FishVariant, ResourceLocation> getGlowTextureByType()
    {
        return GLOW_BY_TYPE;
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random)
    {
        var biome = levelAccessor.getBiome(blockPos);
        return biome.is(Biomes.LUSH_CAVES) || biome.is(Biomes.WARM_OCEAN) || WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, random);
    }

    public enum Variant implements FishVariant
    {
        RUSSET(SpawnSelectors.always()),
        SANDY(SpawnSelectors.categories(Biome.BiomeCategory.BEACH).and(SpawnSelectors.continentalness(Continentalness.COAST))),
        OCEAN(SpawnSelectors.categories(Biome.BiomeCategory.OCEAN)),
        MUDDY(SpawnSelectors.probability(PlatformConfig.muddyWildsplashProbability()).and(SpawnSelectors.categories(Biome.BiomeCategory.SWAMP))),
        CORAL(SpawnSelectors.nightAndSeeSky().and(SpawnSelectors.includeByKey(Biomes.WARM_OCEAN)).and(context -> TerrainUtils.lookForBlocksWithSize(context.blockPos(), 3, 24, blockPos2 ->
        {
            var blockState = context.level().getBlockState(blockPos2);
            return blockState.is(BlockTags.CORALS) || blockState.is(BlockTags.CORAL_BLOCKS) || blockState.is(BlockTags.WALL_CORALS);
        })));

        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final Predicate<SpawnConditionContext> condition;

        Variant(Predicate<SpawnConditionContext> condition)
        {
            this.condition = condition;
        }

        @Override
        public String getName()
        {
            return this.name().toLowerCase(Locale.ROOT);
        }

        @Override
        public int getId()
        {
            return this.ordinal();
        }

        @Override
        public Predicate<SpawnConditionContext> getCondition()
        {
            return this.condition;
        }
    }
}