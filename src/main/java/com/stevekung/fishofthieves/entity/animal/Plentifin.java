package com.stevekung.fishofthieves.entity.animal;

import java.util.*;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class Plentifin extends AbstractSchoolingThievesFish
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.WATERY, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/plentifin/watery_glow.png"));

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
        return Variant.byId(this.entityData.get(TYPE));
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
        var category = levelAccessor.getBiome(blockPos).getBiomeCategory();

        if (category == Biome.BiomeCategory.UNDERGROUND)
        {
            return TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureFeature.MINESHAFT) || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureFeature.STRONGHOLD);
        }
        return waterRules;
    }

    public enum Variant implements ThievesFish.FishVariant
    {
        OLIVE,
        AMBER(context ->
        {
            var time = context.level().getTimeOfDay(1.0F);
            return time >= 0.75F && time <= 0.9F && !context.isRaining();
        }),
        CLOUDY(context -> context.isRaining() && context.seeSkyInWater()),
        BONEDUST(context ->
        {
            var level = context.level();
            var blockPos = context.blockPos();
            return context.random().nextFloat() < FishOfThieves.CONFIG.spawnRate.bonedustPlentifinProbability || context.random().nextInt(10) == 0 && (TerrainUtils.isInFeature(level, blockPos, StructureFeature.MINESHAFT) || TerrainUtils.isInFeature(level, blockPos, StructureFeature.STRONGHOLD));
        }),
        WATERY(context -> context.isNight() && context.seeSkyInWater());

        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final ThievesFish.Condition condition;

        Variant(ThievesFish.Condition condition)
        {
            this.condition = condition;
        }

        Variant()
        {
            this(ThievesFish.Condition.always());
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
        public ThievesFish.Condition getCondition()
        {
            return this.condition;
        }

        public static Variant byId(int id)
        {
            var types = BY_ID;

            if (id < 0 || id >= types.length)
            {
                id = 0;
            }
            return types[id];
        }
    }
}