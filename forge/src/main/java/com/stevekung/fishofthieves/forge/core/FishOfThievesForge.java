package com.stevekung.fishofthieves.forge.core;

import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.forge.proxy.ClientProxyForge;
import com.stevekung.fishofthieves.forge.proxy.CommonProxyForge;
import com.stevekung.fishofthieves.forge.registry.FOTLootItemConditionsForge;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(FishOfThieves.MOD_ID)
public class FishOfThievesForge
{
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, FishOfThieves.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES, FishOfThieves.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FishOfThieves.MOD_ID);

    public static CommonProxyForge PROXY;

    public FishOfThievesForge()
    {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(FishOfThievesConfig.class);
        MinecraftForge.EVENT_BUS.register(FOTLootItemConditionsForge.class);
        ENTITY.register(modEventBus);
        ITEM.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);

        FishOfThieves.init();

        PROXY = DistExecutor.safeRunForDist(() -> ClientProxyForge::new, () -> CommonProxyForge::new);
        PROXY.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FishOfThievesConfig.SPEC);
    }
}