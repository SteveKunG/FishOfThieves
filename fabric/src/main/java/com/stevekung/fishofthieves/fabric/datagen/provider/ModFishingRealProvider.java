package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.fabric.datagen.FishingRealProvider;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class ModFishingRealProvider extends FishingRealProvider
{
    public ModFishingRealProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(output, provider);
    }

    @Override
    public void addFishingReal()
    {
        this.add(FOTItems.SPLASHTAIL, FOTEntities.SPLASHTAIL);
        this.add(FOTItems.PONDIE, FOTEntities.PONDIE);
        this.add(FOTItems.ISLEHOPPER, FOTEntities.ISLEHOPPER);
        this.add(FOTItems.ANCIENTSCALE, FOTEntities.ANCIENTSCALE);
        this.add(FOTItems.PLENTIFIN, FOTEntities.PLENTIFIN);
        this.add(FOTItems.WILDSPLASH, FOTEntities.WILDSPLASH);
        this.add(FOTItems.DEVILFISH, FOTEntities.DEVILFISH);
        this.add(FOTItems.BATTLEGILL, FOTEntities.BATTLEGILL);
        this.add(FOTItems.WRECKER, FOTEntities.WRECKER);
        this.add(FOTItems.STORMFISH, FOTEntities.STORMFISH);
    }
}