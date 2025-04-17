package com.stevekung.fishofthieves.registry;

import com.stevekung.fishofthieves.FOTPlatform;
import com.stevekung.fishofthieves.FishOfThieves;
import com.stevekung.fishofthieves.entity.variant.*;

import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;

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
        FishOfThieves.LOGGER.info("Registering Entity Data Serializer");

        FOTPlatform.registerSerializer("splashtail_variant", FOTDataSerializers.SPLASHTAIL_VARIANT);
        FOTPlatform.registerSerializer("pondie_variant", FOTDataSerializers.PONDIE_VARIANT);
        FOTPlatform.registerSerializer("islehopper_variant", FOTDataSerializers.ISLEHOPPER_VARIANT);
        FOTPlatform.registerSerializer("ancientscale_variant", FOTDataSerializers.ANCIENTSCALE_VARIANT);
        FOTPlatform.registerSerializer("plentifin_variant", FOTDataSerializers.PLENTIFIN_VARIANT);
        FOTPlatform.registerSerializer("wildsplash_variant", FOTDataSerializers.WILDSPLASH_VARIANT);
        FOTPlatform.registerSerializer("devilfish_variant", FOTDataSerializers.DEVILFISH_VARIANT);
        FOTPlatform.registerSerializer("battlegill_variant", FOTDataSerializers.BATTLEGILL_VARIANT);
        FOTPlatform.registerSerializer("wrecker_variant", FOTDataSerializers.WRECKER_VARIANT);
        FOTPlatform.registerSerializer("stormfish_variant", FOTDataSerializers.STORMFISH_VARIANT);
    }
}