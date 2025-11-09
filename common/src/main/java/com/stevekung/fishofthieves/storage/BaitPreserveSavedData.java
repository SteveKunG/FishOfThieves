package com.stevekung.fishofthieves.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stevekung.fishofthieves.registry.FOTDataFixTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.phys.Vec3;

public class BaitPreserveSavedData extends SavedData
{
    public static final String FILE_ID = "fishofthieves_bait_preserve";
    private static final String TAG_BAIT_PRESERVES = "bait_preserves";
    public static final Codec<BaitPreserveSavedData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            BaitPreserve.CODEC.listOf().optionalFieldOf(TAG_BAIT_PRESERVES, List.of())
                                    .forGetter(data -> data.baitStorage.entrySet().stream().map(BaitPreserve::from).toList())
                    )
                    .apply(instance, BaitPreserveSavedData::new));

    public static final SavedDataType<BaitPreserveSavedData> TYPE = new SavedDataType<>(FILE_ID,
            BaitPreserveSavedData::new, CODEC, FOTDataFixTypes.SAVED_BAIT_PRESERVE);

    private final Map<Vec3, ItemStack> baitStorage = new HashMap<>();

    public BaitPreserveSavedData(List<BaitPreserve> list)
    {
        for (var baitPreserve : list)
        {
            this.baitStorage.put(baitPreserve.toVec(), baitPreserve.itemStack());
        }
    }

    public BaitPreserveSavedData()
    {
        this.setDirty();
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

    public Map<Vec3, ItemStack> getBaitStorage()
    {
        return this.baitStorage;
    }

    public record BaitPreserve(ItemStack itemStack, double x, double y, double z)
    {
        public static final Codec<BaitPreserve> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        ItemStack.CODEC.fieldOf("item").forGetter(BaitPreserve::itemStack),
                        Codec.DOUBLE.fieldOf("x").forGetter(BaitPreserve::x),
                        Codec.DOUBLE.fieldOf("y").forGetter(BaitPreserve::y),
                        Codec.DOUBLE.fieldOf("z").forGetter(BaitPreserve::z)
                )
                .apply(instance, BaitPreserve::new));

        public static BaitPreserve from(Map.Entry<Vec3, ItemStack> entry)
        {
            var pos = entry.getKey();
            return new BaitPreserve(entry.getValue(), pos.x(), pos.y(), pos.z());
        }

        public Vec3 toVec()
        {
            return new Vec3(this.x, this.y, this.z);
        }
    }
}