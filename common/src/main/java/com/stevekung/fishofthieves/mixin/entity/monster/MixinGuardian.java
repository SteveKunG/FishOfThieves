package com.stevekung.fishofthieves.mixin.entity.monster;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import com.stevekung.fishofthieves.registry.FOTMobEffects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

@Mixin(Guardian.class)
public class MixinGuardian extends Monster
{
    @Shadow
    RandomStrollGoal randomStrollGoal;

    MixinGuardian()
    {
        super(null, null);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void fishofthieves$addBattlegillSelector(CallbackInfo info)
    {
        if (FishOfThieves.CONFIG.general.neutralFishBehavior)
        {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, false, new BattlegillAttackSelector(Guardian.class.cast(this))));
        }
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/monster/Guardian.hasActiveAttackTarget()Z", ordinal = 1))
    private void fishofthieves$randomAvoidPlayerWithGuardianStifle(CallbackInfo info)
    {
        if (this.random.nextInt(250) == 0 && this.randomStrollGoal != null)
        {
            var player = this.level().getNearestPlayer(this, 10);

            if (player != null && player.hasEffect(FOTMobEffects.GUARDIAN_STIFLE))
            {
                this.randomStrollGoal.trigger();
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.entity.monster.Guardian$GuardianAttackSelector")
    public static class MixinGuardian_GuardianAttackSelector
    {
        @Inject(method = "test", cancellable = true, at = @At("HEAD"))
        private void fishofthieves$preventGuardianAttackPlayer(@Nullable LivingEntity entity, ServerLevel serverLevel, CallbackInfoReturnable<Boolean> info)
        {
            if (entity instanceof Player player && player.hasEffect(FOTMobEffects.GUARDIAN_STIFLE))
            {
                info.setReturnValue(false);
            }
        }
    }

    static class BattlegillAttackSelector implements TargetingConditions.Selector
    {
        private final Guardian guardian;

        public BattlegillAttackSelector(Guardian guardian)
        {
            this.guardian = guardian;
        }

        @Override
        public boolean test(LivingEntity livingEntity, ServerLevel serverLevel)
        {
            return livingEntity instanceof Battlegill && livingEntity.distanceToSqr(this.guardian) > 9.0;
        }
    }
}