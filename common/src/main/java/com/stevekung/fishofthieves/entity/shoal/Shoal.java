package com.stevekung.fishofthieves.entity.shoal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ThievesFish;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

public class Shoal extends Entity
{
    private static final List<EntityType<?>> COMMON_FISH = List.of(FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.ISLEHOPPER, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH);

    private static final List<EntityType<?>> TIER_1_FISH_QUEST = List.of(FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.WRECKER, FOTEntities.DEVILFISH, FOTEntities.ISLEHOPPER);
    private static final List<EntityType<?>> TIER_2_FISH_QUEST = List.of(FOTEntities.SPLASHTAIL, FOTEntities.WILDSPLASH, FOTEntities.BATTLEGILL, FOTEntities.PLENTIFIN, FOTEntities.STORMFISH);

    public static final String SHOAL_FISH_TAG = "shoal_fish";
    public static final String NATURAL_TAG = "natural";

    private final List<ShoalFishData> shoalFishData = new ArrayList<>();
    private List<LivingEntity> shoalFishClient = new ArrayList<>();

    public Shoal(EntityType<?> entityType, Level level)
    {
        super(entityType, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData()
    {
    }

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
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet)
    {
        super.recreateFromPacket(packet);
        FOTPlatform.requestShoalFish(this);
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

            //TODO Disappear particle
            if (this.shoalFishData.isEmpty())
            {
                this.discard();
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
    }

    private static List<? extends EntityType<?>> pickRandom(List<EntityType<?>> list)
    {
        return new Random().ints(0, list.size()).distinct().limit(3).mapToObj(list::get).toList();
    }
}