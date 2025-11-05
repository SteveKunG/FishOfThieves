package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

public class FOTEnvironmentAttributes
{
    public static final EnvironmentAttribute<Boolean> AMBER_PLENTIFIN_SPAWNS = register("gameplay/amber_plentifin_spawns", EnvironmentAttribute.builder(AttributeTypes.BOOLEAN).defaultValue(false));

    public static void init()
    {
        FishOfThieves.LOGGER.info("Registering Environment Attribute");
    }

    private static <Value> EnvironmentAttribute<Value> register(String string, EnvironmentAttribute.Builder<Value> builder)
    {
        var environmentAttribute = builder.build();
        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, FishOfThieves.id(string), environmentAttribute);
    }
}