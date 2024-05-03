package com.stevekung.fishofthieves.mixin.level.structure;

import java.io.IOException;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.stevekung.fishofthieves.FishOfThieves;
import net.minecraft.SharedConstants;
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
        var compoundTag = NbtIo.readCompressed(this.resourceManager.open(resourceLocation));

        if (resourceLocation.getNamespace().equals(FishOfThieves.MOD_ID) && compoundTag.contains("DataVersion", Tag.TAG_ANY_NUMERIC))
        {
            if (compoundTag.getInt("DataVersion") != SharedConstants.getCurrentVersion().getDataVersion().getVersion())
            {
                FishOfThieves.LOGGER.error("Fish of Thieves structures 'DataVersion' are not updated to match the current 'DataVersion'. Please report this to developer.");
            }
        }
    }
}