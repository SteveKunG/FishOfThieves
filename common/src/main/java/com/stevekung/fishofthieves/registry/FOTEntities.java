package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.utils.FOTPlatform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;

public class FOTEntities
{
    public static final EntityType<Splashtail> SPLASHTAIL = FOTPlatform.createEntityType(Splashtail::new, EntityDimensions.fixed(0.7F, 0.4F));
    public static final EntityType<Pondie> PONDIE = FOTPlatform.createEntityType(Pondie::new, EntityDimensions.fixed(0.6F, 0.5F));
    public static final EntityType<Islehopper> ISLEHOPPER = FOTPlatform.createEntityType(Islehopper::new, EntityDimensions.fixed(0.55F, 0.45F));
    public static final EntityType<Ancientscale> ANCIENTSCALE = FOTPlatform.createEntityType(Ancientscale::new, EntityDimensions.fixed(0.6F, 0.5F));
    public static final EntityType<Plentifin> PLENTIFIN = FOTPlatform.createEntityType(Plentifin::new, EntityDimensions.fixed(0.5F, 0.45F));
    public static final EntityType<Wildsplash> WILDSPLASH = FOTPlatform.createEntityType(Wildsplash::new, EntityDimensions.fixed(0.6F, 0.5F));
    public static final EntityType<Devilfish> DEVILFISH = FOTPlatform.createEntityType(Devilfish::new, EntityDimensions.fixed(0.5F, 0.55F));
    public static final EntityType<Battlegill> BATTLEGILL = FOTPlatform.createEntityType(Battlegill::new, EntityDimensions.fixed(0.5F, 0.55F));
    public static final EntityType<Wrecker> WRECKER = FOTPlatform.createEntityType(Wrecker::new, EntityDimensions.fixed(0.5F, 0.5F));
    public static final EntityType<Stormfish> STORMFISH = FOTPlatform.createEntityType(Stormfish::new, EntityDimensions.fixed(0.75F, 0.55F));

    public static void init()
    {
        register("splashtail", SPLASHTAIL);
        register("pondie", PONDIE);
        register("islehopper", ISLEHOPPER);
        register("ancientscale", ANCIENTSCALE);
        register("plentifin", PLENTIFIN);
        register("wildsplash", WILDSPLASH);
        register("devilfish", DEVILFISH);
        register("battlegill", BATTLEGILL);
        register("wrecker", WRECKER);
        register("stormfish", STORMFISH);
    }

    private static <T extends Entity> void register(String key, EntityType<T> type)
    {
        FOTPlatform.registerEntityType(key, type);
    }
}