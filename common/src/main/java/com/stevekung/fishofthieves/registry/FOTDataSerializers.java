package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.IslehopperVariant;
import com.stevekung.fishofthieves.registry.variants.PondieVariant;
import com.stevekung.fishofthieves.registry.variants.SplashtailVariant;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class FOTDataSerializers
{
    public static final EntityDataSerializer<SplashtailVariant> SPLASHTAIL_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.SPLASHTAIL_VARIANT);
    public static final EntityDataSerializer<PondieVariant> PONDIE_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.PONDIE_VARIANT);
    public static final EntityDataSerializer<IslehopperVariant> ISLEHOPPER_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.ISLEHOPPER_VARIANT);

    public static void init()
    {
        EntityDataSerializers.registerSerializer(FOTDataSerializers.SPLASHTAIL_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.PONDIE_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.ISLEHOPPER_VARIANT);
    }
}