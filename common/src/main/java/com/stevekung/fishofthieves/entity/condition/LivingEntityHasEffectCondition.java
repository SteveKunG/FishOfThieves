package com.stevekung.fishofthieves.entity.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;

public record LivingEntityHasEffectCondition(HolderSet<MobEffect> mobEffect) implements SpawnCondition
{
    public static final MapCodec<LivingEntityHasEffectCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("mob_effect").forGetter(LivingEntityHasEffectCondition::mobEffect)).apply(instance, LivingEntityHasEffectCondition::new));

    @Override
    public MapCodec<? extends SpawnCondition> codec()
    {
        return CODEC;
    }

    @Override
    public boolean test(SpawnContext context)
    {
        if (context.fishofthieves$entity() instanceof LivingEntity livingEntity)
        {
            for (var holder : livingEntity.getActiveEffectsMap().keySet())
            {
                return this.mobEffect.contains(holder);
            }
        }
        return false;
    }

    public static SpawnCondition effect(HolderSet<MobEffect> mobEffect)
    {
        return new LivingEntityHasEffectCondition(mobEffect);
    }
}