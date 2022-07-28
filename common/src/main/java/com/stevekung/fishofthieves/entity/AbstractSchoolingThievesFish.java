package com.stevekung.fishofthieves.entity;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.core.FishOfThieves;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractSchoolingThievesFish<T extends FishData> extends AbstractSchoolingFish implements ThievesFish<T>
{
    private static final EntityDataAccessor<Boolean> TROPHY = SynchedEntityData.defineId(AbstractSchoolingThievesFish.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_FED = SynchedEntityData.defineId(AbstractSchoolingThievesFish.class, EntityDataSerializers.BOOLEAN);

    public AbstractSchoolingThievesFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level)
    {
        super(entityType, level);
        this.refreshDimensions();
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(TROPHY, false);
        this.entityData.define(HAS_FED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putString(VARIANT_TAG, this.getRegistry().getKey(this.getVariant()).toString());
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
        compound.putBoolean(HAS_FED_TAG, this.hasFed());
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
            this.growUp(player, itemStack);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag)
    {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
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
        }
        this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
    }
}