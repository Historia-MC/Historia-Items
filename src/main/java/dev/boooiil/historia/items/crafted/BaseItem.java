package dev.boooiil.historia.items.crafted;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BaseItem {

    protected String displayName;
    protected boolean valid;

    public BaseItem() {
    };

    /**
     * Constructor for the BaseItem class that takes an ItemStack as a parameter.
     *
     * @param itemStack The ItemStack to be set as the itemStack variable.
     */
    public BaseItem(@NotNull ItemStack itemStack) {

        this.itemStack = itemStack;

    }

    public BaseItem(@NotNull ItemStack itemStack, boolean valid) {

        this.itemStack = itemStack;
        this.valid = valid;

    }

    protected ItemStack itemStack;

    /**
     * It returns the item stack
     *
     * @return The itemStack variable.
     */
    public ItemStack getItemStack() {

        return itemStack;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public boolean isValid() {

        return this.valid;

    }
}
