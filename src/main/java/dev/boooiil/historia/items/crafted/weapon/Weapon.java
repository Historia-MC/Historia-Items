package dev.boooiil.historia.items.crafted.weapon;

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

public class Weapon extends BaseItem {

    private double damage;
    private double speed;
    private double knockback;
    private double sweeping;

    private int durability;
    private int weightValue;

    private Weight weight;

    public Weapon(ItemStack item) {
        super(item);

        if (!item.hasItemMeta()) {
            this.valid = false;
            return;
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (!container.has(Main.getNamespacedKey("weapon-damage"))) {
            this.valid = false;
            return;
        }

        this.valid = true;

        this.damage = container.get(Main.getNamespacedKey("weapon-damage"), PersistentDataType.DOUBLE);
        this.speed = container.get(Main.getNamespacedKey("weapon-speed"), PersistentDataType.DOUBLE);
        this.knockback = container.get(Main.getNamespacedKey("weapon-knockback"), PersistentDataType.DOUBLE);
        this.sweeping = container.get(Main.getNamespacedKey("weapon-sweeping"), PersistentDataType.DOUBLE);

        this.durability = container.get(Main.getNamespacedKey("weapon-durability"), PersistentDataType.INTEGER);
        this.weightValue = container.get(Main.getNamespacedKey("weapon-weight-value"), PersistentDataType.INTEGER);

        this.weight = Weight
                .getWeight(container.get(Main.getNamespacedKey("weapon-weight"), PersistentDataType.STRING));

    }

    public Weapon(Material material, String displayName, double damage, double speed, double knockback, double sweeping,
            int durability, Weight weight, int weightValue, List<String> lore) {

        this.valid = true;

        this.damage = damage;
        this.speed = speed;
        this.knockback = knockback;
        this.sweeping = sweeping;

        this.durability = durability;
        this.weightValue = weightValue;

        this.weight = weight;

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);
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

    public double getSweeping() {
        return this.sweeping;
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
