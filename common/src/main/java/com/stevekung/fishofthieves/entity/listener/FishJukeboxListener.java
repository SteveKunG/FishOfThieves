package com.stevekung.fishofthieves.entity.listener;

import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

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
    public boolean handleGameEvent(ServerLevel level, GameEvent.Message message)
    {
        if (message.gameEvent() == GameEvent.JUKEBOX_PLAY)
        {
            this.fish.setJukeboxPlaying(new BlockPos(message.source()), true);
            return true;
        }
        if (message.gameEvent() == GameEvent.JUKEBOX_STOP_PLAY)
        {
            this.fish.setJukeboxPlaying(new BlockPos(message.source()), false);
            return true;
        }
        return false;
    }
}