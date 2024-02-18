package dev.boooiil.historia.items.crafted.tool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.items.ToolConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;
import net.kyori.adventure.text.Component;

public class Tool extends BaseItem {

    private double damage;
    private double speed;
    private double knockback;

    private int durability;
    private int weightValue;

    private Weight weight;

    public Tool(@NotNull ItemStack item) {
        super(item);

        if (getItemType() != ItemType.TOOL) {
            return;
        } else
            this.valid = true;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        this.damage = container.get(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE);
        this.speed = container.get(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE);
        this.knockback = container.get(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE);

        this.durability = container.get(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER);
        this.weightValue = container.get(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER);

        this.weight = Weight.getWeight(container.get(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING));

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
        this.displayName = displayName;
        this.itemType = ItemType.TOOL;

        this.itemStack = Construct.itemStack(material, 1, displayName, new ArrayList<>(lore));

        ItemMeta meta = this.itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("tool-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("tool-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("tool-knockback"), PersistentDataType.DOUBLE, knockback);

        container.set(Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("tool-weight-value"), PersistentDataType.INTEGER, weightValue);

        container.set(Main.getNamespacedKey("tool-weight"), PersistentDataType.STRING, weight.getKey());

        itemStack.setItemMeta(meta);
    }

    public Tool(ToolConfiguration configuration) {

        Material material = configuration.getMaterial();
        String displayName = configuration.getDisplayName();
        List<Component> lore = new ArrayList<>();

        for (String s : configuration.getLore()) {
            lore.add(Component.text(s));
        }

        this.valid = true;
        this.displayName = configuration.getDisplayName();
        this.itemType = ItemType.TOOL;

        this.damage = configuration.getDamageRandomValue();
        this.speed = configuration.getSpeedRandomValue();
        this.knockback = configuration.getKnockbackRandomValue();

        this.durability = configuration.getRandomDurabilityValue();
        this.weightValue = configuration.getWeightValue();
        this.weight = configuration.getWeight();

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);

        ItemMeta meta = this.itemStack.getItemMeta();
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
