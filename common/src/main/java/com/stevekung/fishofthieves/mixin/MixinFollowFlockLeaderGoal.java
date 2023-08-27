package com.stevekung.fishofthieves.mixin;

import java.util.List;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;

@Mixin(FollowFlockLeaderGoal.class)
public abstract class MixinFollowFlockLeaderGoal extends Goal
{
    @Shadow
    AbstractSchoolingFish mob;
    @Shadow
    int nextStartTick;

    @Shadow
    abstract int nextStartTick(AbstractSchoolingFish taskOwner);

    @Override
    public boolean canUse() {
        if (this.mob.hasFollowers()) {
            return false;
        } else if (this.mob.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            Predicate<AbstractSchoolingFish> predicate = abstractSchoolingFishx -> abstractSchoolingFishx.canBeFollowed() || !abstractSchoolingFishx.isFollower();
            List<? extends AbstractSchoolingFish> list = this.mob.level.getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), predicate);
            AbstractSchoolingFish abstractSchoolingFish = DataFixUtils.orElse(list.stream().filter(AbstractSchoolingFish::canBeFollowed).findAny(), this.mob);
            abstractSchoolingFish.addFollowers(list.stream().filter(abstractSchoolingFishx -> !abstractSchoolingFishx.isFollower() && !abstractSchoolingFishx.hasFollowers()));
//            abstractSchoolingFish.addFollowers(list.stream().filter(abstractSchoolingFishx -> !abstractSchoolingFishx.isFollower()));
            return this.mob.isFollower();
        }
    }
}