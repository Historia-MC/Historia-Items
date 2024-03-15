package dev.boooiil.historia.items.crafted.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;
import net.kyori.adventure.text.Component;

/**
 * <p>
 * The Weapon class is a subclass of the BaseItem class and is responsible for
 * managing and accessing weapon items within the Historia plugin.
 * </p>
 * <p>
 * Weapon provides methods to retrieve weapon-specific attributes, such as
 * damage, speed, knockback, sweeping, durability, and weight, and to create
 * weapon items with randomized damage, speed, knockback, and durability values.
 * </p>
 * 
 * @see BaseItem
 */
public class Weapon extends BaseItem {

    private double damage;
    private double speed;
    private double knockback;
    private double sweeping;

    private int durability;
    private int weightValue;

    private Weight weight;

    /**
     * Constructs a Weapon object from an existing ItemStack.
     *
     * @param item The ItemStack representing the weapon.
     */
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

    /**
     * Constructs a Weapon object with specified attributes.
     *
     * @param material    The material of the weapon.
     * @param id          The id of the weapon.
     * @param displayName The display name of the weapon.
     * @param damage      The damage value of the weapon.
     * @param speed       The speed value of the weapon.
     * @param knockback   The knockback value of the weapon.
     * @param sweeping    The sweeping value of the weapon.
     * @param durability  The durability of the weapon.
     * @param weight      The weight class of the weapon.
     * @param weightValue The weight value of the weapon.
     * @param lore        The lore of the weapon.
     */
    public Weapon(Material material, String id, String displayName, double damage, double speed, double knockback,
            double sweeping,
            int durability, Weight weight, int weightValue, List<String> lore) {

        this.valid = true;
        this.id = id;
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

        ItemMeta meta = this.itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, knockback);
        container.set(Main.getNamespacedKey("item-sweeping"), PersistentDataType.DOUBLE, sweeping);

        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, weight.getKey());

        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier speedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", speed,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier knockbackModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackKnockback",
                knockback, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, knockbackModifier);

        itemStack.setItemMeta(meta);

    }

    /**
     * Constructs a Weapon object from the specified configuration.
     *
     * @param configuration The configuration of the weapon.
     */
    public Weapon(WeaponConfiguration configuration) {
        this.valid = true;
        this.itemType = ItemType.WEAPON;
        this.id = configuration.getID();

        Material material = configuration.getMaterial();
        this.displayName = configuration.getDisplayName();
        List<Component> lore = new ArrayList<>();

        for (String s : configuration.getLore()) {
            lore.add(Component.text(s));
        }

        this.damage = configuration.getDamageRandomValue();
        this.speed = configuration.getSpeedRandomValue();
        this.knockback = configuration.getKnockbackRandomValue();
        this.sweeping = configuration.getSweepRandomValue();

        this.durability = configuration.getRandomDurabilityValue();
        this.weightValue = configuration.getWeightValue();

        this.weight = configuration.getWeight();

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);

        ItemMeta meta = this.itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, knockback);
        container.set(Main.getNamespacedKey("item-sweeping"), PersistentDataType.DOUBLE, sweeping);

        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, weight.getKey());

        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", damage,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier speedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", speed,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier knockbackModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackKnockback",
                knockback, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, knockbackModifier);

        itemStack.setItemMeta(meta);

    }

    /**
     * Get the damage value of the weapon.
     *
     * @return The damage value.
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * Get the speed value of the weapon.
     *
     * @return The speed value.
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Get the knockback value of the weapon.
     *
     * @return The knockback value.
     */
    public double getKnockback() {
        return this.knockback;
    }

    /**
     * Get the sweeping value of the weapon.
     *
     * @return The sweeping value.
     */
    public double getSweeping() {
        return this.sweeping;
    }

    /**
     * Get the durability of the weapon.
     *
     * @return The durability value.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Get the weight value of the weapon.
     *
     * @return The weight value.
     */
    public int getWeightValue() {
        return this.weightValue;
    }

    /**
     * Get the weight class of the weapon.
     *
     * @return The weight class.
     */
    public Weight getWeight() {
        return this.weight;
    }
}
