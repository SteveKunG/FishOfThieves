package com.stevekung.fishofthieves.mixin.animal;

import java.util.function.BiConsumer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.stevekung.fishofthieves.entity.PartyFish;
import com.stevekung.fishofthieves.entity.listener.FishJukeboxListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;

@Mixin(AbstractFish.class)
public abstract class MixinAbstractFish extends WaterAnimal implements PartyFish
{
    @Unique
    private static final EntityDataAccessor<Boolean> DATA_DANCING = SynchedEntityData.defineId(AbstractFish.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    @Unique
    private BlockPos jukeboxPos;

    @Unique
    private DynamicGameEventListener<FishJukeboxListener> dynamicJukeboxListener;

    MixinAbstractFish()
    {
        super(null, null);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fishofthieves$init(EntityType<? extends AbstractFish> entityType, Level level, CallbackInfo info)
    {
        var positionSource = new EntityPositionSource(this, this.getEyeHeight());
        this.dynamicJukeboxListener = new DynamicGameEventListener<>(new FishJukeboxListener(this, positionSource, GameEvent.JUKEBOX_PLAY.getNotificationRadius()));
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void fishofthieves$checkNearbyJukebox(CallbackInfo info)
    {
        if (this.isDancing() && this.shouldStopDancing() && this.tickCount % 20 == 0)
        {
            this.setDancing(false);
            this.jukeboxPos = null;
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void fishofthieves$addSyncedData(CallbackInfo info)
    {
        this.entityData.define(DATA_DANCING, false);
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> consumer)
    {
        if (this.level() instanceof ServerLevel serverLevel)
        {
            consumer.accept(this.dynamicJukeboxListener, serverLevel);
        }
    }

    @Override
    public void setJukeboxPlaying(BlockPos jukeboxPos, boolean jukeboxPlaying)
    {
        if (jukeboxPlaying)
        {
            if (!this.isDancing())
            {
                this.jukeboxPos = jukeboxPos;
                this.setDancing(true);
            }
        }
        else if (jukeboxPos.equals(this.jukeboxPos) || this.jukeboxPos == null)
        {
            this.jukeboxPos = null;
            this.setDancing(false);
        }
    }

    @Override
    public boolean isDancing()
    {
        return this.entityData.get(DATA_DANCING);
    }

    private void setDancing(boolean dancing)
    {
        if (this.level().isClientSide)
        {
            return;
        }
        this.entityData.set(DATA_DANCING, dancing);
    }

    private boolean shouldStopDancing()
    {
        return this.jukeboxPos == null || !this.jukeboxPos.closerToCenterThan(this.position(), GameEvent.JUKEBOX_PLAY.getNotificationRadius()) || !this.level().getBlockState(this.jukeboxPos).is(Blocks.JUKEBOX);
    }
}