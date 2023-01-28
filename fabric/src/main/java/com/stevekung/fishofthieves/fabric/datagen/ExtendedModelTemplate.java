package com.stevekung.fishofthieves.fabric.datagen;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.google.gson.JsonElement;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

//TODO Remove if 1.19.4 is release
@Deprecated
public interface ExtendedModelTemplate
{
    void create(ResourceLocation modelLocation, TextureMapping textureMapping, List<String> overrides, String suffixes, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput);

    static void generateFlatItemWithCustomModelData(Item item, List<String> overrides, String suffixes, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput)
    {
        ((ExtendedModelTemplate) ModelTemplates.FLAT_ITEM).create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), overrides, suffixes, modelOutput);
    }
}