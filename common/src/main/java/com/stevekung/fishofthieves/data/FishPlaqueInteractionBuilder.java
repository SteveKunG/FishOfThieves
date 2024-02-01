package com.stevekung.fishofthieves.data;

import java.util.function.Consumer;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FishPlaqueInteractionBuilder
{
    private final ResourceLocation entityToInteract;
    private final ResourceLocation item;

    public FishPlaqueInteractionBuilder(ResourceLocation entityToInteract, ResourceLocation item)
    {
        this.entityToInteract = entityToInteract;
        this.item = item;
    }

    public static FishPlaqueInteractionBuilder interaction(ResourceLocation entityToInteract, Item item)
    {
        return new FishPlaqueInteractionBuilder(entityToInteract, BuiltInRegistries.ITEM.getKey(item));
    }

    public static FishPlaqueInteractionBuilder interaction(ResourceLocation entityToInteract, ResourceLocation item)
    {
        return new FishPlaqueInteractionBuilder(entityToInteract, item);
    }

    public static FishPlaqueInteractionBuilder interaction(EntityType<?> entityToInteract, Item item)
    {
        return new FishPlaqueInteractionBuilder(BuiltInRegistries.ENTITY_TYPE.getKey(entityToInteract), BuiltInRegistries.ITEM.getKey(item));
    }

    public void save(Consumer<FinishedInteraction> output)
    {
        output.accept(new InteractionResult(this.entityToInteract, this.item));
    }

    private record InteractionResult(ResourceLocation id, ResourceLocation item) implements FinishedInteraction
    {
        @Override
        public void serializeInteractionData(JsonObject json)
        {
            json.addProperty("item", this.item.toString());
        }
    }
}