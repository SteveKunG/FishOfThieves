package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.client.gui.components.debug.DebugEntryNoop;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.resources.ResourceLocation;

public class FOTDebugScreenEntries
{
    public static final ResourceLocation STRUCTURE_CENTER_POS = DebugScreenEntries.register(FishOfThieves.id("structure_center_pos"), new DebugEntryNoop());

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Debug Screen Entries");
    }
}