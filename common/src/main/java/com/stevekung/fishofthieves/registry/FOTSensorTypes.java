package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.ai.AbstractThievesFishAi;
import com.stevekung.fishofthieves.entity.ai.sensing.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public class FOTSensorTypes
{
    public static final SensorType<TemptingSensor> COMMON_THIEVES_FISH_TEMPTATIONS = register("common_thieves_fish_temptations", new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getCommonTemptations())));
    public static final SensorType<TemptingSensor> LEECHES_THIEVES_FISH_TEMPTATIONS = register("leeches_thieves_fish_temptations", new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getLeechesTemptations())));
    public static final SensorType<TemptingSensor> EARTHWORMS_THIEVES_FISH_TEMPTATIONS = register("earthworms_thieves_fish_temptations", new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getEarthwormsTemptations())));
    public static final SensorType<TemptingSensor> GRUBS_THIEVES_FISH_TEMPTATIONS = register("grubs_thieves_fish_temptations", new SensorType<>(() -> new TemptingSensor(AbstractThievesFishAi.getGrubsTemptations())));
    public static final SensorType<NearestWreckerLocatedSensor> NEAREST_WRECKER_LOCATED = register("nearest_wrecker_located", new SensorType<>(NearestWreckerLocatedSensor::new));
    public static final SensorType<FishAttackablesSensor> BATTLEGILL_ATTACKABLES = register("battlegill_attackables", new SensorType<>(() -> new FishAttackablesSensor(target -> target.getType().is(FOTTags.EntityTypes.BATTLEGILL_ATTACKABLE))));
    public static final SensorType<FishAttackablesSensor> DEVILFISH_ATTACKABLES = register("devilfish_attackables", new SensorType<>(() -> new FishAttackablesSensor(target -> target.getType().is(FOTTags.EntityTypes.DEVILFISH_ATTACKABLE))));
    public static final SensorType<FishAttackablesSensor> WRECKER_ATTACKABLES = register("wrecker_attackables", new SensorType<>(() -> new FishAttackablesSensor(target -> target.getType().is(FOTTags.EntityTypes.WRECKER_ATTACKABLE))));
    public static final SensorType<NonCreativePlayerSensor> NON_CREATIVE_NEAREST_PLAYERS = register("non_creative_nearest_players", new SensorType<>(NonCreativePlayerSensor::new));
    public static final SensorType<NearestSchoolingThievesFishSensor> NEAREST_SCHOOLING_THIEVES_FISH = register("nearest_schooling_thieves_fish", new SensorType<>(NearestSchoolingThievesFishSensor::new));
    public static final SensorType<MagmaBlockSensor> NEAREST_MAGMA_BLOCK = register("nearest_magma_block", new SensorType<>(MagmaBlockSensor::new));
    public static final SensorType<LowBrightnessSensor> LOW_BRIGHTNESS = register("low_brightness", new SensorType<>(LowBrightnessSensor::new));
    public static final SensorType<NearestFlockLeaderSensor> NEAREST_FLOCK_LEADER = register("nearest_flock_leader", new SensorType<>(NearestFlockLeaderSensor::new));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Sensor Type");
    }

    private static <U extends Sensor<?>> SensorType<U> register(String key, SensorType<U> sensorType)
    {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE, FishOfThieves.id(key), sensorType);
    }
}