package dev.boooiil.historia.items.crafted;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;

public enum ItemType {
    ARMOR("armor"),
    CUSTOM("custom"),
    INGOT("ingot"),
    TOOL("tool"),
    WEAPON("weapon"),
    VANILLA("vanilla");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ItemType fromString(String type) {
        for (ItemType t : ItemType.values()) {
            if (t.getType().equalsIgnoreCase(type)) {
                return t;
            }
        }
        return VANILLA;
    }

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
