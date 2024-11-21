package com.stevekung.fishofthieves.registry;

import java.util.function.Function;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class FOTEntities
{
    public static final EntityType<Splashtail> SPLASHTAIL = register("splashtail", key -> FOTPlatform.createEntityType(key, Splashtail::new, EntityDimensions.fixed(0.7F, 0.4F)));
    public static final EntityType<Pondie> PONDIE = register("pondie", key -> FOTPlatform.createEntityType(key, Pondie::new, EntityDimensions.fixed(0.6F, 0.5F)));
    public static final EntityType<Islehopper> ISLEHOPPER = register("islehopper", key -> FOTPlatform.createEntityType(key, Islehopper::new, EntityDimensions.fixed(0.55F, 0.45F)));
    public static final EntityType<Ancientscale> ANCIENTSCALE = register("ancientscale", key -> FOTPlatform.createEntityType(key, Ancientscale::new, EntityDimensions.fixed(0.6F, 0.5F)));
    public static final EntityType<Plentifin> PLENTIFIN = register("plentifin", key -> FOTPlatform.createEntityType(key, Plentifin::new, EntityDimensions.fixed(0.5F, 0.45F)));
    public static final EntityType<Wildsplash> WILDSPLASH = register("wildsplash", key -> FOTPlatform.createEntityType(key, Wildsplash::new, EntityDimensions.fixed(0.6F, 0.5F)));
    public static final EntityType<Devilfish> DEVILFISH = register("devilfish", key -> FOTPlatform.createEntityType(key, Devilfish::new, EntityDimensions.fixed(0.5F, 0.55F)));
    public static final EntityType<Battlegill> BATTLEGILL = register("battlegill", key -> FOTPlatform.createEntityType(key, Battlegill::new, EntityDimensions.fixed(0.5F, 0.55F)));
    public static final EntityType<Wrecker> WRECKER = register("wrecker", key -> FOTPlatform.createEntityType(key, Wrecker::new, EntityDimensions.fixed(0.5F, 0.5F)));
    public static final EntityType<Stormfish> STORMFISH = register("stormfish", key -> FOTPlatform.createEntityType(key, Stormfish::new, EntityDimensions.fixed(0.75F, 0.55F)));

    public interface SpawnData
    {
        WeightedRandomList<MobSpawnSettings.SpawnerData> ANCIENTSCALE = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 12, 4, 8));
        WeightedRandomList<MobSpawnSettings.SpawnerData> PLENTIFIN = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 12, 4, 8));
        WeightedRandomList<MobSpawnSettings.SpawnerData> ANCIENTSCALE_AND_PLENTIFIN = WeightedRandomList.create(ANCIENTSCALE.unwrap().getFirst(), PLENTIFIN.unwrap().getFirst());
        WeightedRandomList<MobSpawnSettings.SpawnerData> BATTLEGILL = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 5, 2, 4));
        WeightedRandomList<MobSpawnSettings.SpawnerData> WRECKER = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 50, 4, 8));
    }

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Entity");
    }

    private static <T extends Entity> EntityType<T> register(String key, Function<String, EntityType<T>> type)
    {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, FishOfThieves.id(key), type.apply(key));
    }
}