package com.stevekung.fishofthieves.registry;

import net.minecraft.world.food.FoodProperties;

public class FOTFoodProperties
{
    public static final FoodProperties WORMS = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final FoodProperties SPLASHTAIL = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties PONDIE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static final FoodProperties ISLEHOPPER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
    public static final FoodProperties ANCIENTSCALE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
    public static final FoodProperties PLENTIFIN = new FoodProperties.Builder().nutrition(2).saturationModifier(0.15f).build();
    public static final FoodProperties WILDSPLASH = new FoodProperties.Builder().nutrition(2).saturationModifier(0.15f).build();
    public static final FoodProperties DEVILFISH = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties BATTLEGILL = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
    public static final FoodProperties WRECKER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.25f).build();
    public static final FoodProperties STORMFISH = new FoodProperties.Builder().nutrition(2).saturationModifier(0.25f).build();

    public static final FoodProperties COOKED_SPLASHTAIL = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_PONDIE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5f).build();
    public static final FoodProperties COOKED_ISLEHOPPER = new FoodProperties.Builder().nutrition(5).saturationModifier(0.55f).build();
    public static final FoodProperties COOKED_ANCIENTSCALE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5f).build();
    public static final FoodProperties COOKED_PLENTIFIN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.55f).build();
    public static final FoodProperties COOKED_WILDSPLASH = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_DEVILFISH = new FoodProperties.Builder().nutrition(5).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_BATTLEGILL = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6f).build();
    public static final FoodProperties COOKED_WRECKER = new FoodProperties.Builder().nutrition(6).saturationModifier(0.65f).build();
    public static final FoodProperties COOKED_STORMFISH = new FoodProperties.Builder().nutrition(6).saturationModifier(0.65f).build();

    public static final FoodProperties COCONUT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.125f).build();
    public static final FoodProperties BANANA = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static final FoodProperties PINEAPPLE = new FoodProperties.Builder().nutrition(6).saturationModifier(1.0f).build();
    public static final FoodProperties HALF_PINEAPPLE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f).build();
    public static final FoodProperties MANGO = new FoodProperties.Builder().nutrition(5).saturationModifier(1.0f).build();
    public static final FoodProperties RAW_MANGO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.5f).build();
    public static final FoodProperties POMEGRANATE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).build();
    public static final FoodProperties GUARDIAN_FRUIT = new FoodProperties.Builder().nutrition(8).saturationModifier(1.0f)
            .alwaysEdible()
            .build();
}