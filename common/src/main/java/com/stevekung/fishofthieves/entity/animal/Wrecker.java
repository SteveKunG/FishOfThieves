package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.WreckerVariants;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class Wrecker extends AbstractThievesFish<WreckerVariant>
{
    private static final Predicate<LivingEntity> SELECTORS = livingEntity -> livingEntity.getMobType() != MobType.WATER && livingEntity.isInWater() && livingEntity.attackable();
    private static final EntityDataAccessor<WreckerVariant> VARIANT = SynchedEntityData.defineId(Wrecker.class, FOTDataSerializers.WRECKER_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:rose");
        map.put(0, "fishofthieves:rose");
        map.put(1, "fishofthieves:sun");
        map.put(2, "fishofthieves:blackcloud");
        map.put(3, "fishofthieves:snow");
        map.put(4, "fishofthieves:moon");
    };

    public Wrecker(EntityType<? extends Wrecker> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SwimToNearbyWreckageGoal(this, 1.25d));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25d, EARTHWORMS_FOOD, false));

        if (FishOfThieves.CONFIG.general.neutralFishBehavior)
        {
            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0d, true));
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, 20, true, false, SELECTORS));
            this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 50, true, false, SELECTORS));
        }
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(VARIANT, WreckerVariants.ROSE);
    }

    @Override
    public Registry<WreckerVariant> getRegistry()
    {
        return FOTRegistry.WRECKER_VARIANT;
    }

    @Override
    public void setVariant(WreckerVariant variant)
    {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public WreckerVariant getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public Holder<WreckerVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_WRECKER_SPAWNS, WreckerVariants.ROSE, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
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
        return this.isTrophy() ? 0.34F : 0.175F;
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
    public boolean isFood(ItemStack itemStack)
    {
        return EARTHWORMS_FOOD.test(itemStack);
    }

    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isWater = level.getFluidState(blockPos).is(FluidTags.WATER) && level.getBlockState(blockPos).is(Blocks.WATER);
        return isWater && (TerrainUtils.isInFeature(level.getLevel(), blockPos, FOTTags.Structures.WRECKERS_SPAWN_IN));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }

    static class SwimToNearbyWreckageGoal extends RandomStrollGoal
    {
        SwimToNearbyWreckageGoal(Wrecker wrecker, double speedModifier)
        {
            super(wrecker, speedModifier);
        }

        @Override
        @Nullable
        protected Vec3 getPosition()
        {
            var wreckagePos = ((ServerLevel) this.mob.level).findNearestMapStructure(FOTTags.Structures.WRECKERS_LOCATED, this.mob.blockPosition(), 32, false);

            if (this.mob.level.random.nextFloat() < 0.3f)
            {
                return LandRandomPos.getPos(this.mob, 10, 7);
            }
            if (wreckagePos != null)
            {
                return Vec3.atCenterOf(wreckagePos);
            }
            return super.getPosition();
        }
    }
}