package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import com.stevekung.fishofthieves.registry.FOTDamageTypes;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;

public class DamageTagsProvider extends FabricTagsProvider<DamageType>
{
    public DamageTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, Registries.DAMAGE_TYPE, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.builder(FOTTags.DamageTypes.IS_MANGO).add(FOTDamageTypes.MANGO);
    }
}