package dev.boooiil.historia.items.crafted;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;

/**
 * An enum representing the type of an item.
 */
public enum ItemType {
    ARMOR("armor"),
    CUSTOM("custom"),
    INGOT("ingot"),
    TOOL("tool"),
    WEAPON("weapon"),
    VANILLA("vanilla");

    private final String type;

    /**
     * Constructs an ItemType enum with a type.
     *
     * @param type The type of the item.
     */
    ItemType(String type) {
        this.type = type;
    }

    /**
     * Get the type of the item.
     *
     * @return The type of the item.
     */
    public String getType() {
        return type;
    }

    /**
     * Get the item type from a string.
     *
     * @return The name of the item type.
     */
    public static ItemType fromString(String type) {
        for (ItemType t : ItemType.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        return VANILLA;
    }

    /**
     * Get the item type from an ItemStack.
     *
     * @param itemStack The ItemStack to get the item type of.
     * @return The item type of the ItemStack.
     */
    public static ItemType fromItemStack(ItemStack itemStack) {

        if (itemStack.getItemMeta() == null) {
            return VANILLA;
        }

        if (!itemStack.getItemMeta().getPersistentDataContainer()
                .has(Main.getNamespacedKey("item-type"), PersistentDataType.STRING)) {
            return VANILLA;
        }

        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        String itemType = container.get(Main.getNamespacedKey("item-type"),
                PersistentDataType.STRING);
        return fromString(itemType);
    }
}
