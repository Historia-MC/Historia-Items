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
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;

public class Custom extends BaseItem {

    public Custom(@NotNull ItemStack itemStack) {
        super(itemStack);

        if (getItemType() != ItemType.CUSTOM) {
            return;
        } else
            this.valid = true;
    }

    public Custom(Material material, int amount, String displayName, List<String> lore) {

        this.valid = true;
        this.displayName = displayName;
        this.itemType = ItemType.CUSTOM;

        this.itemStack = Construct.itemStack(material, amount, displayName, new ArrayList<>(lore));

        ItemMeta itemMeta = Main.server().getItemFactory().getItemMeta(material);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, this.itemType.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        this.itemStack.setItemMeta(itemMeta);

    }

    public Custom(@NotNull CustomItemConfiguration configuration) {

        this.valid = true;
        this.displayName = configuration.getDisplayName();
        this.itemType = ItemType.CUSTOM;

        Material material = configuration.getMaterial();
        int amount = configuration.getAmount();
        List<String> lore = configuration.getLore();

        this.itemStack = Construct.itemStack(material, amount, displayName, new ArrayList<>(lore));

        ItemMeta itemMeta = Main.server().getItemFactory().getItemMeta(material);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, this.itemType.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        this.itemStack.setItemMeta(itemMeta);

    }

}
