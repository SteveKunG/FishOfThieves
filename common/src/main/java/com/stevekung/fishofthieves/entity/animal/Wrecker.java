package com.stevekung.fishofthieves.entity.animal;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.FishVariant;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTItems;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;
import com.stevekung.fishofthieves.spawn.SpawnConditionContext;
import com.stevekung.fishofthieves.spawn.SpawnSelectors;
import com.stevekung.fishofthieves.utils.PlatformConfig;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class Wrecker extends AbstractThievesFish
{
    private static final Map<FishVariant, ResourceLocation> GLOW_BY_TYPE = Collections.singletonMap(Variant.MOON, new ResourceLocation(FishOfThieves.MOD_ID, "textures/entity/wrecker/moon_glow.png"));
    private static final EntityDataAccessor<BlockPos> SHIPWRECK_POS = SynchedEntityData.defineId(Wrecker.class, EntityDataSerializers.BLOCK_POS);
    private static final Predicate<LivingEntity> SELECTORS = livingEntity -> livingEntity.getMobType() != MobType.WATER && livingEntity.attackable();

    public Wrecker(EntityType<? extends Wrecker> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0f, true));
        this.goalSelector.addGoal(5, new SwimToShipwreckGoal(this));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, Monster.class, 20, true, false, SELECTORS));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, Player.class, 50, true, false, null));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(SHIPWRECK_POS, BlockPos.ZERO);
    }

    @Override
    public ItemStack getBucketItemStack()
    {
        return new ItemStack(FOTItems.WRECKER_BUCKET);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FOTSoundEvents.WRECKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return FOTSoundEvents.WRECKER_HURT;
    }

    @Override
    protected SoundEvent getFlopSound()
    {
        return FOTSoundEvents.WRECKER_FLOP;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose) : EntityDimensions.fixed(0.275F, 0.25F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return this.isTrophy() ? 0.35F : 0.175F;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (this.isTrophy())
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5d);
        }
        return spawnData;
    }

    @Override
    public void setTrophy(boolean trophy)
    {
        if (trophy)
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5d);
        }
        super.setTrophy(trophy);
    }

    @Override
    public boolean canGlow()
    {
        return this.getVariant() == Variant.MOON;
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

    public void setShipwreckPos(BlockPos pos)
    {
        this.entityData.set(SHIPWRECK_POS, pos);
    }

    public BlockPos getShipwreckPos()
    {
        return this.entityData.get(SHIPWRECK_POS);
    }

    private boolean closeToNextPos()
    {
        var blockPos = this.getNavigation().getTargetPos();

        if (blockPos != null)
        {
            return blockPos.closerThan(this.position(), 12.0);
        }
        return false;
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random)
    {
        return TerrainUtils.isInFeature((ServerLevel) levelAccessor, blockPos, StructureFeature.SHIPWRECK) && levelAccessor.getFluidState(blockPos).is(FluidTags.WATER);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }

    static class SwimToShipwreckGoal extends Goal
    {
        private final Wrecker wrecker;
        private boolean stuck;

        SwimToShipwreckGoal(Wrecker wrecker)
        {
            this.wrecker = wrecker;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            return this.wrecker.random.nextInt(50) == 0;
        }

        @Override
        public boolean canContinueToUse()
        {
            var blockPos = new BlockPos(this.wrecker.getShipwreckPos().getX(), this.wrecker.getY(), this.wrecker.getShipwreckPos().getZ());
            return !blockPos.closerThan(this.wrecker.position(), 4.0D) && !this.stuck;
        }

        @Override
        public void start()
        {
            if (this.wrecker.level instanceof ServerLevel serverLevel)
            {
                this.stuck = false;
                this.wrecker.getNavigation().stop();
                var blockPos = this.wrecker.blockPosition();
                var blockPos2 = serverLevel.findNearestMapFeature(StructureFeature.SHIPWRECK, blockPos, 32, false);

                if (blockPos2 == null)
                {
                    var blockPos3 = serverLevel.findNearestMapFeature(StructureFeature.SHIPWRECK, blockPos, 32, false);

                    if (blockPos3 == null)
                    {
                        this.stuck = true;
                        return;
                    }
                    this.wrecker.setShipwreckPos(blockPos3);
                }
                else
                {
                    this.wrecker.setShipwreckPos(blockPos2);
                }
            }
        }

        @Override
        public void stop()
        {
            var blockPos = new BlockPos(this.wrecker.getShipwreckPos().getX(), this.wrecker.getY(), this.wrecker.getShipwreckPos().getZ());

            if (blockPos.closerThan(this.wrecker.position(), 4.0) || this.stuck)
            {
                this.wrecker.getNavigation().stop();
            }
        }

        @Override
        public void tick()
        {
            var level = this.wrecker.level;

            if (this.wrecker.closeToNextPos() || this.wrecker.getNavigation().isDone())
            {
                BlockPos blockPos;
                var vec3 = Vec3.atCenterOf(this.wrecker.getShipwreckPos());
                var vec32 = DefaultRandomPos.getPosTowards(this.wrecker, 16, 1, vec3, 0.3926991f);

                if (vec32 == null)
                {
                    vec32 = DefaultRandomPos.getPosTowards(this.wrecker, 8, 4, vec3, 1.5707963705062866);
                }
                if (!(vec32 == null || level.getFluidState(blockPos = new BlockPos(vec32)).is(FluidTags.WATER) && level.getBlockState(blockPos).isPathfindable(level, blockPos, PathComputationType.WATER)))
                {
                    vec32 = DefaultRandomPos.getPosTowards(this.wrecker, 8, 5, vec3, 1.5707963705062866);
                }
                if (vec32 == null)
                {
                    this.stuck = true;
                    return;
                }
                this.wrecker.getLookControl().setLookAt(vec32.x, vec32.y, vec32.z, this.wrecker.getMaxHeadYRot() + 20, this.wrecker.getMaxHeadXRot());
                this.wrecker.getNavigation().moveTo(vec32.x, vec32.y, vec32.z, 1.3D);
            }
        }
    }

    public enum Variant implements FishVariant
    {
        ROSE(SpawnSelectors.always()),
        SUN(SpawnSelectors.dayAndSeeSky()),
        BLACKCLOUD(SpawnSelectors.thunderingAndSeeSky()),
        SNOW(SpawnSelectors.probability(PlatformConfig.snowWreckerProbability()).and(SpawnSelectors.includeByKey(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN))),
        MOON(SpawnSelectors.nightAndSeeSky().and(context -> context.level().getMoonBrightness() > 0F));

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