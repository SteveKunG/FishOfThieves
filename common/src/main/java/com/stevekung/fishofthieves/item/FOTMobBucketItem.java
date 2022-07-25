package com.stevekung.fishofthieves.item;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.entity.ThievesFish;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;

public class FOTMobBucketItem extends MobBucketItem
{
    private final Consumer<Int2ObjectOpenHashMap<String>> dataFixMap;

    public FOTMobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent soundEvent, Consumer<Int2ObjectOpenHashMap<String>> dataFixMap, Item.Properties properties)
    {
        super(entityType, fluid, soundEvent, properties);
        this.dataFixMap = dataFixMap;
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag compoundTag)
    {
        super.verifyTagAfterLoad(compoundTag);
        ThievesFish.fixData(compoundTag, this.dataFixMap);
    }
}