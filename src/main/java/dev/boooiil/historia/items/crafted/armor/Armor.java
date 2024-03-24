package dev.boooiil.historia.items.crafted.armor;

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

import net.kyori.adventure.text.Component;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;

/**
 * <p>
 * The Armor class is a subclass of the BaseItem class and is responsible for
 * managing and accessing armor items within the Historia plugin.
 * </p>
 * <p>
 * Armor provides methods to retrieve armor-specific attributes, such as
 * defense,
 * durability, and weight, and to create armor items with randomized defense and
 * durability values.
 * </p>
 * 
 * @see BaseItem
 */
public class Armor extends BaseItem {

    private double defense;
    private int durability;
    private int weightValue;
    private Weight weight;

    /**
     * Constructs an Armor object from an existing ItemStack.
     *
     * @param item The ItemStack representing the armor.
     */
    public Armor(@NotNull ItemStack item) {
        super(item);

        if (getItemType() != ItemType.ARMOR) {
            return;
        } else
            this.valid = true;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        this.defense = container.get(Main.getNamespacedKey("item-defense"), PersistentDataType.DOUBLE);
        this.durability = container.get(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER);
        this.weightValue = container.get(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER);
        this.weight = Weight.getWeight(container.get(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING));
    }

    /**
     * Constructs an Armor object with specified attributes.
     *
     * @param material    The material of the armor.
     * @param displayName The display name of the armor.
     * @param defense     The defense value of the armor.
     * @param durability  The durability of the armor.
     * @param weight      The weight class of the armor.
     * @param weightValue The weight value of the armor.
     * @param lore        The lore of the armor.
     */
    public Armor(Material material, String id, String displayName, double defense, int durability, Weight weight,
            int weightValue, List<String> lore) {
        this.valid = true;
        this.id = id;
        this.defense = defense;
        this.durability = durability;
        this.weightValue = weightValue;
        this.weight = weight;
        this.displayName = displayName;
        this.itemType = ItemType.ARMOR;

        this.itemStack = Construct.itemStack(material, 1, displayName, new ArrayList<>(lore));

        ItemMeta meta = this.itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, this.itemType.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);
        container.set(Main.getNamespacedKey("item-defense"), PersistentDataType.DOUBLE, defense);
        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, weight.getKey());

        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", defense,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);

        itemStack.setItemMeta(meta);
    }

    /**
     * Constructs an Armor object from an ArmorConfiguration.
     *
     * @param configuration The configuration defining the armor.
     */
    public Armor(ArmorConfiguration configuration) {
        Material material = configuration.getMaterial();
        String displayName = configuration.getDisplayName();
        List<Component> lore = new ArrayList<>();

        for (String s : configuration.getLore()) {
            lore.add(Component.text(s));
        }

        this.id = configuration.getID();
        this.defense = configuration.getRandomDefenseValue();
        this.durability = configuration.getRandomDurabilityValue();
        this.weight = configuration.getWeight();
        this.weightValue = configuration.getWeightValue();
        this.displayName = configuration.getDisplayName();
        this.itemType = ItemType.ARMOR;

        this.valid = true;

        this.itemStack = Construct.itemStack(material, 1, displayName, lore);

        ItemMeta meta = this.itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, this.itemType.getType());
        container.set(Main.getNamespacedKey("item-id"), PersistentDataType.STRING, id);
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, displayName);
        container.set(Main.getNamespacedKey("item-defense"), PersistentDataType.DOUBLE, defense);
        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, durability);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, weightValue);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, weight.getKey());

        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", defense,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);

        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);

        itemStack.setItemMeta(meta);
    }

    /**
     * Get the defense value of the armor.
     *
     * @return The defense value.
     */
    public double getDefense() {
        return this.defense;
    }

    /**
     * Get the durability of the armor.
     *
     * @return The durability value.
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Get the weight value of the armor.
     *
     * @return The weight value.
     */
    public int getWeightValue() {
        return this.weightValue;
    }

    /**
     * Get the weight class of the armor.
     *
     * @return The weight class.
     */
    public Weight getWeight() {
        return this.weight;
    }
}
