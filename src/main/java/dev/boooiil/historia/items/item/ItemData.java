package dev.boooiil.historia.items.item;

import org.bukkit.inventory.ItemStack;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ItemData {
    void apply(ItemStack stack);
}
