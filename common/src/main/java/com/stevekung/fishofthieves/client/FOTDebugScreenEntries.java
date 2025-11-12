package com.stevekung.fishofthieves.client;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.client.gui.components.debug.DebugEntryNoop;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.resources.Identifier;

public class FOTDebugScreenEntries
{
    public static final Identifier STRUCTURE_CENTER_POS = DebugScreenEntries.register(FishOfThieves.id("structure_center_pos"), new DebugEntryNoop());

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Debug Screen Entries");
    }
}