package dev.boooiil.historia.items.crafted;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;

/**
 * The BaseItem class is a superclass that is responsible for managing and
 * accessing items within the Historia plugin.
 * 
 * @see ItemStack
 */
public class BaseItem {

    /** The internal item type */
    protected ItemType itemType;
    /** The configured display name of the item */
    protected String displayName;
    /** The configured id of the item */
    protected String id;
    /** The validity of the item */
    protected boolean valid;

    /**
     * Constructor for the BaseItem class. This will hold a null item stack.
     */
    public BaseItem() {
    };

    /**
     * Constructor for the BaseItem class that takes an ItemStack as a parameter.
     *
     * @param itemStack The ItemStack to be set as the itemStack variable.
     */
    public BaseItem(@NotNull ItemStack itemStack) {

        this.itemStack = itemStack;

        if (this.itemStack.getItemMeta() == null) {
            this.valid = false;
            this.itemType = ItemType.VANILLA;
            return;
        }

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (!container.has(Main.getNamespacedKey("item-type"), PersistentDataType.STRING)) {
            this.valid = false;
            this.itemType = ItemType.VANILLA;
            return;
        }

        this.itemType = ItemType
                .fromString(container.get(Main.getNamespacedKey("item-type"), PersistentDataType.STRING));
        this.displayName = container.get(Main.getNamespacedKey("item-name"), PersistentDataType.STRING);
        this.id = container.get(Main.getNamespacedKey("item-id"), PersistentDataType.STRING);

    }

    /**
     * Constructor for the BaseItem class that takes an ItemStack and a boolean as
     * parameters.
     *
     * @param itemStack The ItemStack to be set as the itemStack variable.
     * @param valid     The boolean to be set as the valid variable.
     */
    @Deprecated(forRemoval = true)
    public BaseItem(@NotNull ItemStack itemStack, boolean valid) {

        this.itemStack = itemStack;
        this.valid = valid;

    }

    /** The item stack relating to this item */
    protected ItemStack itemStack;

    /**
     * It returns the item stack
     *
     * @return The itemStack variable.
     */
    public ItemStack getItemStack() {

        return itemStack;

    }

    /**
     * It returns the display name of the item.
     *
     * @return The display name of the item.
     */
    public String getDisplayName() {

        return this.displayName;

    }

    /**
     * It returns if the item is valid.
     *
     * @return If the item is valid.
     */
    public boolean isValid() {

        return this.valid;

    }

    /**
     * It returns the type of the item.
     *
     * @return The type of the item.
     */
    public ItemType getItemType() {

        return this.itemType;

    }

    /**
     * It returns the id of the item.
     *
     * @return The id of the item.
     */
    public String getID() {

        return this.id;

    }
}
