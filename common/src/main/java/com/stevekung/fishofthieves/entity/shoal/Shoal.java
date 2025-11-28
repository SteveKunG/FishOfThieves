package com.stevekung.fishofthieves.entity.shoal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.ShoalBlock;
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
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Shoal extends Entity
{
    private static final List<EntityType<?>> COMMON_FISH = List.of(FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.ISLEHOPPER, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH);

    private static final List<EntityType<?>> TIER_1_FISH_QUEST = List.of(FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.WRECKER, FOTEntities.DEVILFISH, FOTEntities.ISLEHOPPER);
    private static final List<EntityType<?>> TIER_2_FISH_QUEST = List.of(FOTEntities.SPLASHTAIL, FOTEntities.WILDSPLASH, FOTEntities.BATTLEGILL, FOTEntities.PLENTIFIN, FOTEntities.STORMFISH);

    private static final EntityDataAccessor<Boolean> TREASURED = SynchedEntityData.defineId(Shoal.class, EntityDataSerializers.BOOLEAN);

    public static final String SHOAL_FISH_TAG = "shoal_fish";
    public static final String LIFETIME_TAG = "lifetime";
    public static final String NATURAL_TAG = "natural";
    public static final String TREASURED_TAG = "treasured";

    public static final String FILLED_MAP_TREASURED_FISH = "filled_map.fishofthieves_treasured_fish";

    private final List<ShoalFishData> shoalFishData = new ArrayList<>();
    private long expiredAt = -1;

    private List<LivingEntity> shoalFishClient = new ArrayList<>();

    public Shoal(EntityType<?> entityType, Level level)
    {
        super(entityType, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData()
    {
        this.getEntityData().define(TREASURED, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        this.shoalFishData.clear();
        var listTag = compound.getList(SHOAL_FISH_TAG, Tag.TAG_COMPOUND);

        for (var i = 0; i < listTag.size(); i++)
        {
            var compoundTag = listTag.getCompound(i);
            UUID uuid;

            if (!compoundTag.contains(ShoalFishData.ID_TAG, Tag.TAG_STRING) || compoundTag.getString(ShoalFishData.ID_TAG).isEmpty())
            {
                return;
            }

            if (compoundTag.hasUUID(ShoalFishData.UUID_TAG))
            {
                uuid = compoundTag.getUUID(ShoalFishData.UUID_TAG);
            }
            else
            {
                uuid = UUID.randomUUID();
            }

            this.shoalFishData.add(new ShoalFishData(compoundTag.getString(ShoalFishData.ID_TAG), uuid, compoundTag.getCompound(ShoalFishData.DATA_TAG)));
        }

        if (compound.contains(LIFETIME_TAG, Tag.TAG_LONG))
        {
            this.expiredAt = compound.getLong(LIFETIME_TAG);
        }
        if (compound.contains(TREASURED_TAG, Tag.TAG_BYTE))
        {
            this.setTreasured(compound.getBoolean(TREASURED_TAG));
        }

        if (!this.level().isClientSide())
        {
            if (compound.contains(NATURAL_TAG) && compound.getBoolean(NATURAL_TAG))
            {
                this.createNaturalSpawn(false);
            }
        }
        FOTPlatform.syncClientShoalFish(this, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        var listTag = new ListTag();

        for (var fish : this.shoalFishData)
        {
            var compoundTag = new CompoundTag();
            compoundTag.putString(ShoalFishData.ID_TAG, fish.id());
            compoundTag.putUUID(ShoalFishData.UUID_TAG, fish.uuid());
            compoundTag.put(ShoalFishData.DATA_TAG, fish.data());

            listTag.add(compoundTag);
        }

        compound.put(SHOAL_FISH_TAG, listTag);

        if (this.expiredAt > 0)
        {
            compound.putLong(LIFETIME_TAG, this.expiredAt);
        }

        compound.putBoolean(TREASURED_TAG, this.isTreasured());
    }

    @Override
    public boolean canBeHitByProjectile()
    {
        return false;
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
            var blockState = this.level().getBlockState(this.blockPosition());
            var canSurvive = ShoalBlock.canSurvive(this.level(), this.blockPosition());

            if ((this.level().getGameTime() % 20L == 0L && !canSurvive || !this.isTreasured() && this.level().getGameTime() >= this.expiredAt || this.shoalFishData.isEmpty() || !blockState.is(FOTBlocks.SHOAL)) && !this.isInvulnerable())
            {
                this.destroy();
            }

            if (this.random.nextInt(4) == 0)
            {
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.SPLASH, this.getX(), this.getY(0.8), this.getZ(), 10, this.getBbWidth() / 3.0F, this.getBbHeight() / 5.0F, this.getBbWidth() / 3.0F, 0);
            }

            ((ServerLevel) this.level()).sendParticles(ParticleTypes.BUBBLE, this.getX(), this.getY(0.5), this.getZ(), 1, this.getBbWidth() / 2.0F, this.getBbHeight() / 5.0F, this.getBbWidth() / 2.0F, 0);
        }
    }

    @Override
    public void move(MoverType type, Vec3 pos)
    {
        if (type == MoverType.PISTON)
        {
            this.destroy();
        }
        super.move(type, pos);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet)
    {
        super.recreateFromPacket(packet);
        FOTPlatform.requestServerShoalFish(this);
    }

    @Override
    public void kill()
    {
        super.kill();
        this.destroyShoalBlock();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance)
    {
        var d0 = 64.0 * getViewScale();
        return distance < d0 * d0;
    }

    public void setTreasured(boolean treasured)
    {
        this.getEntityData().set(TREASURED, treasured);
    }

    public boolean isTreasured()
    {
        return this.getEntityData().get(TREASURED);
    }

    public void destroy()
    {
        this.discard();
        this.destroyShoalBlock();
    }

    public void syncClientShoalFish(List<ShoalFishData> shoalFishData, boolean forcedUpdate)
    {
        if (this.shoalFishClient.isEmpty() || this.shoalFishClient.size() != shoalFishData.size() || forcedUpdate)
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
        var uuid = shoalFish.uuid();
        var compoundTag = shoalFish.data();
        compoundTag.putString("id", shoalFish.id());
        var entity = EntityType.loadEntityRecursive(compoundTag, this.level(), Function.identity());

        if (entity instanceof LivingEntity livingEntity)
        {
            this.shoalFishData.removeIf(shoalFishData1 -> shoalFishData1.uuid().equals(uuid));
            FOTPlatform.syncClientShoalFish(this, false);

            if (this.shoalFishData.isEmpty())
            {
                this.destroy();
            }

            return livingEntity;
        }
        else
        {
            FishOfThieves.LOGGER.warn("ShoalFishData with entity id {} is not a living entity", shoalFish.id());
            return null;
        }
    }

    public List<LivingEntity> getShoalFishClient()
    {
        return this.shoalFishClient;
    }

    public void createNaturalSpawn(boolean clientSync)
    {
        if (!(this.level() instanceof ServerLevel serverLevel))
        {
            return;
        }

        var commonFish = new ArrayList<>(COMMON_FISH);

        if (this.level().isThundering())
        {
            commonFish.add(FOTEntities.STORMFISH);
        }

        for (var entityType : pickRandom(commonFish))
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
                this.shoalFishData.add(new ShoalFishData(BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).toString(), UUID.randomUUID(), compoundTag));
            }
        }

        this.expiredAt = this.level().getGameTime() + FishOfThieves.CONFIG.shoal.maxLifetimeDay * 24000L;

        if (clientSync)
        {
            FOTPlatform.syncClientShoalFish(this, false);
        }
    }

    public void createTreasuredSpawn(int tier)
    {
        if (!(this.level() instanceof ServerLevel serverLevel))
        {
            return;
        }

        this.shoalFishData.clear();

        for (var entityType : pickRandom(tier == 1 ? TIER_1_FISH_QUEST : TIER_2_FISH_QUEST))
        {
            var entity = entityType.create(serverLevel);

            if (entity instanceof ThievesFish<?> thievesFish)
            {
                var compoundTag = new CompoundTag();
                var key = Util.getRandom(thievesFish.getRegistry()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().isTreasured().isPresent())
                        .map(entry -> entry.getKey().location()).toList(), this.random);
                compoundTag.putString(ThievesFish.VARIANT_TAG, key.toString());
                compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
                this.shoalFishData.add(new ShoalFishData(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString(), UUID.randomUUID(), compoundTag));
            }
        }
        this.expiredAt = -1;
        FOTPlatform.syncClientShoalFish(this, true);
    }

    public static void setTreasuredShoal(Level level, BlockPos blockPos, int tier)
    {
        level.setBlock(blockPos, FOTBlocks.SHOAL.defaultBlockState().setValue(ShoalBlock.TREASURED, true), ShoalBlock.UPDATE_CLIENTS);

        var shoals = level.getEntitiesOfClass(Shoal.class, new AABB(blockPos).inflate(1));

        if (!shoals.isEmpty())
        {
            var shoal = shoals.get(0);
            shoal.createTreasuredSpawn(tier);
            shoal.setTreasured(true);
        }
    }

    private void destroyShoalBlock()
    {
        var blockPos = this.blockPosition();
        var blockState = this.level().getBlockState(blockPos);

        if (blockState.is(FOTBlocks.SHOAL))
        {
            this.level().setBlock(blockPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
        }

        if (!this.level().isClientSide())
        {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY(1), this.getZ(), 10, this.getBbWidth() / 5.0F, this.getBbHeight() / 2.0F, this.getBbWidth() / 5.0F, 0);
            this.playSound(FOTSoundEvents.SHOAL_DEPLETE, 1.0f, 0.75f);
        }

        //noinspection ConstantValue
        if (FishOfThieves.CONFIG.debug.spawnBeaconAtShoal && FOTPlatform.isDevelopment())
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