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
    public boolean isSalmon()
    {
        return this.salmon;
    }

    @Override
    public void setSalmon(boolean isSalmon)
    {
        this.salmon = isSalmon;
    }

    @Override
    public boolean isDancing()
    {
        return this.dancing;
    }

    @Override
    public void setDancing(boolean dancing)
    {
        this.dancing = dancing;
    }
}