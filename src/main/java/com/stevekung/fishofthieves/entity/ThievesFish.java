package com.stevekung.fishofthieves.entity;

import com.stevekung.fishofthieves.FishOfThieves;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public interface ThievesFish
{
    String VARIANT_TAG = "Variant";
    String TROPHY_TAG = "Trophy";
    String NAME_TAG = "Name";
    Tag.Named<EntityType<?>> THIEVES_FISH = TagFactory.ENTITY_TYPE.create(new ResourceLocation(FishOfThieves.MOD_ID, "thieves_fish"));

    FishVariant getVariant();
    void setVariant(int id);
    boolean isTrophy();
    void setTrophy(boolean trophy);

    default void saveToBucket(ItemStack itemStack, int variant, String name)
    {
        var compound = itemStack.getOrCreateTag();
        compound.putInt(VARIANT_TAG, variant);
        compound.putBoolean(TROPHY_TAG, this.isTrophy());
        compound.putString(NAME_TAG, name);
    }

    default void loadFromBucket(int variant, CompoundTag compound)
    {
        this.setVariant(variant);
        this.setTrophy(compound.getBoolean(TROPHY_TAG));
    }

    interface FishVariant {}

    @FunctionalInterface
    interface Condition
    {
        boolean spawn(ServerLevel level, BlockPos pos);
    }
}