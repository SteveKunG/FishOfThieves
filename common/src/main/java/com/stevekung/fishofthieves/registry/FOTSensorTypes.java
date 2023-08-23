package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.ai.sensing.NearestShipwreckSensor;
import com.stevekung.fishofthieves.entity.ai.sensing.TrophyFishSensor;
import com.stevekung.fishofthieves.entity.ai.sensing.WreckerAttackablesSensor;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public class FOTSensorTypes
{
    public static final SensorType<TemptingSensor> COMMON_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getCommonTemptations()));
    public static final SensorType<TemptingSensor> LEECHES_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getLeechesTemptations()));
    public static final SensorType<TemptingSensor> EARTHWORMS_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getEarthwormsTemptations()));
    public static final SensorType<TrophyFishSensor> TROPHY_FISH_SENSOR = new SensorType<>(TrophyFishSensor::new);
    public static final SensorType<NearestShipwreckSensor> NEAREST_SHIPWRECK = new SensorType<>(NearestShipwreckSensor::new);
    public static final SensorType<WreckerAttackablesSensor> WRECKER_ATTACKABLES = new SensorType<>(WreckerAttackablesSensor::new);

    public static void init()
    {
        register("common_thieves_fish_temptations", COMMON_THIEVES_FISH_TEMPTATIONS);
        register("leeches_thieves_fish_temptations", LEECHES_THIEVES_FISH_TEMPTATIONS);
        register("earthworms_thieves_fish_temptations", EARTHWORMS_THIEVES_FISH_TEMPTATIONS);
        register("trophy_fish_sensor", TROPHY_FISH_SENSOR);
        register("nearest_shipwreck", NEAREST_SHIPWRECK);
        register("wrecker_attackables", WRECKER_ATTACKABLES);
    }

    private static <U extends Sensor<?>> void register(String key, SensorType<U> sensorType)
    {
        Registry.register(Registry.SENSOR_TYPE, FishOfThieves.res(key), sensorType);
    }
}