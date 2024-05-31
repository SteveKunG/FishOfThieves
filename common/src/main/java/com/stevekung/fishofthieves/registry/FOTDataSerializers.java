package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.entity.variant.*;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class FOTDataSerializers
{
    public static final EntityDataSerializer<Holder<SplashtailVariant>> SPLASHTAIL_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.SPLASHTAIL_VARIANT));
    public static final EntityDataSerializer<Holder<PondieVariant>> PONDIE_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.PONDIE_VARIANT));
    public static final EntityDataSerializer<Holder<IslehopperVariant>> ISLEHOPPER_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.ISLEHOPPER_VARIANT));
    public static final EntityDataSerializer<Holder<AncientscaleVariant>> ANCIENTSCALE_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.ANCIENTSCALE_VARIANT));
    public static final EntityDataSerializer<Holder<PlentifinVariant>> PLENTIFIN_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.PLENTIFIN_VARIANT));
    public static final EntityDataSerializer<Holder<WildsplashVariant>> WILDSPLASH_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.WILDSPLASH_VARIANT));
    public static final EntityDataSerializer<Holder<DevilfishVariant>> DEVILFISH_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.DEVILFISH_VARIANT));
    public static final EntityDataSerializer<Holder<BattlegillVariant>> BATTLEGILL_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.BATTLEGILL_VARIANT));
    public static final EntityDataSerializer<Holder<WreckerVariant>> WRECKER_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.WRECKER_VARIANT));
    public static final EntityDataSerializer<Holder<StormfishVariant>> STORMFISH_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.holderRegistry(FOTRegistries.STORMFISH_VARIANT));

    public static void init()
    {
        EntityDataSerializers.registerSerializer(FOTDataSerializers.SPLASHTAIL_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.PONDIE_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.ISLEHOPPER_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.ANCIENTSCALE_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.PLENTIFIN_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.WILDSPLASH_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.DEVILFISH_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.BATTLEGILL_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.WRECKER_VARIANT);
        EntityDataSerializers.registerSerializer(FOTDataSerializers.STORMFISH_VARIANT);
    }
}