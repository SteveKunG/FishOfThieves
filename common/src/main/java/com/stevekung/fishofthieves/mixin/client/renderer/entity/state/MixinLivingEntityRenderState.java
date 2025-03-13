package com.stevekung.fishofthieves.mixin.client.renderer.entity.state;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.stevekung.fishofthieves.client.renderer.entity.state.LivingEntityRenderStateExtender;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Mixin(LivingEntityRenderState.class)
public class MixinLivingEntityRenderState implements LivingEntityRenderStateExtender
{
    @Unique
    private boolean salmon;

    @Unique
    private boolean dancing;

    @Override
    public boolean fishofthieves$isSalmon()
    {
        return this.salmon;
    }

    @Override
    public void fishofthieves$setSalmon(boolean isSalmon)
    {
        this.salmon = isSalmon;
    }

    @Override
    public boolean fishofthieves$isDancing()
    {
        return this.dancing;
    }

    @Override
    public void fishofthieves$setDancing(boolean dancing)
    {
        this.dancing = dancing;
    }
}