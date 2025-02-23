package dev.boooiil.historia.items.item;

import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.core.util.JSONSerializable;

@NullMarked
public interface ItemComponent extends JSONSerializable {
    ItemData data();

    ItemData data(float qualityModifier);

    String getKey();
}