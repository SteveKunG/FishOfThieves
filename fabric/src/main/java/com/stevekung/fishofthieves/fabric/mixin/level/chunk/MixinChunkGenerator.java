package com.stevekung.fishofthieves.fabric.mixin.level.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.stevekung.fishofthieves.registry.FOTEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator
{
    @Inject(method = "getMobsAt", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$addFishSpawn(Holder<Biome> holder, StructureFeatureManager structureFeatureManager, MobCategory category, BlockPos pos, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> info)
    {
        if (category == MobCategory.WATER_AMBIENT)
        {
            if (structureFeatureManager.getStructureWithPieceAt(pos, BuiltinStructures.OCEAN_RUIN_WARM).isValid())
            {
                info.setReturnValue(FOTEntities.ANCIENTSCALES);
            }
            if (structureFeatureManager.getStructureWithPieceAt(pos, BuiltinStructures.MINESHAFT).isValid() || structureFeatureManager.getStructureWithPieceAt(pos, BuiltinStructures.STRONGHOLD).isValid())
            {
                info.setReturnValue(FOTEntities.PLENTIFINS);
            }
            if (structureFeatureManager.getStructureWithPieceAt(pos, BuiltinStructures.OCEAN_MONUMENT).isValid() || structureFeatureManager.getStructureWithPieceAt(pos, BuiltinStructures.PILLAGER_OUTPOST).isValid())
            {
                info.setReturnValue(FOTEntities.BATTLEGILLS);
            }
            if (structureFeatureManager.getStructureWithPieceAt(pos, BuiltinStructures.SHIPWRECK).isValid())
            {
                info.setReturnValue(FOTEntities.WRECKERS);
            }
        }
    }
}