package com.stevekung.fishofthieves.storage;

import java.util.Map;

import com.google.common.collect.Maps;
import com.stevekung.fishofthieves.registry.FOTDataFixTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

public class BaitPreserveSavedData extends SavedData
{
    public static final String FILE_ID = "fishofthieves_bait_preserve";
    private static final String TAG_BAIT_PRESERVES = "bait_preserves";
    private final Map<Vec3, ItemStack> baitStorage;

    private BaitPreserveSavedData(Map<Vec3, ItemStack> baitStorage)
    {
        this.baitStorage = baitStorage;
    }

    public BaitPreserveSavedData()
    {
        this(Maps.newHashMap());
    }

    public static SavedData.Factory<BaitPreserveSavedData> factory()
    {
        return new SavedData.Factory<>(BaitPreserveSavedData::new, BaitPreserveSavedData::load, FOTDataFixTypes.SAVED_BAIT_PRESERVE);
    }

    public static BaitPreserveSavedData load(CompoundTag tag, HolderLookup.Provider provider)
    {
        var baitStorage = Maps.<Vec3, ItemStack>newHashMap();
        var listTag = tag.getList(TAG_BAIT_PRESERVES, CompoundTag.TAG_COMPOUND);

        for (var i = 0; i < listTag.size(); i++)
        {
            var compoundTag = listTag.getCompound(i);
            baitStorage.put(new Vec3(compoundTag.getDouble("x"), compoundTag.getDouble("y"), compoundTag.getDouble("z")), ItemStack.parse(provider, compoundTag.getCompound("item")).orElse(ItemStack.EMPTY));
        }
        return new BaitPreserveSavedData(baitStorage);
    }

    public void spawnBaitOnLoad(Level level)
    {
        for (var entry : this.baitStorage.entrySet())
        {
            var blockPos = BlockPos.containing(entry.getKey());

            if (level.isLoaded(blockPos))
            {
                var vec3 = Vec3.atLowerCornerWithOffset(blockPos, 0.5, 0.25, 0.5).offsetRandom(level.random, 0.3F);
                var itemEntity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), entry.getValue());
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
                this.baitStorage.remove(entry.getKey());
                this.setDirty();
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        if (!this.baitStorage.isEmpty())
        {
            var listTag = new ListTag();

            for (var entry : this.baitStorage.entrySet())
            {
                var baitStorageTag = new CompoundTag();
                var pos = entry.getKey();
                baitStorageTag.put("item", entry.getValue().save(provider, new CompoundTag()));
                baitStorageTag.putDouble("x", pos.x);
                baitStorageTag.putDouble("y", pos.y);
                baitStorageTag.putDouble("z", pos.z);
                listTag.add(baitStorageTag);
            }
            compoundTag.put(TAG_BAIT_PRESERVES, listTag);
        }
        return compoundTag;
    }

    public Map<Vec3, ItemStack> getBaitStorage()
    {
        return this.baitStorage;
    }
}