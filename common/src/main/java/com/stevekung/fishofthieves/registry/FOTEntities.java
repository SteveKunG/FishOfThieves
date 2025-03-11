package com.stevekung.fishofthieves.registry;

import java.util.function.Function;
import java.util.function.Supplier;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.animal.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class FOTEntities
{
    public static final EntityType<Splashtail> SPLASHTAIL = register("splashtail", key -> createFishEntityType(key, Splashtail::new, EntityDimensions.fixed(0.7F, 0.4F)));
    public static final EntityType<Pondie> PONDIE = register("pondie", key -> createFishEntityType(key, Pondie::new, EntityDimensions.fixed(0.6F, 0.5F)));
    public static final EntityType<Islehopper> ISLEHOPPER = register("islehopper", key -> createFishEntityType(key, Islehopper::new, EntityDimensions.fixed(0.55F, 0.45F)));
    public static final EntityType<Ancientscale> ANCIENTSCALE = register("ancientscale", key -> createFishEntityType(key, Ancientscale::new, EntityDimensions.fixed(0.6F, 0.5F)));
    public static final EntityType<Plentifin> PLENTIFIN = register("plentifin", key -> createFishEntityType(key, Plentifin::new, EntityDimensions.fixed(0.5F, 0.45F)));
    public static final EntityType<Wildsplash> WILDSPLASH = register("wildsplash", key -> createFishEntityType(key, Wildsplash::new, EntityDimensions.fixed(0.6F, 0.5F)));
    public static final EntityType<Devilfish> DEVILFISH = register("devilfish", key -> createFishEntityType(key, Devilfish::new, EntityDimensions.fixed(0.5F, 0.55F)));
    public static final EntityType<Battlegill> BATTLEGILL = register("battlegill", key -> createFishEntityType(key, Battlegill::new, EntityDimensions.fixed(0.5F, 0.55F)));
    public static final EntityType<Wrecker> WRECKER = register("wrecker", key -> createFishEntityType(key, Wrecker::new, EntityDimensions.fixed(0.5F, 0.5F)));
    public static final EntityType<Stormfish> STORMFISH = register("stormfish", key -> createFishEntityType(key, Stormfish::new, EntityDimensions.fixed(0.75F, 0.55F)));

    public static final EntityType<Boat> COCONUT_BOAT = register("coconut_boat", key -> createBoatEntityType(key, boatFactory(() -> FOTItems.COCONUT_BOAT)));
    public static final EntityType<ChestBoat> COCONUT_CHEST_BOAT = register("coconut_chest_boat", key -> createBoatEntityType(key, chestBoatFactory(() -> FOTItems.COCONUT_CHEST_BOAT)));

    public interface SpawnData
    {
        WeightedList<MobSpawnSettings.SpawnerData> ANCIENTSCALE = WeightedList.<MobSpawnSettings.SpawnerData>builder().add(new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 4, 8), 12).build();
        WeightedList<MobSpawnSettings.SpawnerData> PLENTIFIN = WeightedList.<MobSpawnSettings.SpawnerData>builder().add(new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 4, 8), 12).build();
        WeightedList<MobSpawnSettings.SpawnerData> ANCIENTSCALE_AND_PLENTIFIN = WeightedList.of(ANCIENTSCALE.unwrap().getFirst(), PLENTIFIN.unwrap().getFirst());
        WeightedList<MobSpawnSettings.SpawnerData> BATTLEGILL = WeightedList.<MobSpawnSettings.SpawnerData>builder().add(new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 2, 4), 5).build();
        WeightedList<MobSpawnSettings.SpawnerData> WRECKER = WeightedList.<MobSpawnSettings.SpawnerData>builder().add(new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 4, 8), 50).build();
    }

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Entity");
    }

    private static <T extends Entity> EntityType<T> register(String key, Function<String, EntityType<T>> type)
    {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, FishOfThieves.id(key), type.apply(key));
    }

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter)
    {
        return (entityType, level) -> new Boat(entityType, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter)
    {
        return (entityType, level) -> new ChestBoat(entityType, level, boatItemGetter);
    }

    private static <T extends Entity> EntityType<T> createFishEntityType(String key, EntityType.EntityFactory<T> entityFactory, EntityDimensions dimensions)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.WATER_AMBIENT).sized(dimensions.width(), dimensions.height()).clientTrackingRange(4).build(ResourceKey.create(Registries.ENTITY_TYPE, FishOfThieves.id(key)));
    }

    private static <T extends Entity> EntityType<T> createBoatEntityType(String key, EntityType.EntityFactory<T> entityFactory)
    {
        return EntityType.Builder.of(entityFactory, MobCategory.MISC).noLootTable().sized(1.375F, 0.5625F).eyeHeight(0.5625F).clientTrackingRange(10).build(ResourceKey.create(Registries.ENTITY_TYPE, FishOfThieves.id(key)));
    }
}