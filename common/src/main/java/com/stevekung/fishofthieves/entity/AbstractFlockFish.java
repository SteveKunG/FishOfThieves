package com.stevekung.fishofthieves.entity;

import java.util.stream.Stream;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;

public abstract class AbstractFlockFish extends AbstractSchoolingFish
{
    public AbstractFlockFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level)
    {
        super(entityType, level);
    }

    public abstract int getSchoolSize();

    public abstract void addThievesFishFollowers(Stream<AbstractFlockFish> followers);

    public abstract void startFollowingThievesFish(AbstractFlockFish leader);

    public abstract boolean isSameType(AbstractFlockFish other);

    @Override
    public abstract boolean isFollower();

    public abstract void addFollower();

    public abstract void removeFollower();

    @Override
    public abstract boolean canBeFollowed();

    public abstract boolean hasFollowCooldown();

    public abstract boolean isLeader();

    public abstract boolean hasLeader();

    public abstract AbstractFlockFish getLeader();

    public abstract boolean isTrophy();
}