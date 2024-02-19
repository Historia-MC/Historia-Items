package dev.boooiil.historia.items.crafted;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;

public class BaseItem {

    protected ItemType itemType;
    protected String displayName;
    protected String id;
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

    @Deprecated(forRemoval = true)
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

    public ItemType getItemType() {

        return this.itemType;

    }

    public String getID() {

        return this.id;

    }
}
