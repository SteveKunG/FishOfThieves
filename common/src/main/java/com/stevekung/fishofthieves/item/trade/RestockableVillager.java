package com.stevekung.fishofthieves.item.trade;

import java.util.Set;

public interface RestockableVillager
{
    default Set<RestockableData> fishofthieves$getRestockableDataSet()
    {
        throw new AssertionError("Implemented via mixin");
    }
}