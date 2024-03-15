package dev.boooiil.historia.items.crafted;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a crafted item.
 *
 * @param <T> The type of the crafted item.
 */
interface CraftedItemInterface<T> {

    /**
     * Create a new crafted item.
     *
     * @param itemStack The ItemStack to be set as the itemStack variable.
     * @return The crafted item.
     */
    T create(@NotNull ItemStack itemStack);

    /**
     * Get the ItemStack of the crafted item.
     *
     * @return The ItemStack of the crafted item.
     */
    ItemStack getItemStack();

    /**
     * Get the display name of the crafted item.
     *
     * @return The display name of the crafted item.
     */
    String getDisplayName();

    /**
     * Get if the crafted item is valid.
     * 
     * @return If the crafted item is valid.
     */
    boolean isValid();

    /**
     * Get the type of the crafted item.
     *
     * @return The type of the crafted item.
     */
    ItemType getItemType();

    /**
     * Get the ID of the crafted item.
     *
     * @return The ID of the crafted item.
     */
    String getID();
}
