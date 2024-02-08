package dev.boooiil.historia.items.crafted.tool;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;

public class Tool extends BaseItem {

    private double damage;
    private double speed;
    private double knockback;

    private int durability;
    private int weightValue;

    private Weight weight;

    public Tool(ItemStack item) {
        super(item);

        if (!item.hasItemMeta()) {
            this.valid = false;
            return;
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (!container.has(Main.getNamespacedKey("tool-damage"))) {
            this.valid = false;
            return;
        }

        this.valid = true;

        this.damage = container.get(Main.getNamespacedKey("tool-damage"), PersistentDataType.DOUBLE);
        this.speed = container.get(Main.getNamespacedKey("tool-speed"), PersistentDataType.DOUBLE);
        this.knockback = container.get(Main.getNamespacedKey("tool-knockback"), PersistentDataType.DOUBLE);

        this.durability = container.get(Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER);
        this.weightValue = container.get(Main.getNamespacedKey("tool-weight-value"), PersistentDataType.INTEGER);

        this.weight = Weight.getWeight(container.get(Main.getNamespacedKey("tool-weight"), PersistentDataType.STRING));

    }

    public Tool(Material material, String displayName, double damage, double speed, double knockback, int durability,
            Weight weight, int weightValue, List<String> lore) {

        this.valid = true;

        this.damage = damage;
        this.speed = speed;
        this.knockback = knockback;

        this.durability = durability;
        this.weightValue = weightValue;

        this.weight = weight;

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);

        ItemMeta meta = Main.server().getItemFactory().getItemMeta(material);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("tool-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("tool-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("tool-knockback"), PersistentDataType.DOUBLE, knockback);

        container.set(Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("tool-weight-value"), PersistentDataType.INTEGER, weightValue);

        container.set(Main.getNamespacedKey("tool-weight"), PersistentDataType.STRING, weight.getKey());

        itemStack.setItemMeta(meta);
    }

    public double getDamage() {
        return this.damage;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getKnockback() {
        return this.knockback;
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
