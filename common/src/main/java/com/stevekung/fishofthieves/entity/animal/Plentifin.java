package com.stevekung.fishofthieves.entity.animal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.FishData;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.registry.FOTTags;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Plentifin extends AbstractSchoolingThievesFish<FishData>
{
//    private static final Map<FishData, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.WATERY, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/plentifin/watery_glow.png"));

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

//    @Override
//    public boolean canGlow()
//    {
//        return this.getVariant() == Variant.WATERY;
//    }
//
//    @Override
//    public Variant getVariant()
//    {
//        return Variant.BY_ID[Mth.positiveModulo(this.entityData.get(TYPE), Variant.BY_ID.length)];
//    }
//
//    @Override
//    public int getSpawnVariantId(boolean bucket)
//    {
//        return ThievesFish.getSpawnVariant(this, Variant.BY_ID, Variant[]::new, bucket);
//    }
//
//    @Override
//    public Map<FishData, ResourceLocation> getGlowTextureByType()
//    {
//        return GLOW_BY_TYPE;
//    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var waterRules = WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, random);

        if (levelAccessor.getBiome(blockPos).is(FOTTags.IS_CAVES))
        {
            return TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureTags.MINESHAFT) || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, FOTTags.STRONGHOLDS);
        }
        return waterRules;
    }

    @Override
    public FishData getVariant()
    {
        return null;
    }

    @Override
    public void setVariant(FishData variant)
    {

    }

    @Override
    public Holder<FishData> getSpawnVariant(boolean bucket)
    {
        return null;
    }

    @Override
    public Registry<FishData> getRegistry()
    {
        return null;
    }

    //    public enum Variant implements FishData
//    {
//        OLIVE(SpawnSelectors.always()),
//        AMBER(SpawnSelectors.simpleSpawn(Predicate.not(SpawnSelectors.rainingAndSeeSky()).and(context ->
//        {
//            var time = context.level().getTimeOfDay(1.0F);
//            return time >= 0.75F && time <= 0.9F;
//        }))),
//        CLOUDY(SpawnSelectors.simpleSpawn(SpawnSelectors.rainingAndSeeSky())),
//        BONEDUST(SpawnSelectors.simpleSpawn(FishOfThieves.CONFIG.spawnRate.bonedustPlentifinProbability, SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.bonedustPlentifinProbability).or(SpawnSelectors.features(StructureTags.MINESHAFT, FOTTags.STRONGHOLDS).and(context -> context.random().nextInt(10) == 0)))),
//        WATERY(SpawnSelectors.nightAndSeeSky());
//
//        public static final Variant[] BY_ID = Stream.of(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
//        private final Predicate<SpawnConditionContext> condition;
//
//        Variant(Predicate<SpawnConditionContext> condition)
//        {
//            this.condition = condition;
//        }
//
//        @Override
//        public String getName()
//        {
//            return this.name().toLowerCase(Locale.ROOT);
//        }
//
//        @Override
//        public int getId()
//        {
//            return this.ordinal();
//        }
//
//        @Override
//        public Predicate<SpawnConditionContext> getCondition()
//        {
//            return this.condition;
//        }
//    }
}