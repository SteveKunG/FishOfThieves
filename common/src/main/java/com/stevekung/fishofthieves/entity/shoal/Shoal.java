package com.stevekung.fishofthieves.entity.shoal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.registry.FOTEntities;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

public class Shoal extends Entity
{
    private static final List<EntityType<?>> COMMON_FISH = List.of(FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.ISLEHOPPER, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH);

    private static final List<EntityType<?>> TIER_1_FISH_QUEST = List.of(FOTEntities.PONDIE, FOTEntities.ANCIENTSCALE, FOTEntities.WRECKER, FOTEntities.DEVILFISH, FOTEntities.ISLEHOPPER);
    private static final List<EntityType<?>> TIER_2_FISH_QUEST = List.of(FOTEntities.SPLASHTAIL, FOTEntities.WILDSPLASH, FOTEntities.BATTLEGILL, FOTEntities.PLENTIFIN, FOTEntities.STORMFISH);

    private static final Function<Level, List<Entity>> TIER1 = Util.memoize(level -> pickRandom(TIER_1_FISH_QUEST).stream().map(entityType -> (Entity) entityType.create(level)).peek(entity -> entity.wasTouchingWater = true).toList());
    private static final Function<Level, List<Entity>> TIER2 = Util.memoize(level -> pickRandom(TIER_2_FISH_QUEST).stream().map(entityType -> (Entity) entityType.create(level)).peek(entity -> entity.wasTouchingWater = true).toList());

//    private final List<ShoalFish> shoalFish = new ArrayList<>(List.of(new ShoalFish(EntityType.getKey(FOTEntities.SPLASHTAIL).toString(), Util.make(new CompoundTag(), tag -> tag.putString("variant", "fishofthieves:seafoam")))));
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
        var listTag = compound.getList("shoal_fish", CompoundTag.TAG_COMPOUND);

        for (var i = 0; i < listTag.size(); i++)
        {
            var compoundTag = listTag.getCompound(i);
//            System.out.println(compoundTag);
            this.shoalFishData.add(new ShoalFishData(compoundTag.getString("id"), compoundTag.getCompound("data")));
        }

        if (!this.level().isClientSide())
        {
            FOTPlatform.syncShoalFish(this);
            System.out.println("send packet on readAdditionalSaveData");
        }
        //(LivingEntity) Util.getRandom(COMMON_FISH, this.random).create(this.level())
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        var listTag = new ListTag();

        for (var fish : this.shoalFishData)
        {
            var compoundTag = new CompoundTag();
            compoundTag.putString("id", fish.id());
            compoundTag.put("data", fish.data());

            listTag.add(compoundTag);
        }
        compound.put("shoal_fish", listTag);
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
        System.out.println("recreateFromPacket");
        FOTPlatform.requestShoalFish(this);
    }

    public void syncShoalFish(List<ShoalFishData> shoalFishData)
    {
        System.out.println("shoalFish " + shoalFishData);
        if (this.shoalFishClient.isEmpty() || this.shoalFishClient.size() != this.shoalFishData.size())
        {
            this.shoalFishClient = shoalFishData.stream()
                    .map(shoalFishData1 ->
                    {
                        var compoundTag = shoalFishData1.data();
                        compoundTag.putString("id", shoalFishData1.id());
//                        System.out.println(compoundTag);
                        return EntityType.loadEntityRecursive(compoundTag, this.level(), Function.identity());
                    })
                    .filter(LivingEntity.class::isInstance)
                    .map(LivingEntity.class::cast)
                    .peek(livingEntity -> livingEntity.wasTouchingWater = true)
                    .toList();
        }
        System.out.println("SYNCCCCC");
    }

    public List<ShoalFishData> getShoalFish()
    {
        return this.shoalFishData;
    }

    public List<LivingEntity> getShoalFishClient()
    {
        return this.shoalFishClient;
    }

    private static List<? extends EntityType<?>> pickRandom(List<EntityType<?>> list)
    {
        return new Random().ints(0, list.size()).distinct().limit(3).mapToObj(list::get).toList();
    }
}