package com.stevekung.fishofthieves.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class FOTConsumables
{
    public static final Consumable WORMS = Consumables.defaultFood().consumeSeconds(0.8F).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.CONFUSION, 400), 0.5F)).build();
    public static final Consumable DEVILFISH = Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200), 0.05F)).build();
    public static final Consumable COOKED_PLENTIFIN = Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.LUCK, 200), 0.1F)).build();
    public static final Consumable COOKED_BATTLEGILL = Consumables.defaultFood().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200), 0.1F)).build();
}