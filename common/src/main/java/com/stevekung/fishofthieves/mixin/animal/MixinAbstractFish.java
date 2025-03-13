package com.stevekung.fishofthieves.mixin.animal;

import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.stevekung.fishofthieves.entity.PartyFish;
import com.stevekung.fishofthieves.registry.FOTMobEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
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
        if (this.jukeboxPos == null || !this.jukeboxPos.closerToCenterThan(this.position(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()) || !this.level().getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX))
        {
            this.dancing = false;
            this.jukeboxPos = null;
        }
    }

    @WrapOperation(method = "registerGoals", at = @At(value = "FIELD", target = "net/minecraft/world/entity/EntitySelector.NO_SPECTATORS:Ljava/util/function/Predicate;"))
    private Predicate<Entity> fishofthieves$preventFishAvoidPlayerWithGuardianStifle(Operation<Predicate<Entity>> operation)
    {
        Predicate<Entity> playerGuardianStifle = entity -> entity instanceof Player player && !player.hasEffect(FOTMobEffects.GUARDIAN_STIFLE);
        return operation.call().and(playerGuardianStifle);
    }

    @Override
    public void setRecordPlayingNearby(BlockPos jukeboxPos, boolean jukeboxPlaying)
    {
        this.dancing = jukeboxPlaying;
        this.jukeboxPos = jukeboxPos;
    }

    @Override
    public boolean fishofthieves$isDancing()
    {
        return this.dancing;
    }
}