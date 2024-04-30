package com.stevekung.fishofthieves.entity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.entity.ai.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.debug.SchoolingFishDebug;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTSensorTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractSchoolingThievesFish<T extends FishData> extends AbstractSchoolingFish implements ThievesFish<T>
{
    private static final EntityDataAccessor<Boolean> TROPHY = SynchedEntityData.defineId(AbstractSchoolingThievesFish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_FED = SynchedEntityData.defineId(AbstractSchoolingThievesFish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NO_FLIP = SynchedEntityData.defineId(AbstractSchoolingThievesFish.class, EntityDataSerializers.BOOLEAN);

    //@formatter:off
    protected static final ImmutableList<SensorType<? extends Sensor<? super AbstractSchoolingThievesFish<?>>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            FOTSensorTypes.NON_CREATIVE_NEAREST_PLAYERS,
            FOTSensorTypes.NEAREST_SCHOOLING_THIEVES_FISH,
            FOTSensorTypes.NEAREST_MAGMA_BLOCK,
            FOTSensorTypes.NEAREST_FLOCK_LEADER,
            SensorType.HURT_BY
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            // Common AI
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,

            // Avoid Repellent AI
            MemoryModuleType.NEAREST_REPELLENT,

            // Avoid Player AI
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.AVOID_TARGET,

            // Follow Flock AI
            FOTMemoryModuleTypes.SCHOOL_SIZE,
            FOTMemoryModuleTypes.FLOCK_LEADER,
            FOTMemoryModuleTypes.IS_FLOCK_LEADER,
            FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER,
            FOTMemoryModuleTypes.FLOCK_FOLLOWERS,
            FOTMemoryModuleTypes.MERGE_FROM_OTHER_FLOCK,
            FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH,
            FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS,
            FOTMemoryModuleTypes.NEAREST_VISIBLE_FLOCK_LEADER,

            // Tempting AI
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.IS_PANICKING
    );
    //@formatter:on

    public AbstractSchoolingThievesFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level)
    {
        super(entityType, level);
        this.refreshDimensions();
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public void tick()
    {
        super.tick();
        SchoolingFishDebug.tick(this);

        if (this.isFollower() && this.hasLeader() && !this.getLeader().isAlive())
        {
            AbstractSchoolingThievesFishAi.resetMemories(this);
        }
    }

    @Override
    protected void registerGoals() {}

    @Override
    public void remove(Entity.RemovalReason reason)
    {
        if (!this.level.isClientSide() && this.isDeadOrDying())
        {
            if (this.isFollower())
            {
                this.getLeader().removeFollower();
            }
        }
        super.remove(reason);
    }

    @Override
    public boolean isFollower()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER) && this.getBrain().getMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER).get();
    }

    @Override
    public void stopFollowing()
    {
        this.getLeader().removeFollower();
        this.getBrain().eraseMemory(FOTMemoryModuleTypes.FLOCK_LEADER);
    }

    @Override
    public boolean canBeFollowed()
    {
        return this.isLeader() && this.getSchoolSize() < this.getMaxSchoolSize();
    }

    @Override
    public boolean hasFollowers()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.FLOCK_FOLLOWERS) && !this.getFlockFollowers().isEmpty() && this.getSchoolSize() > 1;
    }

    public boolean isLeader()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.IS_FLOCK_LEADER) && this.getBrain().getMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER).get();
    }

    @SuppressWarnings("rawtypes")
    public List<AbstractSchoolingThievesFish> getFlockFollowers()
    {
        return this.getBrain().getMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS).get();
    }

    @SuppressWarnings("rawtypes")
    protected void startFollowingThievesFish(AbstractSchoolingThievesFish leader)
    {
        this.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, leader);
        this.getBrain().setMemory(FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER, true);
        leader.getBrain().setMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER, true);
        leader.addFollower();
    }

    @SuppressWarnings("rawtypes")
    public void addThievesFishFollowers(Stream<AbstractSchoolingThievesFish> followers)
    {
        var list = followers.limit(this.getMaxSchoolSize() - this.getSchoolSize()).filter(fish -> fish != this).collect(Collectors.toList());
        var hasFlockFollowerMem = this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.FLOCK_FOLLOWERS);

        list.forEach(fish ->
        {
            fish.startFollowingThievesFish(this);

            if (hasFlockFollowerMem)
            {
                this.getFlockFollowers().add(fish);
            }
        });

        if (!list.isEmpty() && !hasFlockFollowerMem)
        {
            this.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_FOLLOWERS, list);
        }
    }

    public boolean hasFollowCooldown()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS);
    }

    @SuppressWarnings("rawtypes")
    public boolean isSameType(AbstractSchoolingThievesFish other)
    {
        return this.getType() == other.getType();
    }

    public boolean hasLeader()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.FLOCK_LEADER);
    }

    @SuppressWarnings("rawtypes")
    public AbstractSchoolingThievesFish getLeader()
    {
        return this.getBrain().getMemory(FOTMemoryModuleTypes.FLOCK_LEADER).get();
    }

    public void addFollower()
    {
        this.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, this.getSchoolSize() + 1);
    }

    public void removeFollower()
    {
        this.removeFollower(true);
    }

    public void removeFollower(boolean eraseIsLeader)
    {
        this.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, this.getSchoolSize() - 1);

        if (eraseIsLeader && this.getSchoolSize() == 1)
        {
            this.getBrain().eraseMemory(FOTMemoryModuleTypes.IS_FLOCK_LEADER);
        }
    }

    public int getSchoolSize()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.SCHOOL_SIZE) ? this.getBrain().getMemory(FOTMemoryModuleTypes.SCHOOL_SIZE).get() : 1;
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(TROPHY, false);
        this.entityData.define(HAS_FED, false);
        this.entityData.define(NO_FLIP, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putString(VARIANT_TAG, this.getRegistry().getKey(this.getVariant()).toString());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
        compound.putBoolean(HAS_FED_TAG, this.hasFed());
        compound.putBoolean(NO_FLIP_TAG, this.isNoFlip());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        ThievesFish.fixData(compound, this.getDataFix());

        var variant = this.getRegistry().get(ResourceLocation.tryParse(compound.getString(VARIANT_TAG)));

        if (variant != null)
        {
            this.setVariant(variant);
        }

        this.setTrophy(compound.getBoolean(TROPHY_TAG));
        this.setHasFed(compound.getBoolean(HAS_FED_TAG));
        this.setNoFlip(compound.getBoolean(NO_FLIP_TAG));

        AbstractSchoolingThievesFishAi.resetMemories(this);
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack)
    {
        super.saveToBucketTag(itemStack);
        this.saveToBucket(itemStack.getOrCreateTag());
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound)
    {
        super.loadFromBucketTag(compound);
        this.loadFromBucket(compound);
    }

    @Override
    public float getWalkTargetValue(BlockPos blockPos, LevelReader level)
    {
        if (AbstractThievesFishAi.isPosNearNearestRepellent(this, blockPos))
        {
            return -1.0F;
        }
        else
        {
            return super.getWalkTargetValue(blockPos, level);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        var itemStack = player.getItemInHand(hand);

        if (this.isFood(itemStack) && !this.isTrophy() && !this.hasFed())
        {
            if (!this.level.isClientSide())
            {
                this.growUp(player, itemStack);
            }
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
            return InteractionResult.sidedSuccess(this.level.isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        var hurt = super.hurt(source, amount);

        if (this.level.isClientSide())
        {
            return false;
        }
        else
        {
            if (hurt && source.getEntity() instanceof LivingEntity)
            {
                AbstractSchoolingThievesFishAi.wasHurtBy(this);
            }
            return hurt;
        }
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        AbstractSchoolingThievesFishAi.initMemories(this);
        return this.defaultFinalizeSpawn(this, reason, spawnData, dataTag);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key)
    {
        if (TROPHY.equals(key))
        {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public boolean isTrophy()
    {
        return this.entityData.get(TROPHY);
    }

    @Override
    public void setTrophy(boolean trophy)
    {
        if (trophy)
        {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
        this.entityData.set(TROPHY, trophy);
    }

    @Override
    public boolean hasFed()
    {
        return this.entityData.get(HAS_FED);
    }

    @Override
    public void setHasFed(boolean hasFed)
    {
        this.entityData.set(HAS_FED, hasFed);
    }

    @Override
    public void setNoFlip(boolean noFlip)
    {
        this.entityData.set(NO_FLIP, noFlip);
    }

    @Override
    public boolean isNoFlip()
    {
        return this.entityData.get(NO_FLIP);
    }

    private void growUp(Player player, ItemStack itemStack)
    {
        if (!player.getAbilities().instabuild)
        {
            itemStack.shrink(1);
        }
        if (this.random.nextInt(10) == 0)
        {
            this.setTrophy(true);
            this.setHasFed(true);
            this.setHealth(FishOfThieves.CONFIG.general.trophyMaxHealth);
        }
    }
}