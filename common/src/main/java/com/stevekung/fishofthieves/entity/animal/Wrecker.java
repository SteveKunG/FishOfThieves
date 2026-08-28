package com.stevekung.fishofthieves.entity.animal;

import java.util.List;

import org.jspecify.annotations.Nullable;

import com.stevekung.fishofthieves.entity.AbstractThievesFish;
import com.stevekung.fishofthieves.entity.ai.WreckerAi;
import com.stevekung.fishofthieves.entity.variant.WreckerVariant;
import com.stevekung.fishofthieves.registry.*;
import com.stevekung.fishofthieves.registry.variant.WreckerVariants;
import com.stevekung.fishofthieves.utils.TerrainUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.ValueInput;

public class Wrecker extends AbstractThievesFish<WreckerVariant>
{
    private static final EntityDataAccessor<Holder<WreckerVariant>> VARIANT = SynchedEntityData.defineId(Wrecker.class, FOTDataSerializers.WRECKER_VARIANT);

    private static final List<SensorType<? extends Sensor<? super Wrecker>>> SENSOR_TYPES = List.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            FOTSensorTypes.NON_CREATIVE_NEAREST_PLAYERS,
            SensorType.HURT_BY,
            FOTSensorTypes.EARTHWORMS_THIEVES_FISH_TEMPTATIONS,
            FOTSensorTypes.NEAREST_WRECKER_LOCATED,
            FOTSensorTypes.NEAREST_MAGMA_BLOCK,
            FOTSensorTypes.WRECKER_ATTACKABLES,
            FOTSensorTypes.LOW_BRIGHTNESS
    );
    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(
            // Common AI
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,

            // Avoid Repellent AI
            MemoryModuleType.NEAREST_REPELLENT,

            // Find Shipwreck or Ruined Portal AI
            FOTMemoryModuleTypes.NEAREST_WRECKER_LOCATED,

            // Find Low Light AI
            FOTMemoryModuleTypes.NEAREST_LOW_BRIGHTNESS,

            // Attackable AI
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,

            // Tempting AI
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.IS_PANICKING,

            // Jump AI
            MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS,
            FOTMemoryModuleTypes.BREACHED_TICK
    );

    @SuppressWarnings("deprecation")
    private static final Brain.Provider<Wrecker> BRAIN_PROVIDER = Brain.provider(MEMORY_TYPES, SENSOR_TYPES, _ -> WreckerAi.getActivities());

    public Wrecker(EntityType<? extends Wrecker> entityType, Level level)
    {
        super(entityType, level, FOTRegistries.WRECKER_VARIANT, WreckerVariants.ROSE, FOTDataComponentTypes.WRECKER_VARIANT);
    }

    @Override
    protected Brain<?> makeBrain(Brain.Packed packedBrain)
    {
        return BRAIN_PROVIDER.makeBrain(this, packedBrain);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<Wrecker> getBrain()
    {
        return (Brain<Wrecker>) super.getBrain();
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel)
    {
        var profiler = Profiler.get();
        profiler.push("wreckerBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        profiler.popPush("wreckerActivityUpdate");
        WreckerAi.updateActivity(this);
        profiler.pop();
        super.customServerAiStep(serverLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(VARIANT, this.registryAccess().lookupOrThrow(FOTRegistries.WRECKER_VARIANT).getOrThrow(WreckerVariants.ROSE));
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput)
    {
        super.readAdditionalSaveData(valueInput);
        WreckerAi.initMemories(this);
    }

    @Override
    public Holder<WreckerVariant> getVariant()
    {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<WreckerVariant> variant)
    {
        this.entityData.set(VARIANT, variant);
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
        return this.isTrophy() ? super.getDefaultDimensions(pose).withEyeHeight(0.34F) : EntityDimensions.fixed(0.275F, 0.25F).withEyeHeight(0.175F);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData)
    {
        if (this.isTrophy())
        {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2.5d);
        }
        return super.finalizeSpawn(level, difficulty, entitySpawnReason, spawnGroupData);
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
        return itemStack.is(EARTHWORMS_FOOD);
    }

    @SuppressWarnings("unused")
    public static boolean checkSpawnRules(EntityType<? extends WaterAnimal> entityType, ServerLevelAccessor level, EntitySpawnReason entitySpawnReason, BlockPos blockPos, RandomSource random)
    {
        var isWater = level.getFluidState(blockPos).is(FluidTags.WATER) && level.getBlockState(blockPos).is(Blocks.WATER);
        return isWater && TerrainUtils.isInFeature(level.getLevel(), blockPos, FOTTags.Structures.WRECKERS_SPAWN_IN);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.FOLLOW_RANGE, 10.0).add(Attributes.TEMPT_RANGE, 10.0).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.ATTACK_KNOCKBACK, 0.01);
    }

    @Nullable
    public static BlockPos getNearestShipwreckOrRuinedPortalPos(ServerLevel level, BlockPos pos, ChunkPos chunkPos)
    {
        var structureRegistry = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);
        var structureHolderSet = structureRegistry.getTagOrEmpty(FOTTags.Structures.WRECKERS_LOCATED);
        var structureRange = 32;
        var distFromStructure = Integer.MAX_VALUE;
        Structure structure1 = null;
        ChunkPos chunkPos1 = null;

        for (var structureHolder : structureHolderSet)
        {
            var structure = structureHolder.value();
            var structureRefMap = level.getChunk(chunkPos.x(), chunkPos.z(), ChunkStatus.STRUCTURE_STARTS).getAllReferences();
            var optional = structureRefMap.keySet().stream().filter(structurex -> structurex.equals(structure)).findAny();

            if (optional.isPresent())
            {
                structure1 = optional.get();
                chunkPos1 = chunkPos;
            }
        }

        if (structure1 != null)
        {
            for (var structureStart : level.structureManager().startsForStructure(chunkPos1.x(), chunkPos1.z(), structure1))
            {
                var structureCenter = structureStart.getPieces()
                        .stream()
                        .map(structurePiece -> structurePiece.getBoundingBox().getCenter())
                        .findAny();

                if (structureCenter.isPresent())
                {
                    var range = structureCenter.get().distManhattan(pos);

                    // Get nearest structure range
                    if (range < distFromStructure)
                    {
                        distFromStructure = range;
                    }
                    // Found structure within radius
                    if (distFromStructure < structureRange)
                    {
                        return structureCenter.get();
                    }
                }
            }
        }
        return null;
    }
}