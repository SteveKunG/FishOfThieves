package com.stevekung.fishofthieves.datafixers;

import java.util.Map;
import java.util.function.Supplier;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;

import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class V3940 extends NamespacedSchema
{
    public V3940(int versionKey, Schema schema)
    {
        super(versionKey, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema)
    {
        var map = super.registerEntities(schema);
        schema.registerSimple(map, "fishofthieves:splashtail");
        schema.registerSimple(map, "fishofthieves:pondie");
        schema.registerSimple(map, "fishofthieves:islehopper");
        schema.registerSimple(map, "fishofthieves:ancientscale");
        schema.registerSimple(map, "fishofthieves:plentifin");
        schema.registerSimple(map, "fishofthieves:wildsplash");
        schema.registerSimple(map, "fishofthieves:devilfish");
        schema.registerSimple(map, "fishofthieves:battlegill");
        schema.registerSimple(map, "fishofthieves:wrecker");
        schema.registerSimple(map, "fishofthieves:stormfish");
        return map;
    }
}