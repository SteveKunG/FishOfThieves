package com.stevekung.fishofthieves.entity.shoal;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

public class Shoal extends Entity
{
    private static final EntityDataAccessor<Float> DATA_WIDTH_ID = SynchedEntityData.defineId(Shoal.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = SynchedEntityData.defineId(Shoal.class, EntityDataSerializers.FLOAT);

    public Shoal(EntityType<?> entityType, Level level)
    {
        super(entityType, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(DATA_WIDTH_ID, 2.0F);
        this.entityData.define(DATA_HEIGHT_ID, 1.0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        if (compound.contains("width", 99))
        {
            this.setWidth(compound.getFloat("width"));
        }
        if (compound.contains("height", 99))
        {
            this.setHeight(compound.getFloat("height"));
        }

        this.setBoundingBox(this.makeBoundingBox());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        compound.putFloat("width", this.getWidth());
        compound.putFloat("height", this.getHeight());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key)
    {
        super.onSyncedDataUpdated(key);
        if (DATA_HEIGHT_ID.equals(key) || DATA_WIDTH_ID.equals(key))
        {
            this.setBoundingBox(this.makeBoundingBox());
        }
    }

    @Override
    public boolean canBeHitByProjectile()
    {
        return false;
    }

    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Override
    public PushReaction getPistonPushReaction()
    {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean skipAttackInteraction(Entity entity)
    {
        if (entity instanceof Player player)
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger(serverPlayer, this, player.damageSources().generic(), 1.0F, 1.0F, false);
            }

            return false;
        }
        else
        {
            return false;
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand)
    {
            return InteractionResult.PASS;
    }

    @Override
    public void tick()
    {
    }

    private void setWidth(float width)
    {
        this.entityData.set(DATA_WIDTH_ID, width);
    }

    private float getWidth()
    {
        return this.entityData.get(DATA_WIDTH_ID);
    }

    private void setHeight(float height)
    {
        this.entityData.set(DATA_HEIGHT_ID, height);
    }

    private float getHeight()
    {
        return this.entityData.get(DATA_HEIGHT_ID);
    }

    private EntityDimensions getDimensions()
    {
        return EntityDimensions.scalable(this.getWidth(), this.getHeight());
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        return this.getDimensions();
    }

    @Override
    protected AABB makeBoundingBox()
    {
        return this.getDimensions().makeBoundingBox(this.position());
    }
}