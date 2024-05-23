package com.stevekung.fishofthieves.entity.animal;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.stevekung.fishofthieves.entity.AbstractSchoolingThievesFish;
import com.stevekung.fishofthieves.entity.ai.BattlegillAi;
import com.stevekung.fishofthieves.entity.variant.BattlegillVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.BattlegillVariants;
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

public class Battlegill extends AbstractSchoolingThievesFish<BattlegillVariant>
{
    private static final EntityDataAccessor<Holder<BattlegillVariant>> VARIANT = SynchedEntityData.defineId(Battlegill.class, FOTDataSerializers.BATTLEGILL_VARIANT);
    public static final Consumer<Int2ObjectOpenHashMap<String>> DATA_FIX_MAP = map ->
    {
        map.defaultReturnValue("fishofthieves:jade");
        map.put(0, "fishofthieves:jade");
        map.put(1, "fishofthieves:sky");
        map.put(2, "fishofthieves:rum");
        map.put(3, "fishofthieves:sand");
        map.put(4, "fishofthieves:bittersweet");
    };

    //@formatter:off
    private static final ImmutableList<SensorType<? extends Sensor<? super Battlegill>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            FOTSensorTypes.NON_CREATIVE_NEAREST_PLAYERS,
            SensorType.HURT_BY,
            FOTSensorTypes.NEAREST_SCHOOLING_THIEVES_FISH,
            FOTSensorTypes.GRUBS_THIEVES_FISH_TEMPTATIONS,
            FOTSensorTypes.NEAREST_MAGMA_BLOCK,
            FOTSensorTypes.BATTLEGILL_ATTACKABLES
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

            // Attackable AI
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,

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

    public Battlegill(EntityType<? extends Battlegill> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    protected Brain.Provider<Battlegill> brainProvider()
    {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic)
    {
        return BattlegillAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<Battlegill> getBrain()
    {
        return (Brain<Battlegill>) super.getBrain();
    }

    @Override
    protected void customServerAiStep()
    {
        this.level().getProfiler().push("battlegillBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().popPush("battlegillActivityUpdate");
        BattlegillAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(VARIANT, Holder.direct(BattlegillVariants.JADE));
    }

    @Override
    public Registry<BattlegillVariant> getRegistry()
    {
        return FOTRegistry.BATTLEGILL_VARIANT;
    }

    @Override
    public void setVariant(BattlegillVariant variant)
    {
        this.entityData.set(VARIANT, Holder.direct(variant));
    }

    @Override
    public BattlegillVariant getVariant()
    {
        return this.entityData.get(VARIANT).value();
    }

    @Override
    public Holder<BattlegillVariant> getSpawnVariant(boolean fromBucket)
    {
        return this.getSpawnVariant(this, FOTTags.FishVariant.DEFAULT_BATTLEGILL_SPAWNS, BattlegillVariants.JADE, fromBucket);
    }

    @Override
    public Consumer<Int2ObjectOpenHashMap<String>> getDataFix()
    {
        return DATA_FIX_MAP;
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
    public EntityDimensions getDefaultDimensions(Pose pose)
    {
        return this.isTrophy() ? super.getDimensions(pose).withEyeHeight(0.3F) : EntityDimensions.fixed(0.275F, 0.275F).withEyeHeight(0.14F);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);

        if (this.isTrophy())
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0d);
        }
        return spawnData;
    }

    @Override
    public void setTrophy(boolean trophy)
    {
        if (trophy)
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.0d);
        }
        super.setTrophy(trophy);
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return GRUBS_FOOD.test(itemStack);
    }

    @SuppressWarnings("unused")
    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        var isWater = level.getFluidState(blockPos.below()).is(FluidTags.WATER) && level.getBlockState(blockPos.above()).is(Blocks.WATER);
        var isRaided = level.canSeeSkyFromBelowWater(blockPos) && level.getLevel().isRaided(blockPos);
        return isWater && (isRaided || TerrainUtils.isInFeature(level.getLevel(), blockPos, FOTTags.Structures.BATTLEGILLS_SPAWN_IN));
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.0).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }
}