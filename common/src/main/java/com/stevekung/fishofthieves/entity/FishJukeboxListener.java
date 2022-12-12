package com.stevekung.fishofthieves.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class FishJukeboxListener implements GameEventListener
{
    private final PartyFish fish;
    private final PositionSource listenerSource;
    private final int listenerRadius;

    public FishJukeboxListener(PartyFish fish, PositionSource positionSource, int listenerRadius)
    {
        this.fish = fish;
        this.listenerSource = positionSource;
        this.listenerRadius = listenerRadius;
    }

    @Override
    public PositionSource getListenerSource()
    {
        return this.listenerSource;
    }

    @Override
    public int getListenerRadius()
    {
        return this.listenerRadius;
    }

    @Override
    public boolean handleGameEvent(ServerLevel serverLevel, GameEvent gameEvent, GameEvent.Context context, Vec3 vec3)
    {
        if (gameEvent == GameEvent.JUKEBOX_PLAY)
        {
            this.fish.setJukeboxPlaying(new BlockPos(vec3), true);
            return true;
        }
        if (gameEvent == GameEvent.JUKEBOX_STOP_PLAY)
        {
            this.fish.setJukeboxPlaying(new BlockPos(vec3), false);
            return true;
        }
        return false;
    }
}