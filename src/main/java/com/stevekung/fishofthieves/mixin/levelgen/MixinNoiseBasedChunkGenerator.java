package com.stevekung.fishofthieves.mixin.levelgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.FOTEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

@Mixin(NoiseBasedChunkGenerator.class)
public class MixinNoiseBasedChunkGenerator
{
    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> ANCIENTSCALES = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.ANCIENTSCALE, 1, 8, 12));

    @Inject(method = "getMobsAt", cancellable = true, at = @At("HEAD"))
    private void addFishSpawn(Biome biome, StructureFeatureManager structureFeatureManager, MobCategory category, BlockPos pos, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> info)
    {
        if (structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.OCEAN_RUIN).isValid() && category == MobCategory.WATER_AMBIENT)
        {
            info.setReturnValue(ANCIENTSCALES);
        }
    }
}