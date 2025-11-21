package com.stevekung.fishofthieves.mixin.animal;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.registry.FOTMobEffects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.player.Player;

@Mixin(AbstractFish.class)
public abstract class MixinAbstractFish extends WaterAnimal
{
    @Unique
    private static final Predicate<Entity> PLAYER_GUARDIAN_STIFLE = entity -> entity instanceof Player player && !player.hasEffect(FOTMobEffects.GUARDIAN_STIFLE);

    MixinAbstractFish()
    {
        super(null, null);
    }

    @WrapOperation(method = "registerGoals", at = @At(value = "FIELD", target = "net/minecraft/world/entity/EntitySelector.NO_SPECTATORS:Ljava/util/function/Predicate;"))
    private Predicate<Entity> fishofthieves$preventFishAvoidPlayerWithGuardianStifle(Operation<Predicate<Entity>> operation)
    {
        return operation.call().and(PLAYER_GUARDIAN_STIFLE);
    }
}