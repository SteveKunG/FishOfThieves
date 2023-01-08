package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class FOTSoundEvents
{
    public static final SoundEvent SPLASHTAIL_DEATH = create("entity.splashtail.death");
    public static final SoundEvent SPLASHTAIL_FLOP = create("entity.splashtail.flop");
    public static final SoundEvent SPLASHTAIL_HURT = create("entity.splashtail.hurt");
    public static final SoundEvent PONDIE_DEATH = create("entity.pondie.death");
    public static final SoundEvent PONDIE_FLOP = create("entity.pondie.flop");
    public static final SoundEvent PONDIE_HURT = create("entity.pondie.hurt");
    public static final SoundEvent ISLEHOPPER_DEATH = create("entity.islehopper.death");
    public static final SoundEvent ISLEHOPPER_FLOP = create("entity.islehopper.flop");
    public static final SoundEvent ISLEHOPPER_HURT = create("entity.islehopper.hurt");
    public static final SoundEvent ANCIENTSCALE_DEATH = create("entity.ancientscale.death");
    public static final SoundEvent ANCIENTSCALE_FLOP = create("entity.ancientscale.flop");
    public static final SoundEvent ANCIENTSCALE_HURT = create("entity.ancientscale.hurt");
    public static final SoundEvent PLENTIFIN_DEATH = create("entity.plentifin.death");
    public static final SoundEvent PLENTIFIN_FLOP = create("entity.plentifin.flop");
    public static final SoundEvent PLENTIFIN_HURT = create("entity.plentifin.hurt");
    public static final SoundEvent WILDSPLASH_DEATH = create("entity.wildsplash.death");
    public static final SoundEvent WILDSPLASH_FLOP = create("entity.wildsplash.flop");
    public static final SoundEvent WILDSPLASH_HURT = create("entity.wildsplash.hurt");
    public static final SoundEvent DEVILFISH_DEATH = create("entity.devilfish.death");
    public static final SoundEvent DEVILFISH_FLOP = create("entity.devilfish.flop");
    public static final SoundEvent DEVILFISH_HURT = create("entity.devilfish.hurt");
    public static final SoundEvent BATTLEGILL_DEATH = create("entity.battlegill.death");
    public static final SoundEvent BATTLEGILL_FLOP = create("entity.battlegill.flop");
    public static final SoundEvent BATTLEGILL_HURT = create("entity.battlegill.hurt");
    public static final SoundEvent WRECKER_DEATH = create("entity.wrecker.death");
    public static final SoundEvent WRECKER_FLOP = create("entity.wrecker.flop");
    public static final SoundEvent WRECKER_HURT = create("entity.wrecker.hurt");
    public static final SoundEvent STORMFISH_DEATH = create("entity.stormfish.death");
    public static final SoundEvent STORMFISH_FLOP = create("entity.stormfish.flop");
    public static final SoundEvent STORMFISH_HURT = create("entity.stormfish.hurt");

    public static final SoundEvent FISH_PLAQUE_ROTATE = create("block.fish_plaque.rotate");

    public static void init()
    {
        register(SPLASHTAIL_DEATH);
        register(SPLASHTAIL_FLOP);
        register(SPLASHTAIL_HURT);
        register(PONDIE_DEATH);
        register(PONDIE_FLOP);
        register(PONDIE_HURT);
        register(ISLEHOPPER_DEATH);
        register(ISLEHOPPER_FLOP);
        register(ISLEHOPPER_HURT);
        register(ANCIENTSCALE_DEATH);
        register(ANCIENTSCALE_FLOP);
        register(ANCIENTSCALE_HURT);
        register(PLENTIFIN_DEATH);
        register(PLENTIFIN_FLOP);
        register(PLENTIFIN_HURT);
        register(WILDSPLASH_DEATH);
        register(WILDSPLASH_FLOP);
        register(WILDSPLASH_HURT);
        register(DEVILFISH_DEATH);
        register(DEVILFISH_FLOP);
        register(DEVILFISH_HURT);
        register(BATTLEGILL_DEATH);
        register(BATTLEGILL_FLOP);
        register(BATTLEGILL_HURT);
        register(WRECKER_DEATH);
        register(WRECKER_FLOP);
        register(WRECKER_HURT);
        register(STORMFISH_DEATH);
        register(STORMFISH_FLOP);
        register(STORMFISH_HURT);
        register(FISH_PLAQUE_ROTATE);
    }

    private static SoundEvent create(String key)
    {
        return new SoundEvent(new ResourceLocation(FishOfThieves.MOD_ID, key));
    }

    private static void register(SoundEvent soundEvent)
    {
        FOTPlatform.registerSoundEvent(soundEvent);
    }
}