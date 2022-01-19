package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.entity.animal.Ancientscale;
import com.stevekung.fishofthieves.entity.animal.Islehopper;
import com.stevekung.fishofthieves.entity.animal.Pondie;
import com.stevekung.fishofthieves.entity.animal.Splashtail;

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
    public static final EntityType<Pondie> PONDIE = FabricEntityTypeBuilder.<Pondie>create(MobCategory.WATER_AMBIENT, Pondie::new).dimensions(EntityDimensions.fixed(0.6F, 0.5F)).trackRangeBlocks(4).build();
    public static final EntityType<Islehopper> ISLEHOPPER = FabricEntityTypeBuilder.<Islehopper>create(MobCategory.WATER_AMBIENT, Islehopper::new).dimensions(EntityDimensions.fixed(0.55F, 0.45F)).trackRangeBlocks(4).build();
    public static final EntityType<Ancientscale> ANCIENTSCALE = FabricEntityTypeBuilder.<Ancientscale>create(MobCategory.WATER_AMBIENT, Ancientscale::new).dimensions(EntityDimensions.fixed(0.6F, 0.5F)).trackRangeBlocks(4).build();

    public static void init()
    {
        register("splashtail", SPLASHTAIL);
        register("pondie", PONDIE);
        register("islehopper", ISLEHOPPER);
        register("ancientscale", ANCIENTSCALE);
    }

    private static <T extends Entity> EntityType<T> register(String key, EntityType<T> type)
    {
        return Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(FishOfThieves.MOD_ID, key), type);
    }
}