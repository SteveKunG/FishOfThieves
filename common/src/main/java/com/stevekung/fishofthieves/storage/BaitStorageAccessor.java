package com.stevekung.fishofthieves.storage;

public interface BaitStorageAccessor
{
    default BaitPreserveSavedData getBaitPreserve()
    {
        throw new AssertionError("Implemented via mixin");
    }
}