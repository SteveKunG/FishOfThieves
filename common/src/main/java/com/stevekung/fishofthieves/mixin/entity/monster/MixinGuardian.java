package com.stevekung.fishofthieves.mixin.entity.monster;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;

@Mixin(Guardian.class)
public class MixinGuardian extends Monster
{
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