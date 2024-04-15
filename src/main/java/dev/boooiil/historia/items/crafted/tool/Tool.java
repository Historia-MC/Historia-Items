package dev.boooiil.historia.items.crafted.tool;

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
import dev.boooiil.historia.items.configuration.crafted.tool.ToolConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;
import net.kyori.adventure.text.Component;

/**
 * <p>
 * The Tool class is a subclass of the BaseItem class and is responsible for
 * managing and accessing tool items within the Historia plugin.
 * </p>
 * <p>
 * Tool provides methods to retrieve tool-specific attributes, such as damage,
 * speed, knockback, durability, and weight, and to create tool items with
 * randomized damage, speed, knockback, and durability values.
 * </p>
 * 
 * @see BaseItem
 */
public class Tool extends BaseItem {

    private double damage;
    private double speed;
    private double knockback;

    private int durability;
    private int weightValue;

    private Weight weight;

    /**
     * Constructs a Tool object from an existing ItemStack.
     *
     * @param item The ItemStack representing the tool.
     */
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

    /**
     * Constructs a Tool object with specified attributes.
     *
     * @param material    The material of the tool.
     * @param id          The id of the tool.
     * @param displayName The display name of the tool.
     * @param damage      The damage value of the tool.
     * @param speed       The speed value of the tool.
     * @param knockback   The knockback value of the tool.
     * @param durability  The durability of the tool.
     * @param weight      The weight class of the tool.
     * @param weightValue The weight value of the tool.
     * @param lore        The lore of the tool.
     */
    public Tool(Material material, String id, String displayName, double damage, double speed, double knockback,
            int durability,
            Weight weight, int weightValue, List<String> lore) {

        this.valid = true;
        this.id = id;
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

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, damage);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, speed);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, knockback);

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
     * Constructs a Tool object from the specified configuration.
     *
     * @param configuration The configuration of the tool.
     */
    public Tool(ToolConfiguration configuration) {

        Material material = configuration.getMaterial();
        String displayName = configuration.getDisplayName();
        List<Component> lore = new ArrayList<>();

        for (String s : configuration.getLore()) {
            lore.add(Component.text(s));
        }

        this.valid = true;
        this.id = configuration.getID();
        this.displayName = configuration.getDisplayName();
        this.itemType = ItemType.TOOL;

        this.damage = configuration.getRandomDamageValue();
        this.speed = configuration.getRandomSpeedValue();
        this.knockback = configuration.getRandomKnockbackValue();

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
     * This function returns the damage value of the tool.
     * 
     * @return The damage value of the tool.
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * This function returns the speed value of the tool.
     * 
     * @return The speed value of the tool.
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * This function returns the knockback value of the tool.
     * 
     * @return The knockback value of the tool.
     */
    public double getKnockback() {
        return this.knockback;
    }

    /**
     * This function returns the durability value of the tool.
     * 
     * @return The durability value of the tool.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * This function returns the weight value of the tool.
     * 
     * @return The weight value of the tool.
     */
    public int getWeightValue() {
        return this.weightValue;
    }

    /**
     * This function returns the weight class of the tool.
     * 
     * @return The weight class of the tool.
     */
    public Weight getWeight() {
        return this.weight;
    }
}
