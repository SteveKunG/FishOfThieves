package com.stevekung.fishofthieves.mixin.animal;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.entity.PartyFish;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;

@Mixin(AbstractFish.class)
public abstract class MixinAbstractFish extends WaterAnimal implements PartyFish
{
    @Unique
    private boolean dancing;

    @Nullable
    @Unique
    private BlockPos jukeboxPos;

    MixinAbstractFish()
    {
        super(null, null);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void fishofthieves$checkNearbyJukebox(CallbackInfo info)
    {
        if (this.jukeboxPos == null || !this.jukeboxPos.closerToCenterThan(this.position(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()) || !this.level.getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX))
        {
            this.dancing = false;
            this.jukeboxPos = null;
        }
    }

    @Override
    public void setRecordPlayingNearby(BlockPos jukeboxPos, boolean jukeboxPlaying)
    {
        this.dancing = jukeboxPlaying;
        this.jukeboxPos = jukeboxPos;
    }

    @Override
    public boolean isDancing()
    {
        return this.dancing;
    }
}