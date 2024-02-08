package dev.boooiil.historia.items.items;

import org.bukkit.inventory.ItemStack;

public abstract class BaseItem {

    protected String name;
    protected boolean valid;

    public BaseItem() {
    };

    /**
     * Constructor for the BaseItem class that takes an ItemStack as a parameter.
     *
     * @param itemStack The ItemStack to be set as the itemStack variable.
     */
    public BaseItem(ItemStack itemStack) {

        this.itemStack = itemStack;

    }

    public BaseItem(ItemStack itemStack, boolean valid) {

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

    public String getName() {

        return this.name;

    }

    public boolean isValid() {

        return this.valid;

    }
}
