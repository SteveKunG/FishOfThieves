package com.stevekung.fishofthieves.fabric.datagen;

import java.util.function.Consumer;

import com.stevekung.fishofthieves.data.FinishedFishPlaqueInteraction;
import com.stevekung.fishofthieves.data.FishPlaqueInteractionBuilder;
import com.stevekung.fishofthieves.data.FishPlaqueInteractionProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public class ModFishPlaqueInteractionProvider extends FishPlaqueInteractionProvider
{
    public ModFishPlaqueInteractionProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<FinishedFishPlaqueInteraction> output)
    {
        System.out.println(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SALMON));
        FishPlaqueInteractionBuilder.interaction(new ResourceLocation("naturalist", "snail"), Items.BUCKET).save(output);
        FishPlaqueInteractionBuilder.interaction(new ResourceLocation("alexsmobs", "cosmic_cod"), Items.BUCKET).save(output);
        FishPlaqueInteractionBuilder.interaction(new ResourceLocation("alexsmobs", "stradpole"), Items.LAVA_BUCKET).save(output);
        FishPlaqueInteractionBuilder.interaction(new ResourceLocation("alexscaves", "radgill"), new ResourceLocation("alexscaves", "acid_bucket")).save(output);
    }
}