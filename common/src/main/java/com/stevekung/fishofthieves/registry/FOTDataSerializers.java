package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.registry.variants.*;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class FOTDataSerializers
{
    public static final EntityDataSerializer<SplashtailVariant> SPLASHTAIL_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.SPLASHTAIL_VARIANT);
    public static final EntityDataSerializer<PondieVariant> PONDIE_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.PONDIE_VARIANT);
    public static final EntityDataSerializer<IslehopperVariant> ISLEHOPPER_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.ISLEHOPPER_VARIANT);
    public static final EntityDataSerializer<AncientscaleVariant> ANCIENTSCALE_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.ANCIENTSCALE_VARIANT);
    public static final EntityDataSerializer<PlentifinVariant> PLENTIFIN_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.PLENTIFIN_VARIANT);
    public static final EntityDataSerializer<WildsplashVariant> WILDSPLASH_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.WILDSPLASH_VARIANT);
    public static final EntityDataSerializer<DevilfishVariant> DEVILFISH_VARIANT = EntityDataSerializer.simpleId(FOTRegistry.DEVILFISH_VARIANT);

    public static void init()
    {
        EntityDataSerializers.registerSerializer(FOTDataSerializers.SPLASHTAIL_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.PONDIE_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.ISLEHOPPER_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.ANCIENTSCALE_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.PLENTIFIN_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.WILDSPLASH_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.DEVILFISH_VARIANT);
    }
}