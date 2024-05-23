package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.ai.WreckerAi;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.WreckerVariants;
import com.stevekung.fishofthieves.utils.TerrainUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Wrecker extends AbstractThievesFish<WreckerVariant>
{
    private static final EntityDataAccessor<Holder<WreckerVariant>> VARIANT = SynchedEntityData.defineId(Wrecker.class, FOTDataSerializers.WRECKER_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:rose");
        map.put(0, "fishofthieves:rose");
        map.put(1, "fishofthieves:sun");
        map.put(2, "fishofthieves:blackcloud");
        map.put(3, "fishofthieves:snow");
        map.put(4, "fishofthieves:moon");
    };

    //@formatter:off
    private static final ImmutableList<SensorType<? extends Sensor<? super Wrecker>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            FOTSensorTypes.NON_CREATIVE_NEAREST_PLAYERS,
            SensorType.HURT_BY,
            FOTSensorTypes.EARTHWORMS_THIEVES_FISH_TEMPTATIONS,
            FOTSensorTypes.NEAREST_SHIPWRECK,
            FOTSensorTypes.NEAREST_MAGMA_BLOCK,
            FOTSensorTypes.WRECKER_ATTACKABLES,
            FOTSensorTypes.LOW_BRIGHTNESS
    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            // Common AI
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,

            // Avoid Repellent AI
            MemoryModuleType.NEAREST_REPELLENT,

            // Find Shipwreck AI
            FOTMemoryModuleTypes.NEAREST_SHIPWRECK,

            // Find Low Light AI
            FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS,

            // Attackable AI
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,

            // Tempting AI
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.IS_PANICKING
    );
    //@formatter:on

    public Wrecker(EntityType<? extends Wrecker> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected Brain.Provider<Wrecker> brainProvider()
    {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic)
    {
        return WreckerAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<Wrecker> getBrain()
    {
        return (Brain<Wrecker>) super.getBrain();
    }

    @Override
    protected void customServerAiStep()
    {
        this.level().getProfiler().push("wreckerBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().popPush("wreckerActivityUpdate");
        WreckerAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(VARIANT, Holder.direct(WreckerVariants.ROSE));
    }

    @Override
    public Registry<WreckerVariant> getRegistry()
    {
        return FOTRegistry.WRECKER_VARIANT;
    }

    @Override
    public void setVariant(WreckerVariant variant)
    {
        this.entityData.set(VARIANT, Holder.direct(variant));
    }

    @Override
    public WreckerVariant getVariant()
    {
        return this.entityData.get(VARIANT).value();
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
    public EntityDimensions getDefaultDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose).withEyeHeight(0.34F) : EntityDimensions.fixed(0.275F, 0.25F).withEyeHeight(0.175F);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);

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

    @SuppressWarnings("unused")
    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isWater = level.getFluidState(blockPos).is(FluidTags.WATER) && level.getBlockState(blockPos).is(Blocks.WATER);
        return isWater && TerrainUtils.isInFeature(level.getLevel(), blockPos, FOTTags.Structures.WRECKERS_SPAWN_IN);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }

    @Nullable
    public static BlockPos getNearestShipwreckPos(ServerLevel level, BlockPos pos)
    {
        return level.findNearestMapStructure(FOTTags.Structures.WRECKERS_LOCATED, pos, 32, false);
    }
}