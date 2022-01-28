package com.stevekung.fishofthieves.mixin.levelgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.stevekung.fishofthieves.registry.FOTEntities;

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
    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> PLENTIFINS = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.PLENTIFIN, 12, 4, 8));
    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> BATTLEGILLS = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.BATTLEGILL, 5, 2, 4));
    private static final WeightedRandomList<MobSpawnSettings.SpawnerData> WRECKERS = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(FOTEntities.WRECKER, 24, 4, 8));

    @Inject(method = "getMobsAt", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$addFishSpawn(Biome biome, StructureFeatureManager structureFeatureManager, MobCategory category, BlockPos pos, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> info)
    {
        if (category == MobCategory.WATER_AMBIENT)
        {
            if (structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.OCEAN_RUIN).isValid())
            {
                info.setReturnValue(ANCIENTSCALES);
            }
            if (structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.MINESHAFT).isValid() || structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.STRONGHOLD).isValid())
            {
                info.setReturnValue(PLENTIFINS);
            }
            if (structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.OCEAN_MONUMENT).isValid() || structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.PILLAGER_OUTPOST).isValid())
            {
                info.setReturnValue(BATTLEGILLS);
            }
            if (structureFeatureManager.getStructureWithPieceAt(pos, StructureFeature.SHIPWRECK).isValid())
            {
                info.setReturnValue(WRECKERS);
            }
        }
    }
}