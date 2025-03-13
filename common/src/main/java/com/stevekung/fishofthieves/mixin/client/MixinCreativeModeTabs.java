package com.stevekung.fishofthieves.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

@Mixin(CreativeModeTabs.class)
public interface MixinCreativeModeTabs
{
    @Accessor
    static CreativeModeTab.ItemDisplayParameters getCACHED_PARAMETERS()
    {
        throw new AssertionError("Implemented via mixin");
    }

    @Accessor
    static void setCACHED_PARAMETERS(CreativeModeTab.ItemDisplayParameters parameters)
    {
        throw new AssertionError("Implemented via mixin");
    }

    @Invoker
    static void invokeBuildAllTabContents(CreativeModeTab.ItemDisplayParameters parameters)
    {
        throw new AssertionError("Implemented via mixin");
    }
}