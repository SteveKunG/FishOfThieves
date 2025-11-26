package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.entity.animal.*;
import com.stevekung.fishofthieves.entity.shoal.Shoal;

import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.MobSpawnSettings;

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

    public static final EntityType<Shoal> SHOAL = FOTPlatform.createEntityType(Shoal::new, MobCategory.MISC, EntityDimensions.fixed(2.0F, 1.0F), 10);

    public interface SpawnData
    {
        WeightedRandomList<MobSpawnSettings.SpawnerData> ANCIENTSCALE = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 12, 4, 8));
        WeightedRandomList<MobSpawnSettings.SpawnerData> PLENTIFIN = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 12, 4, 8));
        WeightedRandomList<MobSpawnSettings.SpawnerData> BATTLEGILL = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 5, 2, 4));
        WeightedRandomList<MobSpawnSettings.SpawnerData> WRECKER = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 50, 4, 8));
    }

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

        register("shoal", SHOAL);
    }

    private static <T extends Entity> void register(String key, EntityType<T> type)
    {
        FOTPlatform.registerEntityType(key, type);
    }
}