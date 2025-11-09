package com.stevekung.fishofthieves.mixin.level;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.network.DebugCenterPosStructureInfo;
import com.stevekung.fishofthieves.registry.FOTDebugSubscriptions;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.debug.DebugValueSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(LevelChunk.class)
public abstract class MixinLevelChunk extends ChunkAccess
{
    MixinLevelChunk()
    {
        super(null, null, null, null, 0, null, null);
    }

    @Inject(method = "registerDebugValues", at = @At("HEAD"))
    private void fishofthieves$registerDebug(ServerLevel level, DebugValueSource.Registration registration, CallbackInfo info)
    {
        registration.register(FOTDebugSubscriptions.STRUCTURE_CENTER_POS, () ->
        {
            List<DebugCenterPosStructureInfo> list = new ArrayList<>();

            for (var structureStart : this.getAllStarts().values())
            {
                DebugCenterPosStructureInfo.addStructures(structureStart, list, (structurePosList, structureStartx) ->
                {
                    var optional = structureStartx.getPieces().stream().map(structurePiece -> structurePiece.getBoundingBox().getCenter()).findAny();
                    optional.ifPresent(blockPos -> structurePosList.add(new DebugCenterPosStructureInfo.StructurePos(blockPos, level.registryAccess().lookupOrThrow(Registries.STRUCTURE).getKey(structureStart.getStructure()))));
                });
            }
            return list;
        });
    }
}