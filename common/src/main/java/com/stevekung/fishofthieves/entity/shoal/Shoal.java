package com.stevekung.fishofthieves.entity.shoal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.PushReaction;

public class Shoal extends Entity
{
    private static final List<EntityType<?>> COMMON_FISH = List.of(FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.ISLEHOPPER, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH);

    private static final List<EntityType<?>> TIER_1_FISH_QUEST = List.of(FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.WRECKER, FOTEntities.DEVILFISH, FOTEntities.ISLEHOPPER);
    private static final List<EntityType<?>> TIER_2_FISH_QUEST = List.of(FOTEntities.SPLASHTAIL, FOTEntities.WILDSPLASH, FOTEntities.BATTLEGILL, FOTEntities.PLENTIFIN, FOTEntities.STORMFISH);

    public static final String SHOAL_FISH_TAG = "shoal_fish";
    public static final String LIFE_TIME_TAG = "life_time";
    public static final String NATURAL_TAG = "natural";

    private final List<ShoalFishData> shoalFishData = new ArrayList<>();
    private long expiredAt = -1;

    private List<LivingEntity> shoalFishClient = new ArrayList<>();

    public Shoal(EntityType<?> entityType, Level level)
    {
        super(entityType, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        this.shoalFishData.clear();
        var listTag = compound.getList(SHOAL_FISH_TAG, CompoundTag.TAG_COMPOUND);

        for (var i = 0; i < listTag.size(); i++)
        {
            var compoundTag = listTag.getCompound(i);
            this.shoalFishData.add(new ShoalFishData(compoundTag.getString(ShoalFishData.ID_TAG), compoundTag.getCompound(ShoalFishData.DATA_TAG)));
        }

        this.expiredAt = compound.getLong(LIFE_TIME_TAG);

        if (!this.level().isClientSide())
        {
            if (compound.contains(NATURAL_TAG) && compound.getBoolean(NATURAL_TAG))
            {
                this.createNaturalSpawn();
            }
            FOTPlatform.syncShoalFish(this);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        var listTag = new ListTag();

        for (var fish : this.shoalFishData)
        {
            var compoundTag = new CompoundTag();
            compoundTag.putString(ShoalFishData.ID_TAG, fish.id());
            compoundTag.put(ShoalFishData.DATA_TAG, fish.data());

            listTag.add(compoundTag);
        }
        compound.put(SHOAL_FISH_TAG, listTag);
        compound.putLong(LIFE_TIME_TAG, this.expiredAt);
    }

    @Override
    public boolean canBeHitByProjectile()
    {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction()
    {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean skipAttackInteraction(Entity entity)
    {
        return true;
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!this.level().isClientSide())
        {
            if ((this.level().getGameTime() >= this.expiredAt || this.shoalFishData.isEmpty()) && !this.isInvulnerable())
            {
                this.discard();
                this.destroyShoalBlock();
            }

            if (this.random.nextInt(4) == 0)
            {
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.SPLASH, this.getX(), this.getY(0.8), this.getZ(), 10, this.getBbWidth() / 3.0F, this.getBbHeight() / 5.0F, this.getBbWidth() / 3.0F, 0);
            }

            ((ServerLevel) this.level()).sendParticles(ParticleTypes.BUBBLE, this.getX(), this.getY(0.5), this.getZ(), 1, this.getBbWidth() / 2.0F, this.getBbHeight() / 5.0F, this.getBbWidth() / 2.0F, 0);
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet)
    {
        super.recreateFromPacket(packet);
        FOTPlatform.requestShoalFish(this);
    }

    @Override
    public void kill()
    {
        super.kill();
        this.destroyShoalBlock();
    }

    public void syncShoalFish(List<ShoalFishData> shoalFishData)
    {
        if (this.shoalFishClient.isEmpty() || this.shoalFishClient.size() != shoalFishData.size())
        {
            this.shoalFishClient = shoalFishData.stream()
                    .map(shoalFishData1 ->
                    {
                        var compoundTag = shoalFishData1.data();
                        compoundTag.putString("id", shoalFishData1.id());
                        return EntityType.loadEntityRecursive(compoundTag, this.level(), Function.identity());
                    })
                    .filter(LivingEntity.class::isInstance)
                    .map(LivingEntity.class::cast)
                    .peek(livingEntity -> livingEntity.wasTouchingWater = true)
                    .toList();
        }
    }

    public List<ShoalFishData> getShoalFish()
    {
        return this.shoalFishData;
    }

    @Nullable
    public LivingEntity getRandomFishInShoal()
    {
        var shoalFish = Util.getRandom(this.shoalFishData, this.random);
        var id = shoalFish.id();
        var compoundTag = shoalFish.data();
        compoundTag.putString("id", id);
        var entity = EntityType.loadEntityRecursive(compoundTag, this.level(), Function.identity());

        if (entity instanceof LivingEntity livingEntity)
        {
            this.shoalFishData.removeIf(shoalFishData1 -> shoalFishData1.id().equals(id));
            FOTPlatform.syncShoalFish(this);

            if (this.shoalFishData.isEmpty())
            {
                this.discard();
                this.destroyShoalBlock();
            }

            return livingEntity;
        }
        else
        {
            FishOfThieves.LOGGER.warn("ShoalFishData with entity id {} is not a living entity", id);
            return null;
        }
    }

    public List<LivingEntity> getShoalFishClient()
    {
        return this.shoalFishClient;
    }

    public void createNaturalSpawn()
    {
        if (!(this.level() instanceof ServerLevel serverLevel))
        {
            return;
        }

        for (var entityType : pickRandom(COMMON_FISH))
        {
            var entity = entityType.create(serverLevel);

            if (entity instanceof Mob mob)
            {
                var compoundTag = new CompoundTag();
                mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.NATURAL, null, null);

                var tempTag = mob.saveWithoutId(new CompoundTag());

                if (mob instanceof ThievesFish<?>)
                {
                    compoundTag.putString(ThievesFish.VARIANT_TAG, tempTag.getString(ThievesFish.VARIANT_TAG));
                    compoundTag.putBoolean(ThievesFish.TROPHY_TAG, this.random.nextFloat() < FishOfThieves.CONFIG.spawnRate.trophyProbability);
                }
                this.shoalFishData.add(new ShoalFishData(BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).toString(), compoundTag));
            }
        }
        this.expiredAt = this.level().getGameTime() + FishOfThieves.CONFIG.shoal.maxLifeTimeDay * 24000L;
    }

    private void destroyShoalBlock()
    {
        var blockPos = this.blockPosition();
        var blockState = this.level().getBlockState(blockPos);

        if (blockState.is(FOTBlocks.SHOAL_BLOCK))
        {
            this.level().setBlock(blockPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
        }

        if (!this.level().isClientSide())
        {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(1), this.getZ(), 10, this.getBbWidth() / 5.0F, this.getBbHeight() / 2.0F, this.getBbWidth() / 5.0F, 0);
            this.playSound(FOTSoundEvents.SHOAL_DEPLETE, 1.0f, 0.75f);
        }

        if (FishOfThieves.CONFIG.debug.spawnBeaconAtShoal)
        {
            for (var blockPos1 : BlockPos.betweenClosed(blockPos.offset(-1, -4, -1), blockPos.offset(1, -4, 1)))
            {
                if (this.level().getBlockState(blockPos1).is(Blocks.IRON_BLOCK))
                {
                    this.level().setBlock(blockPos1, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
            if (this.level().getBlockState(blockPos.below(3)).is(Blocks.BEACON))
            {
                this.level().setBlock(blockPos.below(3), Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    private static List<? extends EntityType<?>> pickRandom(List<EntityType<?>> list)
    {
        return new Random().ints(0, list.size()).distinct().limit(3).mapToObj(list::get).toList();
    }
}