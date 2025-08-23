package com.stevekung.fishofthieves.datafixers;

import java.util.Map;
import java.util.function.Supplier;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;

import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;

public class V3940 extends NamespacedSchema
{
    public V3940(int versionKey, Schema schema)
    {
        super(versionKey, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema)
    {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        registerMob(schema, map, "fishofthieves:splashtail");
        registerMob(schema, map, "fishofthieves:pondie");
        registerMob(schema, map, "fishofthieves:islehopper");
        registerMob(schema, map, "fishofthieves:ancientscale");
        registerMob(schema, map, "fishofthieves:plentifin");
        registerMob(schema, map, "fishofthieves:wildsplash");
        registerMob(schema, map, "fishofthieves:devilfish");
        registerMob(schema, map, "fishofthieves:battlegill");
        registerMob(schema, map, "fishofthieves:wrecker");
        registerMob(schema, map, "fishofthieves:stormfish");
        return map;
    }

    private static void registerMob(Schema schema, Map<String, Supplier<TypeTemplate>> map, String name)
    {
        schema.register(map, name, () -> V100.equipment(schema));
    }
}