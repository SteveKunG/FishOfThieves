package com.stevekung.fishofthieves.fabric.datagen.provider;

import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.ArrayUtils;
import com.stevekung.fishofthieves.registry.FOTEntities;
import com.stevekung.fishofthieves.registry.FOTTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityTagsProvider extends FabricTagProvider.EntityTypeTagProvider
{
    // Immersive Weathering
    private static final TagKey<EntityType<?>> FREEZING_WATER_IMMUNE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("immersive_weathering", "freezing_water_immune"));

    public EntityTagsProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider)
    {
        super(dataOutput, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        var neutralFishes = new EntityType<?>[] { FOTEntities.DEVILFISH, FOTEntities.BATTLEGILL, FOTEntities.WRECKER };
        var fishes = new EntityType<?>[] { FOTEntities.SPLASHTAIL, FOTEntities.PONDIE, FOTEntities.ISLEHOPPER, FOTEntities.ANCIENTSCALE, FOTEntities.PLENTIFIN, FOTEntities.WILDSPLASH, FOTEntities.STORMFISH };
        this.getOrCreateTagBuilder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(ArrayUtils.removeElements(fishes, neutralFishes));
        this.getOrCreateTagBuilder(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE).add(ArrayUtils.addAll(fishes, neutralFishes));
        this.getOrCreateTagBuilder(FOTTags.EntityTypes.FISH_BONE_DROP).add(EntityType.COD, EntityType.SALMON, EntityType.TROPICAL_FISH);
        this.getOrCreateTagBuilder(FOTTags.EntityTypes.HORIZONTAL_MOB_RENDER).add(EntityType.PUFFERFISH, EntityType.TADPOLE, EntityType.AXOLOTL);
        this.getOrCreateTagBuilder(FOTTags.EntityTypes.BATTLEGILL_ATTACKABLE).add(EntityType.DROWNED, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.PILLAGER);
        this.getOrCreateTagBuilder(FOTTags.EntityTypes.DEVILFISH_ATTACKABLE).add(EntityType.DROWNED, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN);
        this.getOrCreateTagBuilder(FOTTags.EntityTypes.WRECKER_ATTACKABLE).add(EntityType.PLAYER, EntityType.DROWNED, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN);

        // Immersive Weathering compatibility
        this.getOrCreateTagBuilder(FREEZING_WATER_IMMUNE).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).forceAddTag(FOTTags.EntityTypes.THIEVES_FISH_ENTITY_TYPE);
    }
}