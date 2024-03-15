package dev.boooiil.historia.items.configuration.crafted.custom;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.crafted.ItemType;

/**
 * <p>
 * The CustomItemConfiguration class is a subclass of the BaseConfiguration
 * class
 * and is responsible for managing and accessing configuration data for custom
 * items within the Historia plugin.
 * </p>
 * <p>
 * CustomItemConfiguration provides methods to retrieve custom item-specific
 * configuration settings, such as recipe items, recipe shape, and
 * proficiencies,
 * and to create custom items with the specified recipe items and shape.
 * </p>
 * 
 * @see BaseItemConfiguration
 */
public class CustomConfiguration extends BaseItemConfiguration {

    /**
     * Create a built {@link CustomConfiguration} object from the configuration.
     * 
     * @param section The configuration section.
     */
    CustomConfiguration(ConfigurationSection section) {

        this.itemType = ItemType.CUSTOM;
        this.material = Material.getMaterial(section.getString(".item.type"));
        this.amount = section.getInt(".item.amount");
        this.displayName = section.getString(".item.display-name");
        this.lore = section.getStringList(".item.lore");
        // TODO: replace with .id when implemented
        this.id = section.getString(".item.loc-name");

        // Getting the recipe items from the config.
        this.recipeItems = section.getStringList(".recipe-items");

        // Getting the recipe shape from the config.
        this.recipeShape = section.getStringList(".recipe-shape");

        this.isShaped = section.getBoolean(".requireShape");

        this.proficiencies = section.getStringList(".canCraft");

    }

    /**
     * Create a built {@link CustomConfiguration} object from the configuration.
     * 
     * @param section The configuration section.
     * @return The built {@link CustomConfiguration} object.
     */
    public static CustomConfiguration fromConfigurationSection(ConfigurationSection section) {
        return new CustomConfiguration(section);
    }
}
