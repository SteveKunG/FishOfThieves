package com.stevekung.fishofthieves.fabric.datagen;

import java.util.Optional;

import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

public class FOTModelTemplates
{
    public static final TextureSlot PLANKS = TextureSlot.create("planks");
    public static final TextureSlot FRUIT = TextureSlot.create("fruit");
    public static final ModelTemplate WOODEN_FISH_PLAQUE = create("template_wooden_fish_plaque", PLANKS);
    public static final ModelTemplate IRON_FRAME_FISH_PLAQUE = create("template_iron_frame_fish_plaque", PLANKS);
    public static final ModelTemplate GOLDEN_FRAME_FISH_PLAQUE = create("template_golden_frame_fish_plaque", PLANKS);
    public static final ModelTemplate GILDED_FISH_PLAQUE = create("template_gilded_fish_plaque", PLANKS);
    public static final ModelTemplate SMALL_LOG = create("template_small_log", TextureSlot.TOP, TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate MEDIUM_LOG = create("template_medium_log", TextureSlot.END, TextureSlot.SIDE);
    public static final ModelTemplate BANANA_CLUSTER = create("template_banana_cluster", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModelTemplate BANANA_CLUSTER_PLANT = create("template_banana_cluster_plant", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModelTemplate BANANA_CLUSTER_PLANT_SMALL_CLUSTER = create("template_banana_cluster_plant_small_cluster", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModelTemplate BANANA_CLUSTER_PLANT_STEM = create("template_banana_cluster_plant_stem", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModelTemplate MANGO_FRUIT = create("template_mango_fruit", FRUIT);
    public static final ModelTemplate HANGING_MANGO_FRUIT = create("template_hanging_mango_fruit", FRUIT);

    private static ModelTemplate create(String blockModelLocation, TextureSlot... requiredSlots)
    {
        return new ModelTemplate(Optional.of(FishOfThieves.id("block/" + blockModelLocation)), Optional.empty(), requiredSlots);
    }
}