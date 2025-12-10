package com.stevekung.fishofthieves.mixin.accessor;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

@Mixin(CompoundTag.class)
public interface CompoundTagAccessor
{
    @Accessor("tags")
    Map<String, Tag> getTags();
}