package com.stevekung.fishofthieves.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

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
    public static final FoodProperties POMEGRANATE = new FoodProperties.Builder().nutrition(5).saturationModifier(1.0f).build();

    //TODO New juicy effect
    public static final Consumable RAW_MANGO_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 0.2f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0), 0.15f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.JUMP, 200, 0), 0.1f))
            .build();
}