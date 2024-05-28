package com.stevekung.fishofthieves.common.blockentity;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.common.registry.FOTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FishPlaqueBlockEntity extends BlockEntity
{
    public static final String PLAQUE_DATA_TAG = "PlaqueData";
    public static final String WAXED_TAG = "Waxed";

    private boolean waxed;

    @Nullable
    private Entity displayEntity;

    @Nullable
    private CompoundTag plaqueData;

    public FishPlaqueBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(FOTBlockEntityTypes.FISH_PLAQUE, blockPos, blockState);
    }

    @Override
    public void load(CompoundTag tag)
    {
        super.load(tag);

        if (tag.contains(PLAQUE_DATA_TAG, Tag.TAG_COMPOUND))
        {
            this.setPlaqueData(tag.getCompound(PLAQUE_DATA_TAG));
        }
        this.waxed = tag.getBoolean(WAXED_TAG);
        this.displayEntity = null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        if (this.plaqueData != null)
        {
            tag.put(PLAQUE_DATA_TAG, this.plaqueData);
        }
        tag.putBoolean(WAXED_TAG, this.waxed);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        this.displayEntity = null;
        return this.saveWithoutMetadata();
    }

    @Override
    public boolean onlyOpCanSetNbt()
    {
        return true;
    }

    public void setPlaqueData(CompoundTag plaqueData)
    {
        this.plaqueData = plaqueData;
    }

    @Nullable
    public CompoundTag getPlaqueData()
    {
        return this.plaqueData;
    }

    public boolean hasPlaqueData()
    {
        return this.plaqueData != null && this.plaqueData.contains("id", Tag.TAG_STRING);
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
        return this.plaqueData.getString("id");
    }

    public void clearDisplayEntity()
    {
        this.plaqueData = null;
        this.displayEntity = null;
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
        return EntityType.loadEntityRecursive(blockEntity.getPlaqueData(), level, Function.identity());
    }
}