package com.stevekung.fishofthieves.client;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.client.core.registries.BuiltInClientRegistries;
import net.minecraft.client.core.registries.ClientRegistries;
import net.minecraft.client.gui.components.debug.DebugEntryNoop;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FOTDebugScreenEntries
{
    public static final ResourceKey<DebugScreenEntry> STRUCTURE_CENTER_POS = key("structure_center_pos");

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Debug Screen Entries");
        Registry.register(BuiltInClientRegistries.DEBUG_SCREEN_ENTRY, STRUCTURE_CENTER_POS, new DebugEntryNoop());
    }

    private static ResourceKey<DebugScreenEntry> key(String name)
    {
        return ResourceKey.create(ClientRegistries.DEBUG_SCREEN_ENTRY, FishOfThieves.id(name));
    }
}