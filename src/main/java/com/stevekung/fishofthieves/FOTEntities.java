package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.entity.Splashtail;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FOTEntities
{
    public static final EntityType<Splashtail> SPLASHTAIL = FabricEntityTypeBuilder.<Splashtail>create(MobCategory.WATER_AMBIENT, Splashtail::new).dimensions(EntityDimensions.fixed(0.7F, 0.4F)).trackRangeBlocks(4).build();

    public static void init()
    {
        register("splashtail", SPLASHTAIL);
    }

    private static <T extends Entity> EntityType<T> register(String key, EntityType<T> type)
    {
        return Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, key), type);
    }
}