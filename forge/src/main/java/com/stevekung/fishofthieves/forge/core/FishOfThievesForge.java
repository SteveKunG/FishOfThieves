package com.stevekung.fishofthieves.forge.core;

import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.forge.datagen.FOTBiomeModifier;
import com.stevekung.fishofthieves.forge.datagen.FOTStructureModifiers;
import com.stevekung.fishofthieves.forge.proxy.ClientProxyForge;
import com.stevekung.fishofthieves.forge.proxy.CommonProxyForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(FishOfThieves.MOD_ID)
public class FishOfThievesForge
{
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<Codec<? extends StructureModifier>> STRUCTURE_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, FishOfThieves.MOD_ID);

    public static CommonProxyForge PROXY;

    private static final String THIEVES_FISH_SPAWNS_IN_STRUCTURE = "thieves_fish_spawns_in_structure";
    public static final ResourceLocation ADD_THIEVES_FISH_SPAWNS_IN_STRUCTURE_RL = new ResourceLocation(FishOfThieves.MOD_ID, THIEVES_FISH_SPAWNS_IN_STRUCTURE);

    public FishOfThievesForge()
    {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
        modEventBus.addListener(this::commonSetup);
        ITEM.register(modEventBus);
        ENTITY.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        BIOME_MODIFIERS.register(modEventBus);
        STRUCTURE_MODIFIERS.register(modEventBus);

        FishOfThieves.init();

        modEventBus.addListener(FOTBiomeModifier::generateBiomeModifiers);
        STRUCTURE_MODIFIERS.register(THIEVES_FISH_SPAWNS_IN_STRUCTURE, FOTStructureModifiers.Modifier::makeCodec);
        modEventBus.addListener(FOTStructureModifiers::generateStructureModifiers);

        PROXY = DistExecutor.safeRunForDist(() -> ClientProxyForge::new, () -> CommonProxyForge::new);
        PROXY.init();
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        FishOfThieves.initCommon();

        if (ModList.get().isLoaded("aquaculture"))
        {
            Aquaculture2.init();
        }
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event)
    {
        event.register(ForgeRegistries.Keys.ITEMS, helper -> FOTItems.init());
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> FOTEntities.init());
    }
}