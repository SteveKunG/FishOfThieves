package com.stevekung.fishofthieves.entity.shoal;

import java.util.*;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.block.ShoalBlock;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTBlocks;
import com.stevekung.fishofthieves.registry.FOTCriteriaTriggers;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTSoundEvents;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
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
    public static final String PARTICIPATES_TAG = "participates";

    public static final String FILLED_MAP_TREASURED_FISH = "filled_map.fishofthieves_treasured_fish";

    private final List<ShoalFishData> shoalFishData = new ArrayList<>();
    private final Set<UUID> participates = new HashSet<>();
    private long expiredAt = -1;

    private List<LivingEntity> shoalFishClient = new ArrayList<>();

    public Shoal(EntityType<?> entityType, Level level)
    {
        super(entityType, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(TREASURED, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        this.shoalFishData.clear();
        var shoalFishListTag = compound.getListOrEmpty(SHOAL_FISH_TAG);

        for (var i = 0; i < shoalFishListTag.size(); i++)
        {
            var compoundTag = shoalFishListTag.getCompoundOrEmpty(i);
            var uuid = compound.read(ShoalFishData.UUID_TAG, UUIDUtil.CODEC).orElse(UUID.randomUUID());

            if (!compoundTag.contains(ShoalFishData.ID_TAG) || compoundTag.getString(ShoalFishData.ID_TAG).isEmpty())
            {
                return;
            }
            this.shoalFishData.add(new ShoalFishData(compoundTag.getString(ShoalFishData.ID_TAG).orElseThrow(), uuid, compoundTag.getCompoundOrEmpty(ShoalFishData.DATA_TAG)));
        }

        if (compound.contains(LIFETIME_TAG))
        {
            this.expiredAt = compound.getLongOr(LIFETIME_TAG, -1);
        }
        if (compound.contains(TREASURED_TAG))
        {
            this.setTreasured(compound.getBooleanOr(TREASURED_TAG, false));
        }

        var participatesListTag = compound.getListOrEmpty(PARTICIPATES_TAG);

        for (var i = 0; i < participatesListTag.size(); i++)
        {
            var compoundTag = participatesListTag.getCompoundOrEmpty(i);
            compoundTag.read(UUID_TAG, UUIDUtil.CODEC).ifPresent(this.participates::add);
        }

        if (!this.level().isClientSide())
        {
            if (compound.contains(NATURAL_TAG) && compound.getBooleanOr(NATURAL_TAG, false))
            {
                this.createNaturalSpawn(false);
            }
        }
        FOTPlatform.syncClientShoalFish(this, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        var shoalFishListTag = new ListTag();

        for (var fish : this.shoalFishData)
        {
            var compoundTag = new CompoundTag();
            compoundTag.putString(ShoalFishData.ID_TAG, fish.id());
            compound.store(ShoalFishData.UUID_TAG, UUIDUtil.CODEC, fish.uuid());
            compoundTag.put(ShoalFishData.DATA_TAG, fish.data());

            shoalFishListTag.add(compoundTag);
        }

        compound.put(SHOAL_FISH_TAG, shoalFishListTag);

        if (this.expiredAt > 0)
        {
            compound.putLong(LIFETIME_TAG, this.expiredAt);
        }

        compound.putBoolean(TREASURED_TAG, this.isTreasured());

        var participatesListTag = new ListTag();

        for (var uuid : this.participates)
        {
            var compoundTag = new CompoundTag();
            compoundTag.store(UUID_TAG, UUIDUtil.CODEC, uuid);
            participatesListTag.add(compoundTag);
        }

        compound.put(PARTICIPATES_TAG, participatesListTag);
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

            if (!this.shoalFishData.isEmpty())
            {
                var soundChance = 40 / this.shoalFishData.size();

                if (this.random.nextInt(Math.max(15, soundChance)) == 0)
                {
                    this.playSound(SoundEvents.FISH_SWIM, 0.1F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                }
            }
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
    public void kill(ServerLevel level)
    {
        super.kill(level);
        this.destroyShoalBlock();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance)
    {
        var d0 = 64.0 * getViewScale();
        return distance < d0 * d0;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount)
    {
        if (damageSource.is(DamageTypeTags.IS_EXPLOSION))
        {
            this.destroy();
            return true;
        }
        return false;
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
                        return EntityType.loadEntityRecursive(compoundTag, this.level(), EntitySpawnReason.LOAD, Function.identity());
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
        var entity = EntityType.loadEntityRecursive(compoundTag, this.level(), EntitySpawnReason.LOAD, Function.identity());

        if (entity instanceof LivingEntity livingEntity)
        {
            this.shoalFishData.removeIf(shoalFishData1 -> shoalFishData1.uuid().equals(uuid));
            FOTPlatform.syncClientShoalFish(this, false);

            if (this.shoalFishData.isEmpty())
            {
                for (var participate : this.participates)
                {
                    var player = this.level().getPlayerByUUID(participate);

                    if (player instanceof ServerPlayer serverPlayer)
                    {
                        FOTCriteriaTriggers.PARTICIPATE_SHOAL.trigger(serverPlayer);
                    }
                }
                this.destroy();
            }

            if (livingEntity instanceof Mob mob && livingEntity instanceof ThievesFish<?> thievesFish && thievesFish.isTreasured())
            {
                mob.setPersistenceRequired();
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

        for (var entityType : pickRandom(commonFish, 3))
        {
            var entity = entityType.create(serverLevel, EntitySpawnReason.LOAD);

            if (entity instanceof Mob mob)
            {
                var compoundTag = new CompoundTag();
                mob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(entity.blockPosition()), EntitySpawnReason.NATURAL, null);

                var tempTag = mob.saveWithoutId(new CompoundTag());

                if (mob instanceof ThievesFish<?>)
                {
                    compoundTag.putString(ThievesFish.VARIANT_TAG, tempTag.getString(ThievesFish.VARIANT_TAG).orElseThrow());
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

        var prevShoalSize = this.shoalFishData.size();
        this.shoalFishData.clear();

        for (var entityType : pickRandom(tier == 1 ? TIER_1_FISH_QUEST : TIER_2_FISH_QUEST, prevShoalSize))
        {
            var entity = entityType.create(serverLevel, EntitySpawnReason.LOAD);

            if (entity instanceof ThievesFish<?> thievesFish)
            {
                var compoundTag = new CompoundTag();
                var key = Util.getRandom(this.registryAccess().lookupOrThrow(thievesFish.getRegistryKey())
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().treasured().isPresent())
                        .map(entry -> entry.getKey().location()).toList(), this.random);
                compoundTag.putString(ThievesFish.VARIANT_TAG, key.toString());
                compoundTag.putBoolean(ThievesFish.TROPHY_TAG, true);
                this.shoalFishData.add(new ShoalFishData(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString(), UUID.randomUUID(), compoundTag));
            }
        }
        this.expiredAt = -1;
        FOTPlatform.syncClientShoalFish(this, true);
    }

    public void addParticipatePlayer(UUID uuid)
    {
        this.participates.add(uuid);
    }

    public static void setTreasuredShoal(Level level, BlockPos blockPos, int tier)
    {
        level.setBlock(blockPos, FOTBlocks.SHOAL.defaultBlockState().setValue(ShoalBlock.TREASURED, true), ShoalBlock.UPDATE_CLIENTS);

        var shoals = level.getEntitiesOfClass(Shoal.class, new AABB(blockPos).inflate(1));

        if (!shoals.isEmpty())
        {
            var shoal = shoals.getFirst();
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

    private static List<? extends EntityType<?>> pickRandom(List<EntityType<?>> list, int count)
    {
        return new Random().ints(0, list.size()).distinct().limit(count).mapToObj(list::get).toList();
    }
}