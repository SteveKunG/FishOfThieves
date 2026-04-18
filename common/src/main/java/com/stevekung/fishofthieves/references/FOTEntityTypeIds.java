package com.stevekung.fishofthieves.references;

import com.stevekung.fishofthieves.FishOfThieves;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

public class FOTEntityTypeIds
{
    public static final ResourceKey<EntityType<?>> SPLASHTAIL = create("splashtail");
    public static final ResourceKey<EntityType<?>> PONDIE = create("pondie");
    public static final ResourceKey<EntityType<?>> ISLEHOPPER = create("islehopper");
    public static final ResourceKey<EntityType<?>> ANCIENTSCALE = create("ancientscale");
    public static final ResourceKey<EntityType<?>> PLENTIFIN = create("plentifin");
    public static final ResourceKey<EntityType<?>> WILDSPLASH = create("wildsplash");
    public static final ResourceKey<EntityType<?>> DEVILFISH = create("devilfish");
    public static final ResourceKey<EntityType<?>> BATTLEGILL = create("battlegill");
    public static final ResourceKey<EntityType<?>> WRECKER = create("wrecker");
    public static final ResourceKey<EntityType<?>> STORMFISH = create("stormfish");

    public static final ResourceKey<EntityType<?>> COCONUT_BOAT = create("coconut_boat");
    public static final ResourceKey<EntityType<?>> COCONUT_CHEST_BOAT = create("coconut_chest_boat");
    public static final ResourceKey<EntityType<?>> SHOAL = create("shoal");

    private static ResourceKey<EntityType<?>> create(String name)
    {
        return ResourceKey.create(Registries.ENTITY_TYPE, FishOfThieves.id(name));
    }
}