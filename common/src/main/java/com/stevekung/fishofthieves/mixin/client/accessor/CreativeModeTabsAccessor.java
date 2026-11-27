package com.stevekung.fishofthieves.mixin.client.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

@Mixin(CreativeModeTabs.class)
public interface CreativeModeTabsAccessor
{
    @Accessor("CACHED_PARAMETERS")
    static CreativeModeTab.ItemDisplayParameters getCachedParameters()
    {
        throw new AssertionError();
    }

    @Accessor("CACHED_PARAMETERS")
    static void setCachedParameters(CreativeModeTab.ItemDisplayParameters parameters)
    {
        throw new AssertionError();
    }

    @Invoker
    static void invokeBuildAllTabContents(CreativeModeTab.ItemDisplayParameters parameters)
    {
        throw new AssertionError();
    }
}