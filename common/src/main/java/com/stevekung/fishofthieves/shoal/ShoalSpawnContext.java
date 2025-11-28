package com.stevekung.fishofthieves.shoal;

import com.stevekung.fishofthieves.utils.Continentalness;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public record ShoalSpawnContext(Holder<Biome> biome, Continentalness continentalness) {}