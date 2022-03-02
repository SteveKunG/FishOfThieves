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
import com.stevekung.fishofthieves.utils.PlatformConfig;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

public class Plentifin extends AbstractSchoolingThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.WATERY, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/plentifin/watery_glow.png"));

    public Plentifin(EntityType<? extends Plentifin> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.PLENTIFIN_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.PLENTIFIN_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.PLENTIFIN_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.PLENTIFIN_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.275F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.17F : 0.09F;
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.WATERY;
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
        var waterRules = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, random);
        var category = levelAccessor.getBiome(blockPos).value().getBiomeCategory();

        if (category == Biome.BiomeCategory.UNDERGROUND)
        {
            return TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, BuiltinStructures.MINESHAFT) || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, BuiltinStructures.STRONGHOLD);
        }
        return waterRules;
    }

    public enum Variant implements FishVariant
    {
        OLIVE(SpawnSelectors.always()),
        AMBER(Predicate.not(SpawnSelectors.rainingAndSeeSky()).and(context ->
        {
            var time = context.level().getTimeOfDay(1.0F);
            return time >= 0.75F && time <= 0.9F;
        })),
        CLOUDY(SpawnSelectors.rainingAndSeeSky()),
        BONEDUST(SpawnSelectors.probability(PlatformConfig.bonedustPlentifinProbability()).or(SpawnSelectors.features(BuiltinStructures.MINESHAFT, BuiltinStructures.STRONGHOLD).and(context -> context.random().nextInt(10) == 0))),
        WATERY(SpawnSelectors.nightAndSeeSky());

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