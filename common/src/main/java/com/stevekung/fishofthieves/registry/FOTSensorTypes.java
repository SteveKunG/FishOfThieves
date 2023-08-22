package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.ai.sensing.TrophyFishSensor;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public class FOTSensorTypes
{
    public static final SensorType<TemptingSensor> COMMON_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getCommonTemptations()));
    public static final SensorType<TemptingSensor> LEECHES_THIEVES_FISH_TEMPTATIONS = new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getLeechesTemptations()));
    public static final SensorType<TrophyFishSensor> TROPHY_FISH_SENSOR = new SensorType<>(TrophyFishSensor::new);

    public static void init()
    {
        register("common_thieves_fish_temptations", COMMON_THIEVES_FISH_TEMPTATIONS);
        register("leeches_thieves_fish_temptations", LEECHES_THIEVES_FISH_TEMPTATIONS);
        register("trophy_fish_sensor", TROPHY_FISH_SENSOR);
    }

    private static <U extends Sensor<?>> void register(String key, SensorType<U> sensorType)
    {
        Registry.register(Registry.SENSOR_TYPE, FishOfThieves.res(key), sensorType);
    }
}