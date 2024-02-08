package dev.boooiil.historia.items.crafted;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.util.Construct;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.crafted.modifiers.Weight;

public class Armor extends BaseItem {

    private double defense;

    private int durability;
    private int weightValue;

    private Weight weight;

    public Armor(ItemStack item) {
        super(item);

        if (!item.hasItemMeta()) {
            this.valid = false;
            return;
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (!container.has(Main.getNamespacedKey("armor-defense"))) {
            this.valid = false;
            return;
        }

        this.valid = true;

        this.defense = container.get(Main.getNamespacedKey("armor-defense"), PersistentDataType.DOUBLE);

        this.durability = container.get(Main.getNamespacedKey("armor-durability"), PersistentDataType.INTEGER);
        this.weightValue = container.get(Main.getNamespacedKey("armor-weight-value"), PersistentDataType.INTEGER);

        this.weight = Weight.getWeight(container.get(Main.getNamespacedKey("armor-weight"), PersistentDataType.STRING));

    }

    public Armor(Material material, String displayName, double defense, int durability, Weight weight,
            int weightValue, List<String> lore) {

        this.valid = true;

        this.defense = defense;

        this.durability = durability;
        this.weightValue = weightValue;

        this.weight = weight;

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);

        ItemMeta meta = Main.server().getItemFactory().getItemMeta(material);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("armor-defense"), PersistentDataType.DOUBLE, defense);
        container.set(Main.getNamespacedKey("armor-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("armor-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("armor-weight"), PersistentDataType.STRING, weight.getKey());

        itemStack.setItemMeta(meta);
    }

    public double getDefense() {
        return this.defense;
    }

    public int getDurability() {
        return this.durability;
    }

    public int getWeightValue() {
        return this.weightValue;
    }

    public Weight getWeight() {
        return this.weight;
    }

}
