package com.stevekung.fishofthieves.blockentity;

import org.jspecify.annotations.Nullable;

import com.stevekung.fishofthieves.block.FishPlaqueBlock;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class FishPlaqueBlockEntity extends BlockEntity
{
    private static final String OLD_PLAQUE_DATA = "plaque_data";
    public static final String PLAQUE_DATA_TAG = "PlaqueData";
    public static final String WAXED_TAG = "Waxed";

    private boolean waxed;
    private int animationTickCount;
    private boolean isAnimating;

    @Nullable
    private Entity displayEntity;

    @Nullable
    private CompoundTag plaqueData;

    public FishPlaqueBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(FOTBlockEntityTypes.FISH_PLAQUE, blockPos, blockState);
    }

    @Override
    public void loadAdditional(ValueInput valueInput)
    {
        valueInput.read(PLAQUE_DATA_TAG, CompoundTag.CODEC).ifPresent(data -> this.setPlaqueData(data.getCompound(OLD_PLAQUE_DATA).orElse(data)));
        this.waxed = valueInput.getBooleanOr(WAXED_TAG, false);
        this.displayEntity = null;
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput)
    {
        if (this.plaqueData != null)
        {
            valueOutput.storeNullable(PLAQUE_DATA_TAG, CompoundTag.CODEC, this.plaqueData);
        }
        valueOutput.putBoolean(WAXED_TAG, this.waxed);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider)
    {
        this.displayEntity = null;
        return this.saveWithoutMetadata(provider);
    }

    public void setPlaqueData(CompoundTag compoundTag)
    {
        this.plaqueData = compoundTag;
    }

    @Nullable
    public CompoundTag getPlaqueData()
    {
        return this.plaqueData;
    }

    public boolean hasPlaqueData()
    {
        return this.plaqueData != null && this.plaqueData.contains("id");
    }

    public void setWaxed(boolean waxed)
    {
        this.waxed = waxed;
    }

    public boolean isWaxed()
    {
        return this.waxed;
    }

    public String getEntityKeyFromPlaqueData()
    {
        return this.plaqueData.getString("id").orElseThrow();
    }

    public void clearDisplayEntity()
    {
        this.plaqueData = null;
        this.displayEntity = null;
    }

    public static void animation(Level level, BlockPos pos, BlockState state, FishPlaqueBlockEntity blockEntity)
    {
        if (state.hasProperty(FishPlaqueBlock.POWERED) && state.getValue(FishPlaqueBlock.POWERED))
        {
            blockEntity.isAnimating = true;
            ++blockEntity.animationTickCount;
        }
        else
        {
            blockEntity.isAnimating = false;
        }
    }

    public float getAnimation(float partialTick)
    {
        return this.isAnimating ? (float) this.animationTickCount + partialTick : (float) this.animationTickCount;
    }

    @Nullable
    public Entity getOrCreateDisplayEntity(Level level)
    {
        if (!this.hasPlaqueData())
        {
            return null;
        }
        if (this.displayEntity == null)
        {
            this.displayEntity = FishPlaqueBlockEntity.createEntity(this, level);
        }
        return this.displayEntity;
    }

    @Nullable
    public static Entity createEntity(FishPlaqueBlockEntity blockEntity, Level level)
    {
        return EntityType.loadEntityRecursive(blockEntity.getPlaqueData(), level, new EntitySpawnRequest(EntitySpawnReason.LOAD, true), BaseSpawner.SET_DISPLAY_ENTITY_ID);
    }
}