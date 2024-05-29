package com.stevekung.fishofthieves.mixin.entity.monster;

import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.Battlegill;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

    static class BattlegillAttackSelector implements Predicate<LivingEntity>
    {
        private final Guardian guardian;

        public BattlegillAttackSelector(Guardian guardian)
        {
            this.guardian = guardian;
        }

        @Override
        public boolean test(@Nullable LivingEntity entity)
        {
            return entity instanceof Battlegill && entity.distanceToSqr(this.guardian) > 9.0;
        }
    }
}