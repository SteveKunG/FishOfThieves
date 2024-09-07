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
    public static final ModelTemplate MEDIUM_COCONUT_LOG = create("medium_coconut_log", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate MEDIUM_COCONUT_LOG_HORIZONTAL = create("medium_coconut_log_horizontal", "_horizontal", TextureSlot.END, TextureSlot.SIDE);

    private static ModelTemplate create(String blockModelLocation, TextureSlot... requiredSlots)
    {
        return new ModelTemplate(Optional.of(FishOfThieves.id("block/" + blockModelLocation)), Optional.empty(), requiredSlots);
    }

    private static ModelTemplate create(String blockModelLocation, String suffix, TextureSlot... requiredSlots)
    {
        return new ModelTemplate(Optional.of(FishOfThieves.id("block/" + blockModelLocation)), Optional.of(suffix), requiredSlots);
    }
}