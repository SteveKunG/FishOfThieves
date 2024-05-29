package com.stevekung.fishofthieves.fabric.datagen;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

public class FOTModelTemplates
{
    public static final TextureSlot PLANKS = TextureSlot.create("planks");
    public static final ModelTemplate WOODEN_FISH_PLAQUE = create("template_wooden_fish_plaque", PLANKS);
    public static final ModelTemplate IRON_FRAME_FISH_PLAQUE = create("template_iron_frame_fish_plaque", PLANKS);
    public static final ModelTemplate GOLDEN_FRAME_FISH_PLAQUE = create("template_golden_frame_fish_plaque", PLANKS);
    public static final ModelTemplate GILDED_FISH_PLAQUE = create("template_gilded_fish_plaque", PLANKS);

    private static ModelTemplate create(String blockModelLocation, TextureSlot... requiredSlots)
    {
        return new ModelTemplate(Optional.of(FishOfThieves.id("block/" + blockModelLocation)), Optional.empty(), requiredSlots);
    }
}