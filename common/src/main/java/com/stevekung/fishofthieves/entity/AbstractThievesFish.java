package com.stevekung.fishofthieves.entity;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.variant.AbstractFishVariant;
import com.stevekung.fishofthieves.registry.FOTSensorTypes;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractThievesFish<T extends AbstractFishVariant> extends AbstractFish implements ThievesFish<T>
{
    private static final EntityDataAccessor<Boolean> TROPHY = SynchedEntityData.defineId(AbstractThievesFish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_FED = SynchedEntityData.defineId(AbstractThievesFish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> NO_FLIP = SynchedEntityData.defineId(AbstractThievesFish.class, EntityDataSerializers.BOOLEAN);

    //@formatter:off
    protected static final ImmutableList<SensorType<? extends Sensor<? super AbstractThievesFish<?>>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            FOTSensorTypes.NON_CREATIVE_NEAREST_PLAYERS,
            FOTSensorTypes.NEAREST_MAGMA_BLOCK,
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

            // Tempting AI
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.IS_PANICKING
    );
    //@formatter:on

    private final ResourceKey<? extends Registry<T>> registryKey;
    private final ResourceKey<T> resourceKey;

    public AbstractThievesFish(EntityType<? extends AbstractFish> entityType, Level level, ResourceKey<? extends Registry<T>> registryKey, ResourceKey<T> resourceKey)
    {
        super(entityType, level);
        this.refreshDimensions();
        this.registryKey = registryKey;
        this.resourceKey = resourceKey;
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    public ResourceKey<? extends Registry<T>> getRegistryKey()
    {
        return this.registryKey;
    }

    @Override
    public ResourceKey<T> getDefaultKey()
    {
        return this.resourceKey;
    }

    @Override
    protected void registerGoals() {}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(TROPHY, false);
        builder.define(HAS_FED, false);
        builder.define(NO_FLIP, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putString(VARIANT_TAG, this.getVariant().unwrapKey().orElse(this.getDefaultKey()).location().toString());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
        compound.putBoolean(HAS_FED_TAG, this.hasFed());
        compound.putBoolean(NO_FLIP_TAG, this.isNoFlip());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);

        Optional.ofNullable(ResourceLocation.tryParse(compound.getString(VARIANT_TAG))).map(resourceLocation -> ResourceKey.create(this.getRegistryKey(), resourceLocation)).flatMap(resourceKey -> this.registryAccess().registryOrThrow(this.getRegistryKey()).getHolder(resourceKey)).ifPresent(this::setVariant);
        this.setTrophy(compound.getBoolean(TROPHY_TAG));
        this.setHasFed(compound.getBoolean(HAS_FED_TAG));
        this.setNoFlip(compound.getBoolean(NO_FLIP_TAG));
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack)
    {
        super.saveToBucketTag(itemStack);
        this.saveToBucket(itemStack);
    }

    @Override
    public void loadFromBucketTag(CompoundTag compound)
    {
        super.loadFromBucketTag(compound);
        this.loadFromBucket(compound, this.registryAccess());

        if (!compound.contains(VARIANT_TAG))
        {
            var registry = this.registryAccess().registryOrThrow(this.registryKey);
            var muha = Util.getRandomSafe(registry.holders().toList(), this.getRandom());
            this.setVariant(muha.orElseGet(() -> registry.getHolderOrThrow(this.resourceKey)));
            this.setTrophy(this.random.nextBoolean());
        }
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
            if (!this.level().isClientSide())
            {
                this.growUp(player, itemStack);
            }
            this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData)
    {
        if (entitySpawnReason == EntitySpawnReason.BUCKET)
        {
            return spawnGroupData;
        }
        else
        {
            spawnGroupData = super.finalizeSpawn(accessor, difficulty, entitySpawnReason, spawnGroupData);
            return this.defaultFinalizeSpawn(accessor, this, entitySpawnReason, spawnGroupData);
        }
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