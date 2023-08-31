package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.ai.sensing.*;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public class FOTSensorTypes
{
    public static final SensorType<TemptingSensor> COMMON_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getCommonTemptations()));
    public static final SensorType<TemptingSensor> LEECHES_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getLeechesTemptations()));
    public static final SensorType<TemptingSensor> EARTHWORMS_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getEarthwormsTemptations()));
    public static final SensorType<TemptingSensor> GRUBS_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getGrubsTemptations()));
    public static final SensorType<NearestShipwreckSensor> NEAREST_SHIPWRECK = new SensorType<>(NearestShipwreckSensor::new);
    public static final SensorType<FishAttackablesSensor> BATTLEGILL_ATTACKABLES = new SensorType<>(() -> new FishAttackablesSensor(target -> target.getType().is(FOTTags.EntityTypes.BATTLEGILL_ATTACKABLE)));
    public static final SensorType<FishAttackablesSensor> DEVILFISH_ATTACKABLES = new SensorType<>(() -> new FishAttackablesSensor(target -> target.getType().is(FOTTags.EntityTypes.DEVILFISH_ATTACKABLE)));
    public static final SensorType<FishAttackablesSensor> WRECKER_ATTACKABLES = new SensorType<>(() -> new FishAttackablesSensor(target -> target.getType().is(FOTTags.EntityTypes.WRECKER_ATTACKABLE)));
    public static final SensorType<NonCreativePlayerSensor> NON_CREATIVE_NEAREST_PLAYERS = new SensorType<>(NonCreativePlayerSensor::new);
    public static final SensorType<NearestSchoolingThievesFishSensor> NEAREST_SCHOOLING_THIEVES_FISH = new SensorType<>(NearestSchoolingThievesFishSensor::new);
    public static final SensorType<MagmaBlockSensor> NEAREST_MAGMA_BLOCK = new SensorType<>(MagmaBlockSensor::new);
    public static final SensorType<LowBrightnessSensor> LOW_BRIGHTNESS = new SensorType<>(LowBrightnessSensor::new);
    public static final SensorType<NearestFlockLeaderSensor> NEAREST_FLOCK_LEADER = new SensorType<>(NearestFlockLeaderSensor::new);

    public static void init()
    {
        register("common_thieves_fish_temptations", COMMON_THIEVES_FISH_TEMPTATIONS);
        register("leeches_thieves_fish_temptations", LEECHES_THIEVES_FISH_TEMPTATIONS);
        register("earthworms_thieves_fish_temptations", EARTHWORMS_THIEVES_FISH_TEMPTATIONS);
        register("grubs_thieves_fish_temptations", GRUBS_THIEVES_FISH_TEMPTATIONS);
        register("nearest_shipwreck", NEAREST_SHIPWRECK);
        register("battlegill_attackables", BATTLEGILL_ATTACKABLES);
        register("devilfish_attackables", DEVILFISH_ATTACKABLES);
        register("wrecker_attackables", WRECKER_ATTACKABLES);
        register("non_creative_nearest_players", NON_CREATIVE_NEAREST_PLAYERS);
        register("nearest_schooling_thieves_fish", NEAREST_SCHOOLING_THIEVES_FISH);
        register("nearest_magma_block", NEAREST_MAGMA_BLOCK);
        register("low_brightness", LOW_BRIGHTNESS);
        register("nearest_flock_leader", NEAREST_FLOCK_LEADER);
    }

    private static <U extends Sensor<?>> void register(String key, SensorType<U> sensorType)
    {
        Registry.register(Registry.SENSOR_TYPE, FishOfThieves.res(key), sensorType);
    }
}