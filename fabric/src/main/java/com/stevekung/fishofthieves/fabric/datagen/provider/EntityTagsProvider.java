package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ArrayUtils;

import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;

public class EntityTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider
{
    // Immersive Weathering
    private static final TagKey<EntityType<?>> LIGHT_FREEZE_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("immersive_weathering", "light_freeze_immune"));

    public EntityTagsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        var neutralFishes = new EntityType<?>[] { FOTEntities.DEVILFISH, FOTEntities.BATTLEGILL, FOTEntities.WRECKER };
        var fishes = new EntityType<?>[] { FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ISLEHOPPER, FOTEntities.ANCIENTSCALE, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH, FOTEntities.STORMFISH };
        this.builder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(ArrayUtils.removeElements(fishes, neutralFishes));
        this.builder(EntityTypeTags.AQUATIC).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.CAN_BREATHE_UNDER_WATER).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.NOT_SCARY_FOR_PUFFERFISH).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.CANNOT_BE_PUSHED_ONTO_BOATS).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(EntityTypeTags.BOAT).add(FOTEntities.COCONUT_BOAT);
        this.builder(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).add(ArrayUtils.addAll(fishes, neutralFishes));
        this.builder(FOTTags.EntityTypes.FISH_BONE_DROP).add(EntityTypes.COD, EntityTypes.SALMON, EntityTypes.TROPICAL_FISH);
        this.builder(FOTTags.EntityTypes.FISH_PLAQUE_HORIZONTAL_RENDER).add(EntityTypes.PUFFERFISH, EntityTypes.TADPOLE, EntityTypes.AXOLOTL);
        this.builder(FOTTags.EntityTypes.FISH_PLAQUE_HORIZONTAL_RENDER_ON_POWERED)
                .add(EntityTypes.COD, EntityTypes.SALMON, EntityTypes.TROPICAL_FISH, EntityTypes.AXOLOTL)
                .forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.builder(FOTTags.EntityTypes.BATTLEGILL_ATTACKABLE).add(EntityTypes.DROWNED, EntityTypes.ZOMBIE, EntityTypes.SKELETON, EntityTypes.GUARDIAN, EntityTypes.ELDER_GUARDIAN, EntityTypes.PILLAGER);
        this.builder(FOTTags.EntityTypes.DEVILFISH_ATTACKABLE).add(EntityTypes.DROWNED, EntityTypes.ZOMBIE, EntityTypes.SKELETON, EntityTypes.GUARDIAN, EntityTypes.ELDER_GUARDIAN);
        this.builder(FOTTags.EntityTypes.WRECKER_ATTACKABLE).add(EntityTypes.PLAYER, EntityTypes.DROWNED, EntityTypes.ZOMBIE, EntityTypes.SKELETON, EntityTypes.GUARDIAN, EntityTypes.ELDER_GUARDIAN);

        // Immersive Weathering compatibility
        this.builder(LIGHT_FREEZE_IMMUNE).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
    }
}