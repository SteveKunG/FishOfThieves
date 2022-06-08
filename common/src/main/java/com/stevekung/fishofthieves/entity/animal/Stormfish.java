package com.stevekung.fishofthieves.entity.animal;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.Continentalness;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;

public class Stormfish extends AbstractThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.TWILIGHT, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/stormfish/twilight_glow.png"));

    public Stormfish(EntityType<? extends Stormfish> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.STORMFISH_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.STORMFISH_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.STORMFISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.STORMFISH_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.4F, 0.3F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.2F : 0.11F;
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightning) {}

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.TWILIGHT;
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

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        return levelAccessor.getLevelData().isThundering() && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER) && levelAccessor.canSeeSkyFromBelowWater(blockPos);
    }

    public enum Variant implements FishVariant
    {
        ANCIENT(SpawnSelectors.always()),
        SHORES(context -> context.continentalness() == Continentalness.COAST),
        WILD(SpawnSelectors.includeByKey(Biomes.SPARSE_JUNGLE)),
        SHADOW(SpawnSelectors.probability(FishOfThieves.CONFIG.spawnRate.shadowStormfishProbability).and(context -> context.level().getBrightness(LightLayer.SKY, context.blockPos()) <= 4)),
        TWILIGHT(context -> context.level().getSkyDarken() >= 9);

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