package com.stevekung.fishofthieves.common.mixin.entity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import com.stevekung.fishofthieves.common.registry.FOTEntitySubPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.util.ExtraCodecs;

@Mixin(EntitySubPredicate.Types.class)
public class MixinEntitySubPredicate_Types
{
    @Shadow
    @Final
    @Mutable
    static BiMap<String, EntitySubPredicate.Type> TYPES;

    @Shadow
    @Final
    @Mutable
    static Codec<EntitySubPredicate.Type> TYPE_CODEC;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo info)
    {
        TYPES = ImmutableBiMap.copyOf(FOTEntitySubPredicate.putNewSubPredicates());
        TYPE_CODEC = ExtraCodecs.stringResolverCodec(TYPES.inverse()::get, TYPES::get);
    }
}