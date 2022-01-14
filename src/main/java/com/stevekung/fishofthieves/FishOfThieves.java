package com.stevekung.fishofthieves;

import com.stevekung.fishofthieves.config.FishOfThievesConfig;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public class FishOfThieves implements ModInitializer
{
    public static final String MOD_ID = "fishofthieves";
    public static FishOfThievesConfig CONFIG;

    public static final CreativeModeTab FOT_TAB = FabricItemGroupBuilder.build(new ResourceLocation(MOD_ID, "main"), () -> new ItemStack(FOTItems.SPLASHTAIL));

    @Override
    public void onInitialize()
    {
        AutoConfig.register(FishOfThievesConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(FishOfThievesConfig.class).getConfig();

        FOTItems.init();
        FOTEntities.init();
        FOTSoundEvents.init();

        FabricDefaultAttributeRegistry.register(FOTEntities.SPLASHTAIL, AbstractFish.createAttributes());

        SpawnRestrictionAccessor.callRegister(FOTEntities.SPLASHTAIL, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);

        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.BiomeCategory.OCEAN), FOTEntities.SPLASHTAIL.getCategory(), FOTEntities.SPLASHTAIL, 15, 8, 8);
    }
}