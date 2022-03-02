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

@Mixin(AbstractFish.class)
public abstract class MixinAbstractFish extends WaterAnimal implements PartyFish
{
    @Unique
    private boolean partyFish;
    @Nullable
    @Unique
    private BlockPos jukebox;

    MixinAbstractFish()
    {
        super(null, null);
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void fishofthieves$checkNearbyJukebox(CallbackInfo info)
    {
        if (this.jukebox == null || !this.jukebox.closerToCenterThan(this.position(), 8.0D) || !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX))
        {
            this.partyFish = false;
            this.jukebox = null;
        }
    }

    @Override
    public void setRecordPlayingNearby(BlockPos blockPos, boolean isPartying)
    {
        this.jukebox = blockPos;
        this.partyFish = isPartying;
    }

    @Override
    public boolean isPartying()
    {
        return this.partyFish;
    }
}