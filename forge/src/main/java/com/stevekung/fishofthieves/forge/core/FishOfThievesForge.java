package com.stevekung.fishofthieves.forge.core;

import com.stevekung.fishofthieves.config.FishOfThievesConfig;
import com.stevekung.fishofthieves.core.FishOfThieves;
import com.stevekung.fishofthieves.forge.compatibility.Aquaculture2;
import com.stevekung.fishofthieves.forge.proxy.ClientProxyForge;
import com.stevekung.fishofthieves.forge.proxy.CommonProxyForge;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        modEventBus.register(this);
        modEventBus.addListener(this::commonSetup);
        ITEM.register(modEventBus);
        ENTITY.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);

        FishOfThieves.init();

        PROXY = DistExecutor.safeRunForDist(() -> ClientProxyForge::new, () -> CommonProxyForge::new);
        PROXY.init();

        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, screen) -> AutoConfig.getConfigScreen(FishOfThievesConfig.class, screen).get()));
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
    public void registerItem(RegistryEvent.Register<Item> event)
    {
        FOTItems.init();
    }

    @SubscribeEvent
    public void registerEntityType(RegistryEvent.Register<EntityType<?>> event)
    {
        FOTEntities.init();
    }
}