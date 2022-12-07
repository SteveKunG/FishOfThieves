package com.stevekung.fishofthieves.mixin.datafix;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.AdvancementsRenameFix;

@Mixin(DataFixers.class)
public class MixinDataFixers
{
    @Shadow
    @Final
    static BiFunction<Integer, Schema, Schema> SAME_NAMESPACED;

    @Shadow
    static UnaryOperator<String> createRenamer(Map<String, String> renameMap)
    {
        throw new AssertionError();
    }

    @Inject(method = "addFixers", at = @At("TAIL"))
    private static void fishofthieves$addFixers(DataFixerBuilder builder, CallbackInfo info)
    {
        var schema = builder.addSchema(3210, SAME_NAMESPACED);

        //@formatter:off
        builder.addFixer(new AdvancementsRenameFix(schema, false, "Rename FOT recipe advancements", createRenamer(ImmutableMap.<String, String>builder()
                .put("fishofthieves:recipes/fishofthieves.main/cooked_splashtail", "fishofthieves:recipes/food/cooked_splashtail")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_pondie", "fishofthieves:recipes/food/cooked_pondie")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_islehopper", "fishofthieves:recipes/food/cooked_islehopper")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_plentifin", "fishofthieves:recipes/food/cooked_plentifin")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_ancientscale", "fishofthieves:recipes/food/cooked_ancientscale")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_wildsplash", "fishofthieves:recipes/food/cooked_wildsplash")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_devilfish", "fishofthieves:recipes/food/cooked_devilfish")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_battlegill", "fishofthieves:recipes/food/cooked_battlegill")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_wrecker", "fishofthieves:recipes/food/cooked_wrecker")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_stormfish", "fishofthieves:recipes/food/cooked_stormfish")

                .put("fishofthieves:recipes/fishofthieves.main/cooked_splashtail_from_smoking", "fishofthieves:recipes/food/cooked_splashtail_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_pondie_from_smoking", "fishofthieves:recipes/food/cooked_pondie_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_islehopper_from_smoking", "fishofthieves:recipes/food/cooked_islehopper_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_plentifin_from_smoking", "fishofthieves:recipes/food/cooked_plentifin_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_ancientscale_from_smoking", "fishofthieves:recipes/food/cooked_ancientscale_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_wildsplash_from_smoking", "fishofthieves:recipes/food/cooked_wildsplash_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_devilfish_from_smoking", "fishofthieves:recipes/food/cooked_devilfish_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_battlegill_from_smoking", "fishofthieves:recipes/food/cooked_battlegill_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_wrecker_from_smoking", "fishofthieves:recipes/food/cooked_wrecker_from_smoking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_stormfish_from_smoking", "fishofthieves:recipes/food/cooked_stormfish_from_smoking")

                .put("fishofthieves:recipes/fishofthieves.main/cooked_splashtail_from_campfire_cooking", "fishofthieves:recipes/food/cooked_splashtail_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_pondie_from_campfire_cooking", "fishofthieves:recipes/food/cooked_pondie_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_islehopper_from_campfire_cooking", "fishofthieves:recipes/food/cooked_islehopper_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_plentifin_from_campfire_cooking", "fishofthieves:recipes/food/cooked_plentifin_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_ancientscale_from_campfire_cooking", "fishofthieves:recipes/food/cooked_ancientscale_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_wildsplash_from_campfire_cooking", "fishofthieves:recipes/food/cooked_wildsplash_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_devilfish_from_campfire_cooking", "fishofthieves:recipes/food/cooked_devilfish_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_battlegill_from_campfire_cooking", "fishofthieves:recipes/food/cooked_battlegill_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_wrecker_from_campfire_cooking", "fishofthieves:recipes/food/cooked_wrecker_from_campfire_cooking")
                .put("fishofthieves:recipes/fishofthieves.main/cooked_stormfish_from_campfire_cooking", "fishofthieves:recipes/food/cooked_stormfish_from_campfire_cooking")
                .build())));
        //@formatter:on
    }
}