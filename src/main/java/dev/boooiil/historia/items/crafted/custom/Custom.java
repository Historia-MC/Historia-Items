package dev.boooiil.historia.items.crafted.custom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.util.Construct;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.crafted.custom.CustomConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;

/**
 * <p>
 * The Custom class is a subclass of the BaseItem
 * and is responsible for managing and accessing custom items within the
 * Historia plugin.
 * </p>
 * <p>
 * Custom provides methods to retrieve custom-specific attributes, such as
 * recipe items, recipe shape, and proficiencies, and to create custom items
 * with the specified recipe items and shape.
 * </p>
 * 
 * @see BaseItem
 */
public class Custom extends BaseItem {

    /**
     * Constructs a Custom object from an existing ItemStack.
     *
     * @param itemStack The ItemStack representing the custom item.
     */
    public Custom(@NotNull ItemStack itemStack) {
        super(itemStack);

        if (getItemType() != ItemType.CUSTOM) {
            return;
        } else
            this.valid = true;
    }

    /**
     * Constructs a Custom object from the specified material, amount, id, display
     * name, and lore.
     *
     * @param material    The material of the custom item.
     * @param amount      The amount of the custom item.
     * @param id          The id of the custom item.
     * @param displayName The display name of the custom item.
     * @param lore        The lore of the custom item.
     */
    public Custom(Material material, int amount, String id, String displayName, List<String> lore) {

        this.valid = true;
        this.id = id;
        this.displayName = displayName;
        this.itemType = ItemType.CUSTOM;

        this.itemStack = Construct.itemStack(material, amount, displayName, new ArrayList<>(lore));

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, this.itemType.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        this.itemStack.setItemMeta(itemMeta);

    }

    /**
     * Constructs a Custom object from the specified configuration.
     *
     * @param configuration The configuration of the custom item.
     */
    public Custom(@NotNull CustomConfiguration configuration) {

        this.valid = true;
        this.id = configuration.getID();
        this.displayName = configuration.getDisplayName();
        this.itemType = ItemType.CUSTOM;

        Material material = configuration.getMaterial();
        int amount = configuration.getAmount();
        List<String> lore = configuration.getLore();

        this.itemStack = Construct.itemStack(material, amount, displayName, new ArrayList<>(lore));

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        this.itemStack.setItemMeta(itemMeta);

    }

}
