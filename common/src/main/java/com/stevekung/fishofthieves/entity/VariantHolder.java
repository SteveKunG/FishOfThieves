package com.stevekung.fishofthieves.entity;

public interface VariantHolder<T>
{
    void setVariant(T variant);

    T getVariant();
}