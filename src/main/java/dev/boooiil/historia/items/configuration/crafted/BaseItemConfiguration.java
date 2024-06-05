package dev.boooiil.historia.items.configuration.crafted;

import dev.boooiil.historia.items.crafted.ItemType;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

/**
 * <p>
 * The BaseConfiguration class is an abstract class that serves as the base for
 * all item configuration classes within the Historia plugin.
 * </p>
 * <p>
 * BaseConfiguration provides methods to retrieve and validate recipe items and
 * shapes, to check if a given proficiency can craft an item, and to retrieve
 * the item stack of an item.
 * </p>
 */
public abstract class BaseItemConfiguration {

    // TODO: ALL items need to have an ID in the configuration file.
    // TODO: ADD an id field to each section.
    // TODO: set all members to private and add getters and setters.

    /** custom id of the item */
    protected String id;

    /** The material of the item. */
    protected Material material;

    /** The amount of the item. */
    protected int amount;

    /** The display name of the item. */
    protected String displayName;

    /** The type of the item. */
    protected ItemType itemType;

    /** The lore of the item. */
    protected List<String> lore;

    /** base item configuration default constructor */
    public BaseItemConfiguration() {
    }

    /**
     * It returns the material of the item.
     *
     * @return The material of the item.
     */
    public Material getMaterial() {

        return this.material;

    }

    /**
     * It returns the amount of the item.
     *
     * @return The amount of the item.
     */
    public int getAmount() {

        return this.amount;

    }

    /**
     * It returns the display name of the item.
     *
     * @return The display name of the item.
     */
    public String getDisplayName() {

        return this.displayName;

    }

    /**
     * It returns the type of the item.
     *
     * @return The type of the item.
     */
    public ItemType getItemType() {
        return this.itemType;
    }

    /**
     * It returns the lore of the item.
     *
     * @return The lore of the item.
     */
    public List<String> getLore() {

        return this.lore;

    }

    @Override
    public String toString() {
        return "Material: " + this.material + " Amount: " + this.amount + " Display Name: " + this.displayName
                + " Lore: " + this.lore.toString();
    }

    /**
     * It returns the ID of the item.
     *
     * @return The ID of the item.
     */
    public String getID() {
        return this.id;
    }

    /**
     * Create a built {@link BaseItemConfiguration} object from the configuration.
     * 
     * @param <T>     The type of the configuration that extends
     *                {@link BaseItemConfiguration}.
     * @param section The configuration section.
     * @return The built {@link BaseItemConfiguration} object.
     */
    public static <T extends BaseItemConfiguration> T fromConfigurationSection(ConfigurationSection section) {
        return null;
    };
}
