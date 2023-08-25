package com.stevekung.fishofthieves.entity;

import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTSensorTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

            // Avoid Player AI
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.AVOID_TARGET,

            FOTMemoryModuleTypes.school_size,
            FOTMemoryModuleTypes.leader,
            FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH,

            // Follow Flock AI
//            FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS,
//            FOTMemoryModuleTypes.IS_FLOCK_FOLLOWER,
//            FOTMemoryModuleTypes.IS_FLOCK_LEADER,
//            FOTMemoryModuleTypes.NEAREST_FLOCK_LEADER,

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
    protected void registerGoals() {
    }

    @Override
    public boolean isFollower() {
        var leader = this.getBrain().getMemory(FOTMemoryModuleTypes.leader);
        return leader.isPresent() && leader.get().isAlive();
    }

    @SuppressWarnings("rawtypes")
    public AbstractSchoolingThievesFish startFollowingThievesFish(AbstractSchoolingThievesFish leader) {
        this.getBrain().setMemory(FOTMemoryModuleTypes.leader, leader);
        leader.addFollower();
        return leader;
    }

    @Override
    public void stopFollowing() {
        var leader = this.getBrain().getMemory(FOTMemoryModuleTypes.leader).get();
        leader.removeFollower();
        this.getBrain().eraseMemory(FOTMemoryModuleTypes.leader);
    }

    private void addFollower() {
        this.getBrain().setMemory(FOTMemoryModuleTypes.school_size, this.getBrain().getMemory(FOTMemoryModuleTypes.school_size).get() + 1);
    }

    private void removeFollower() {
        this.getBrain().setMemory(FOTMemoryModuleTypes.school_size, this.getBrain().getMemory(FOTMemoryModuleTypes.school_size).get() - 1);
    }

    @Override
    public boolean canBeFollowed() {
        return this.hasFollowers() && this.getBrain().getMemory(FOTMemoryModuleTypes.school_size).get() < this.getMaxSchoolSize();
    }

    @Override
    public boolean hasFollowers() {
        var schoolSize = this.getBrain().getMemory(FOTMemoryModuleTypes.school_size);
        return schoolSize.isPresent() && schoolSize.get() > 1;
    }

    @SuppressWarnings("rawtypes")
    public void addThievesFishFollowers(Stream<? extends AbstractSchoolingThievesFish> followers) {
        followers.limit(this.getMaxSchoolSize() - this.getBrain().getMemory(FOTMemoryModuleTypes.school_size).get()).filter(abstractSchoolingFish -> abstractSchoolingFish != this).forEach(abstractSchoolingFish -> abstractSchoolingFish.startFollowingThievesFish(this));
    }

    @Override
    @Deprecated//TODO DEBUG
    public boolean canBeLeashed(Player player)
    {
        return true;
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
    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        var itemStack = player.getItemInHand(hand);

        if (this.isFood(itemStack) && !this.isTrophy() && !this.hasFed())
        {
            if (!this.level.isClientSide)
            {
                this.growUp(player, itemStack);
            }
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        if (spawnData == null) {
            spawnData = new ThievesFishSchoolSpawnGroupData<>(this);
        } else {
            //noinspection rawtypes
            this.startFollowingThievesFish(((ThievesFishSchoolSpawnGroupData)spawnData).leader);
        }
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

    public static class ThievesFishSchoolSpawnGroupData<T extends FishData> implements SpawnGroupData {
        public final AbstractSchoolingThievesFish<T> leader;

        public ThievesFishSchoolSpawnGroupData(AbstractSchoolingThievesFish<T> abstractSchoolingFish) {
            this.leader = abstractSchoolingFish;
        }
    }
}