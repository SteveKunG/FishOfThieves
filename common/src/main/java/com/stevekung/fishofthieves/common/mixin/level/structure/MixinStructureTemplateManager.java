package com.stevekung.fishofthieves.common.mixin.level.structure;

import java.io.IOException;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.fishofthieves.common.FishOfThieves;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

@Mixin(StructureTemplateManager.class)
public class MixinStructureTemplateManager
{
    @Shadow
    ResourceManager resourceManager;

    @Inject(method = "loadFromResource", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void fishofthieves$validateStructureVersion(ResourceLocation id, CallbackInfoReturnable<Optional<StructureTemplate>> info, ResourceLocation resourceLocation) throws IOException
    {
        var compoundTag = NbtIo.readCompressed(this.resourceManager.open(resourceLocation), NbtAccounter.unlimitedHeap());

        if (resourceLocation.getNamespace().equals(FishOfThieves.MOD_ID) && compoundTag.contains("DataVersion", Tag.TAG_ANY_NUMERIC))
        {
            var structureDataVersion = compoundTag.getInt("DataVersion");
            var currentDataVersion = SharedConstants.getCurrentVersion().getDataVersion().getVersion();

            if (structureDataVersion != currentDataVersion)
            {
                FishOfThieves.LOGGER.error("Fish of Thieves structures 'DataVersion' are not updated to match the current 'DataVersion', Expected {} but got {}. Please report this to developer.", currentDataVersion, structureDataVersion);
            }
        }
    }
}