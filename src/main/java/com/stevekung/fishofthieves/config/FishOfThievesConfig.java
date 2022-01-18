package com.stevekung.fishofthieves.config;

import com.stevekung.fishofthieves.FishOfThieves;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = FishOfThieves.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/prismarine_bricks.png")
public final class FishOfThievesConfig implements ConfigData
{
    @Comment("Toggles spore particles in Lush Cave biome.")
    public boolean sporeParticlesInLushCave = true;

    @ConfigEntry.Category("be_render_distance")
    @ConfigEntry.Gui.TransitiveObject
    public BlockEntityRenderDistanceCategory blockEntityRenderDistance;

    public FishOfThievesConfig()
    {
        this.blockEntityRenderDistance = new BlockEntityRenderDistanceCategory();
    }

    public static class BlockEntityRenderDistanceCategory
    {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int chest = 64;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int shulkerBox = 64;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int conduit = 64;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1024)
        public int beacon = 256;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int banner = 64;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int movingPiston = 68;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int skullBlock = 64;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int bell = 64;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 512)
        public int sign = 64;
    }
}