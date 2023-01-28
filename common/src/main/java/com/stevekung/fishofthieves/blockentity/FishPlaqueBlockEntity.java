package com.stevekung.fishofthieves.blockentity;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import com.stevekung.fishofthieves.registry.FOTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FishPlaqueBlockEntity extends BlockEntity
{
    public static final String PLAQUE_DATA_TAG = "PlaqueData";

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
        var plaqueData = tag.getCompound(PLAQUE_DATA_TAG);

        if (plaqueData != null)
        {
            this.setPlaqueData(plaqueData);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag)
    {
        super.saveAdditional(tag);

        if (this.plaqueData != null)
        {
            tag.put(PLAQUE_DATA_TAG, this.plaqueData);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
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
        return this.plaqueData != null && this.plaqueData.contains("id");
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