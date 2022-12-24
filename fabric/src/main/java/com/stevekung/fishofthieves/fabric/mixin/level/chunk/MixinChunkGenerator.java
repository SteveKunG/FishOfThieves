package com.stevekung.fishofthieves.fabric.mixin.level.chunk;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator
{
    @Inject(method = "getMobsAt", cancellable = true, at = @At("HEAD"))
    private void fishofthieves$addFishSpawn(Holder<Biome> holder, StructureManager structureFeatureManager, MobCategory category, BlockPos pos, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> info)
    {
        if (category == MobCategory.WATER_AMBIENT)
        {
            if (structureFeatureManager.getStructureWithPieceAt(pos, StructureTags.OCEAN_RUIN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.ANCIENTSCALE);
            }
            else if (structureFeatureManager.getStructureWithPieceAt(pos, FOTTags.Structures.ANCIENTSCALES_SPAWN_IN).isValid() || structureFeatureManager.getStructureWithPieceAt(pos, FOTTags.Structures.PLENTIFINS_SPAWN_IN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.ANCIENTSCALE_AND_PLENTIFIN);
            }
            else if (structureFeatureManager.getStructureWithPieceAt(pos, FOTTags.Structures.BATTLEGILLS_SPAWN_IN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.BATTLEGILL);
            }
            else if (structureFeatureManager.getStructureWithPieceAt(pos, FOTTags.Structures.WRECKERS_SPAWN_IN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.WRECKER);
            }
        }
    }
}