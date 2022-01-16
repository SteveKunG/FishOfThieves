package com.stevekung.fishofthieves;

import net.minecraft.core.Registry;
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

    public static void init()
    {
        register(SPLASHTAIL_DEATH);
        register(SPLASHTAIL_FLOP);
        register(SPLASHTAIL_HURT);
        register(PONDIE_DEATH);
        register(PONDIE_FLOP);
        register(PONDIE_HURT);
    }

    private static SoundEvent create(String key)
    {
        return new SoundEvent(new ResourceLocation(FishOfThieves.MOD_ID, key));
    }

    private static SoundEvent register(SoundEvent soundEvent)
    {
        return Registry.register(Registry.SOUND_EVENT, soundEvent.getLocation().getPath(), soundEvent);
    }
}