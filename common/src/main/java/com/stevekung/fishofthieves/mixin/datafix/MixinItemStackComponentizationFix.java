package com.stevekung.fishofthieves.mixin.datafix;

import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.stevekung.fishofthieves.entity.ThievesFish;
import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;

@Mixin(ItemStackComponentizationFix.class)
public class MixinItemStackComponentizationFix
{
    @Shadow
    @Final
    @Mutable
    static Set<String> BUCKETED_MOB_IDS;

    @Shadow
    @Final
    @Mutable
    static List<String> BUCKETED_MOB_TAGS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fishofthieves$addBucketFixers(CallbackInfo info)
    {
        BUCKETED_MOB_IDS = Sets.newLinkedHashSet(BUCKETED_MOB_IDS);
        BUCKETED_MOB_IDS.add("fishofthieves:splashtail_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:pondie_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:islehopper_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:ancientscale_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:plentifin_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:wildsplash_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:devilfish_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:battlegill_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:wrecker_bucket");
        BUCKETED_MOB_IDS.add("fishofthieves:stormfish_bucket");

        BUCKETED_MOB_TAGS = Lists.newLinkedList(BUCKETED_MOB_TAGS);
        BUCKETED_MOB_TAGS.add(ThievesFish.VARIANT_TAG);
        BUCKETED_MOB_TAGS.add(ThievesFish.TROPHY_TAG);
        BUCKETED_MOB_TAGS.add(ThievesFish.HAS_FED_TAG);
        BUCKETED_MOB_TAGS.add(ThievesFish.NO_FLIP_TAG);
    }
}