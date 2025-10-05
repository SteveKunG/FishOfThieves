package com.stevekung.fishofthieves.neoforge;

import net.minecraft.world.level.ItemLike;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;

public interface CompostableList
{
    Object2FloatMap<ItemLike> COMPOSTABLES = new Object2FloatOpenHashMap<>();
}