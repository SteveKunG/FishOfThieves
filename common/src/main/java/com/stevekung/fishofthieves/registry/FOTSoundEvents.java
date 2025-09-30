package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class FOTSoundEvents
{
    public static final SoundEvent SPLASHTAIL_DEATH = register("entity.splashtail.death");
    public static final SoundEvent SPLASHTAIL_FLOP = register("entity.splashtail.flop");
    public static final SoundEvent SPLASHTAIL_HURT = register("entity.splashtail.hurt");
    public static final SoundEvent PONDIE_DEATH = register("entity.pondie.death");
    public static final SoundEvent PONDIE_FLOP = register("entity.pondie.flop");
    public static final SoundEvent PONDIE_HURT = register("entity.pondie.hurt");
    public static final SoundEvent ISLEHOPPER_DEATH = register("entity.islehopper.death");
    public static final SoundEvent ISLEHOPPER_FLOP = register("entity.islehopper.flop");
    public static final SoundEvent ISLEHOPPER_HURT = register("entity.islehopper.hurt");
    public static final SoundEvent ANCIENTSCALE_DEATH = register("entity.ancientscale.death");
    public static final SoundEvent ANCIENTSCALE_FLOP = register("entity.ancientscale.flop");
    public static final SoundEvent ANCIENTSCALE_HURT = register("entity.ancientscale.hurt");
    public static final SoundEvent PLENTIFIN_DEATH = register("entity.plentifin.death");
    public static final SoundEvent PLENTIFIN_FLOP = register("entity.plentifin.flop");
    public static final SoundEvent PLENTIFIN_HURT = register("entity.plentifin.hurt");
    public static final SoundEvent WILDSPLASH_DEATH = register("entity.wildsplash.death");
    public static final SoundEvent WILDSPLASH_FLOP = register("entity.wildsplash.flop");
    public static final SoundEvent WILDSPLASH_HURT = register("entity.wildsplash.hurt");
    public static final SoundEvent DEVILFISH_DEATH = register("entity.devilfish.death");
    public static final SoundEvent DEVILFISH_FLOP = register("entity.devilfish.flop");
    public static final SoundEvent DEVILFISH_HURT = register("entity.devilfish.hurt");
    public static final SoundEvent BATTLEGILL_DEATH = register("entity.battlegill.death");
    public static final SoundEvent BATTLEGILL_FLOP = register("entity.battlegill.flop");
    public static final SoundEvent BATTLEGILL_HURT = register("entity.battlegill.hurt");
    public static final SoundEvent WRECKER_DEATH = register("entity.wrecker.death");
    public static final SoundEvent WRECKER_FLOP = register("entity.wrecker.flop");
    public static final SoundEvent WRECKER_HURT = register("entity.wrecker.hurt");
    public static final SoundEvent STORMFISH_DEATH = register("entity.stormfish.death");
    public static final SoundEvent STORMFISH_FLOP = register("entity.stormfish.flop");
    public static final SoundEvent STORMFISH_HURT = register("entity.stormfish.hurt");
    public static final SoundEvent FISH_JUMP = register("entity.fish.jump");

    public static final SoundEvent FISH_PLAQUE_ROTATE = register("block.fish_plaque.rotate");
    public static final SoundEvent FISH_PLAQUE_WAX_OFF = register("block.fish_plaque.wax_off");
    public static final SoundEvent PINEAPPLE_SHEAR = register("block.pineapple.shear");
    public static final SoundEvent POMEGRANATE_PLANT_PICK = register("block.pomegranate_plant.pick_pomegranates");
    public static final SoundEvent GUARDIAN_FRUIT_GROW = register("block.guardian_fruit.grow");

    public static final SoundEvent CRUSH_POMEGRANATE = register("item.pomegranate.crush");

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Sound Event");
    }

    private static SoundEvent register(String key)
    {
        var soundEvent = SoundEvent.createVariableRangeEvent(FishOfThieves.id(key));
        return Registry.register(BuiltInRegistries.SOUND_EVENT, soundEvent.location().getPath(), soundEvent);
    }
}