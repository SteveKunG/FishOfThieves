package com.stevekung.fishofthieves.entity;

import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractSchoolingThievesFishAi;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTMemoryModuleTypes;
import com.stevekung.fishofthieves.registry.FOTSensorTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

            // Follow Flock AI
            FOTMemoryModuleTypes.SCHOOL_SIZE,
            FOTMemoryModuleTypes.FLOCK_LEADER,
            FOTMemoryModuleTypes.NEAREST_VISIBLE_SCHOOLING_THIEVES_FISH,
            FOTMemoryModuleTypes.FOLLOW_FLOCK_COOLDOWN_TICKS,

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

    @Deprecated //TODO Remove debug
    private static String color(boolean cond)
    {
        var color = cond ? ChatFormatting.GREEN : ChatFormatting.RED;
        return color.toString() + cond;
    }

    @Override
    public void tick()
    {
        super.tick();

        //TODO Remove debug
        if (!this.level.isClientSide() && this.getType() == FOTEntities.SPLASHTAIL)
        {
            var compo = Component.empty();
            var text = "";

            if (this.hasFollowers())
            {
                text += " hasFollowers: " + color(this.hasFollowers());
                text += ChatFormatting.RESET;
            }
            if (this.isFollower())
            {
                text += " isFollower: " + color(this.isFollower());
                text += ChatFormatting.RESET;
            }

            if (this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.SCHOOL_SIZE))
            {
                text += " schoolSize: " + ChatFormatting.GOLD + this.getBrain().getMemory(FOTMemoryModuleTypes.SCHOOL_SIZE).get();
                text += ChatFormatting.RESET;
            }

            text += " uuid: " + this.getUUID().toString().split("-")[0];

            if (this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.FLOCK_LEADER))
            {
                text += " leader: " + ChatFormatting.GOLD + this.getBrain().getMemory(FOTMemoryModuleTypes.FLOCK_LEADER).get().getUUID().toString().split("-")[0];
                text += ChatFormatting.RESET;
            }

            compo = Component.literal(text);
            this.setCustomNameVisible(true);
            this.setCustomName(compo);
        }
        //TODO Remove debug

        if (this.hasFollowers() && this.level.random.nextInt(200) == 1)
        {
            var list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(16));

            if (list.size() <= 1)
            {
                AbstractSchoolingThievesFishAi.resetMemories(this);
            }
        }
        if (this.hasLeader() && !this.getLeader().isAlive())
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
        return this.hasLeader() && this.getLeader().isAlive();
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
        return this.hasFollowers() && this.getSchoolSize() < this.getMaxSchoolSize();
    }

    @Override
    public boolean hasFollowers()
    {
        return this.getSchoolSize() > 1;
    }

    @SuppressWarnings("rawtypes")
    protected void startFollowingThievesFish(AbstractSchoolingThievesFish leader)
    {
        this.getBrain().setMemory(FOTMemoryModuleTypes.FLOCK_LEADER, leader);
        leader.addFollower();
    }

    @SuppressWarnings("rawtypes")
    public void addThievesFishFollowers(Stream<? extends AbstractSchoolingThievesFish> followers)
    {
        followers.limit(this.getMaxSchoolSize() - this.getSchoolSize()).filter(fish -> fish != this).forEach(fish -> fish.startFollowingThievesFish(this));
    }

    private boolean hasLeader()
    {
        return this.getBrain().hasMemoryValue(FOTMemoryModuleTypes.FLOCK_LEADER);
    }

    @SuppressWarnings("rawtypes")
    public AbstractSchoolingThievesFish getLeader()
    {
        return this.getBrain().getMemory(FOTMemoryModuleTypes.FLOCK_LEADER).get();
    }

    private void addFollower()
    {
        this.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, this.getSchoolSize() + 1);
    }

    public void removeFollower()
    {
        this.getBrain().setMemory(FOTMemoryModuleTypes.SCHOOL_SIZE, this.getSchoolSize() - 1);
    }

    private int getSchoolSize()
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
        super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (spawnData == null)
        {
            spawnData = new ThievesFishSchoolSpawnGroupData<>(this);
        }
        else
        {
            //noinspection rawtypes
            this.startFollowingThievesFish(((ThievesFishSchoolSpawnGroupData) spawnData).leader);
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

    public static class ThievesFishSchoolSpawnGroupData<T extends FishData> implements SpawnGroupData
    {
        public final AbstractSchoolingThievesFish<T> leader;

        public ThievesFishSchoolSpawnGroupData(AbstractSchoolingThievesFish<T> abstractSchoolingFish)
        {
            this.leader = abstractSchoolingFish;
        }
    }
}