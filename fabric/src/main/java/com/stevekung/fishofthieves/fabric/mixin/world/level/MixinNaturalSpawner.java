package com.stevekung.fishofthieves.fabric.mixin.world.level;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.stevekung.fishofthieves.common.registry.FOTEntities;
import com.stevekung.fishofthieves.common.registry.FOTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;

@Mixin(NaturalSpawner.class)
public class MixinNaturalSpawner
{
    @Inject(method = "mobsAt", cancellable = true, at = @At("HEAD"))
    private static void fishofthieves$addFishSpawn(ServerLevel level, StructureManager structureManager, ChunkGenerator generator, MobCategory category, BlockPos pos, @Nullable Holder<Biome> biome, CallbackInfoReturnable<WeightedRandomList<MobSpawnSettings.SpawnerData>> info)
    {
        if (category == MobCategory.WATER_AMBIENT)
        {
            if (structureManager.getStructureWithPieceAt(pos, FOTTags.Structures.ANCIENTSCALES_SPAWN_IN).isValid() || structureManager.getStructureWithPieceAt(pos, FOTTags.Structures.PLENTIFINS_SPAWN_IN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.ANCIENTSCALE_AND_PLENTIFIN);
            }
            else if (structureManager.getStructureWithPieceAt(pos, FOTTags.Structures.BATTLEGILLS_SPAWN_IN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.BATTLEGILL);
            }
            else if (structureManager.getStructureWithPieceAt(pos, FOTTags.Structures.WRECKERS_SPAWN_IN).isValid())
            {
                info.setReturnValue(FOTEntities.SpawnData.WRECKER);
            }
        }
    }
}