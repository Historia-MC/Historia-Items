package dev.boooiil.historia.items.item;

import org.bukkit.inventory.ItemStack;

import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.core.util.JSONSerializable;

@NullMarked
public interface ItemData extends JSONSerializable {
    void apply(ItemStack stack);

}
