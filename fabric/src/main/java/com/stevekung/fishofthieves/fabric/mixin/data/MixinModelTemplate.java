package com.stevekung.fishofthieves.fabric.mixin.data;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stevekung.fishofthieves.fabric.datagen.ExtendedModelTemplate;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

@Mixin(ModelTemplate.class)
public abstract class MixinModelTemplate implements ExtendedModelTemplate
{
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Shadow
    @Final
    Optional<ResourceLocation> model;

    @Shadow
    abstract Map<TextureSlot, ResourceLocation> createMap(TextureMapping textureMapping);

    @Override
    public void create(ResourceLocation modelLocation, TextureMapping textureMapping, List<String> overrides, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput)
    {
        var map = this.createMap(textureMapping);

        modelOutput.accept(modelLocation, () ->
        {
            var jsonObject = new JsonObject();
            this.model.ifPresent(resourceLocation -> jsonObject.addProperty("parent", resourceLocation.toString()));

            if (!map.isEmpty())
            {
                var jsonObject2 = new JsonObject();
                map.forEach((textureSlot, resourceLocation) -> jsonObject2.addProperty(textureSlot.getId(), resourceLocation.toString()));
                jsonObject.add("textures", jsonObject2);
            }

            var overrideArray = new JsonArray();

            if (!overrides.isEmpty())
            {
                var index = 1;

                for (var override : overrides)
                {
                    var customModelDataPredicate = new JsonObject();
                    var customModelData = new JsonObject();
                    var customModel = getCustomModelLocation(modelLocation.getNamespace(), override);
                    customModelData.addProperty("custom_model_data", index++);
                    customModelDataPredicate.add("predicate", customModelData);
                    customModelDataPredicate.addProperty("model", customModel.toString());
                    overrideArray.add(customModelDataPredicate);
                }
            }
            jsonObject.add("overrides", overrideArray);
            return jsonObject;
        });

        for (var override : overrides)
        {
            var customModel = getCustomModelLocation(modelLocation.getNamespace(), override);
            ModelTemplate.class.cast(this).create(customModel, TextureMapping.layer0(customModel), modelOutput);
        }
    }

    private static ResourceLocation getCustomModelLocation(String namespace, String item)
    {
        return new ResourceLocation(namespace, "item/" + item);
    }
}