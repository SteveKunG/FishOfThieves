package com.stevekung.fishofthieves.mixin.client.level;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.stevekung.fishofthieves.registry.FOTBiomes;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends Level
{
    MixinClientLevel()
    {
        super(null, null, null, null, false, false, 0, 0);
    }

    @Inject(method = "doAnimateTick", at = @At(value = "INVOKE", target = "net/minecraft/world/level/biome/Biome.getAmbientParticle()Ljava/util/Optional;"))
    private void fishofthieves$playFireflyAmbients(int posX, int posY, int posZ, int range, RandomSource random, @Nullable Block block, BlockPos.MutableBlockPos blockPos, CallbackInfo info)
    {
        if (this.getBiome(blockPos).is(FOTBiomes.TROPICAL_ISLAND))
        {
            if (this.random.nextDouble() < 0.0000625d && this.isMoonVisible() && this.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos) <= blockPos.getY())
            {
                this.playLocalSound(blockPos, SoundEvents.FIREFLY_BUSH_IDLE, SoundSource.AMBIENT, 0.2f, 1.0F, false);
            }

            if (this.getMaxLocalRawBrightness(blockPos) <= 13 && random.nextFloat() <= 0.00625F)
            {
                this.addParticle(ParticleTypes.FIREFLY, blockPos.getX() + this.random.nextDouble(), blockPos.getY() + this.random.nextDouble(), blockPos.getZ() + this.random.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
    }
}