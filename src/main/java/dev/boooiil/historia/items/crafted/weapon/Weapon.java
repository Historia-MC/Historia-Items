package dev.boooiil.historia.items.crafted.weapon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;
import net.kyori.adventure.text.Component;

public class Weapon extends BaseItem {

    private double damage;
    private double speed;
    private double knockback;
    private double sweeping;

    private int durability;
    private int weightValue;

    private Weight weight;

    public Weapon(@NotNull ItemStack item) {
        super(item);

        if (getItemType() != ItemType.WEAPON) {
            return;
        } else
            this.valid = true;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        this.damage = container.get(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE);
        this.speed = container.get(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE);
        this.knockback = container.get(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE);
        this.sweeping = container.get(Main.getNamespacedKey("item-sweeping"), PersistentDataType.DOUBLE);

        this.durability = container.get(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER);
        this.weightValue = container.get(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER);

        this.weight = Weight
                .getWeight(container.get(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING));

    }

    public Weapon(Material material, String displayName, double damage, double speed, double knockback, double sweeping,
            int durability, Weight weight, int weightValue, List<String> lore) {

        this.valid = true;
        this.itemType = ItemType.WEAPON;
        this.displayName = displayName;

        this.damage = damage;
        this.speed = speed;
        this.knockback = knockback;
        this.sweeping = sweeping;

        this.durability = durability;
        this.weightValue = weightValue;

        this.weight = weight;

        this.itemStack = Construct.itemStack(material, 1, displayName, new ArrayList<>(lore));

        ItemMeta meta = Main.server().getItemFactory().getItemMeta(material);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, knockback);
        container.set(Main.getNamespacedKey("item-sweeping"), PersistentDataType.DOUBLE, sweeping);

        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, weight.getKey());

        itemStack.setItemMeta(meta);

    }

    public Weapon(WeaponConfiguration configuration) {
        this.valid = true;
        this.itemType = ItemType.WEAPON;

        Material material = configuration.getMaterial();
        this.displayName = configuration.getDisplayName();
        List<Component> lore = new ArrayList<>();

        for (String s : configuration.getLore()) {
            lore.add(Component.text(s));
        }

        this.damage = configuration.getDamageRandomValue();
        this.speed = configuration.getSpeedRandomValue();
        this.knockback = configuration.getKnockbackRandomValue();
        this.sweeping = configuration.getSpeedRandomValue();

        this.durability = configuration.getRandomDurabilityValue();
        this.weightValue = configuration.getWeightValue();

        this.weight = configuration.getWeight();

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);

        ItemMeta meta = Main.server().getItemFactory().getItemMeta(material);
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, knockback);
        container.set(Main.getNamespacedKey("item-sweeping"), PersistentDataType.DOUBLE, sweeping);

        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, weight.getKey());

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
