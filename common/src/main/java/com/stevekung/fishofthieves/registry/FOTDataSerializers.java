package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.SplashtailVariant;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class FOTDataSerializers
{
    public static final EntityDataSerializer<SplashtailVariant> SPLASHTAIL_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.SPLASHTAIL_VARIANT);

    public static void init()
    {
        EntityDataSerializers.registerSerializer(FOTDataSerializers.SPLASHTAIL_VARIANT);
    }
}