package com.stevekung.fishofthieves.entity.animal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public class Battlegill extends AbstractSchoolingThievesFish
{
    private static final Map<ThievesFish.FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.BITTERSWEET, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/battlegill/bittersweet_glow.png"));
    private static final Predicate<LivingEntity> SELECTORS = livingEntity -> livingEntity instanceof Enemy && (livingEntity.getMobType() == MobType.UNDEAD || livingEntity.getMobType() == MobType.WATER) && livingEntity.attackable();

    public Battlegill(EntityType<? extends Battlegill> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0f, true));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, Monster.class, 20, true, false, SELECTORS));
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.BATTLEGILL_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.BATTLEGILL_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.BATTLEGILL_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.BATTLEGILL_FLOP;
    }

    @Override
    public int getMaxSchoolSize()
    {
        return 4;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.275F, 0.275F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.25F : 0.125F;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (this.isTrophy())
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0d);
        }
        return spawnData;
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.BITTERSWEET;
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
        var waterRules = (TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureFeature.OCEAN_MONUMENT) || TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureFeature.PILLAGER_OUTPOST)) && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER);
        var inFeatures = levelAccessor.canSeeSkyFromBelowWater(blockPos) && ((ServerLevel)levelAccessor).isRaided(blockPos);
        return waterRules || inFeatures;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }

    public enum Variant implements ThievesFish.FishVariant
    {
        JADE,
        SKY(SpawnConditionContext::seeSkyInWater),
        RUM,
        SAND(context ->
        {
            var inBiomes = TerrainUtils.isInBiome(context.level(), context.blockPos(), Biomes.WARM_OCEAN) || TerrainUtils.isInBiome(context.level(), context.blockPos(), Biomes.LUKEWARM_OCEAN) || TerrainUtils.isInBiome(context.level(), context.blockPos(), Biomes.DEEP_LUKEWARM_OCEAN);
            return inBiomes && context.random().nextFloat() < FishOfThieves.CONFIG.spawnRate.sandBattlegillProbability;
        }),
        BITTERSWEET(context -> context.isNight() && context.seeSkyInWater());

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