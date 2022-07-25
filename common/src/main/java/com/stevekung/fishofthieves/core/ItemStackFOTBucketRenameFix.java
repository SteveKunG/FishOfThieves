/*
 * Decompiled with CFR 0.1.1 (FabricMC 57d88659).
 */
package com.stevekung.fishofthieves.core;

import java.util.Optional;

import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.datafix.fixes.References;

public class ItemStackFOTBucketRenameFix extends DataFix
{
    private static final Object2ObjectMap<Integer, String> MAP = DataFixUtils.make(new Object2ObjectOpenHashMap<>(), int2ObjectOpenHashMap ->
    {
        int2ObjectOpenHashMap.put(0, "minecraft:protection");
        int2ObjectOpenHashMap.put(1, "minecraft:fire_protection");
        int2ObjectOpenHashMap.put(2, "minecraft:feather_falling");
        int2ObjectOpenHashMap.put(3, "minecraft:blast_protection");
        int2ObjectOpenHashMap.put(4, "minecraft:projectile_protection");
        int2ObjectOpenHashMap.put(5, "minecraft:respiration");
    });
    private static final Object2ObjectMap<Test, String> MAP2 = DataFixUtils.make(new Object2ObjectOpenHashMap<>(), int2ObjectOpenHashMap ->
    {
        int2ObjectOpenHashMap.put(new Test("ruby", 0), "fishofthieves:ruby");
        int2ObjectOpenHashMap.put(new Test("sunny", 1), "fishofthieves:sunny");
        int2ObjectOpenHashMap.put(new Test("indigo", 2), "fishofthieves:indigo");
        int2ObjectOpenHashMap.put(new Test("umber", 3), "fishofthieves:umber");
        int2ObjectOpenHashMap.put(new Test("seafoam", 4), "fishofthieves:seafoam");
    });

    record Test(String name, int id)
    {

    }

    public ItemStackFOTBucketRenameFix(Schema schema, boolean bl)
    {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule()
    {
        Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemStackFOTBucketRenameFix", type, typed2 -> typed2.updateTyped(opticFinder, typed -> typed.update(DSL.remainderFinder(), this::fixTag)));
    }

    private Dynamic<?> fixTag(Dynamic<?> dynamic2)
    {
        Optional<? extends Dynamic<?>> optional = dynamic2.get("Variant").asStreamOpt().map(stream -> stream.map(dynamic -> {

//            MAP2.getOrDefault(new Test(dynamic.get("Name").asString("ruby"), dynamic.get("variant").asInt(0)), "fishofthieves:ruby");


            return dynamic.set("variant", dynamic.createString(MAP2.getOrDefault(new Test(dynamic.get("Name").asString("ruby"), dynamic.get("variant").asInt(0)), "fishofthieves:ruby")));
//            return dynamic.set("variant", dynamic.createString(MAP.getOrDefault(dynamic.get("variant").asInt(0), "fishofthieves:ruby")));

        })).map(dynamic2::createList).result();

        if (optional.isPresent())
        {
            dynamic2 = dynamic2.remove("Variant").set("variant", optional.get());
        }
        return dynamic2;
    }
}

