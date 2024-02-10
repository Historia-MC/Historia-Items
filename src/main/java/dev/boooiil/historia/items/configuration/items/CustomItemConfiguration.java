package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.util.Construct;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

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
public class CustomItemConfiguration extends BaseItemConfiguration {

    /**
     * Create a built {@link CustomItemConfiguration} object from the configuration.
     * 
     * @param section The configuration section.
     */
    CustomItemConfiguration(ConfigurationSection section) {

        Material material = Material.getMaterial(section.getString(".item.type"));
        int amount = section.getInt(".item.amount");
        String displayName = section.getString(".item.display-name");
        List<String> lore = section.getStringList(".item.lore");

        this.itemStack = Construct.itemStack(material, amount, displayName, lore);

        // Getting the recipe items from the config.
        this.recipeItems = section.getStringList(".recipe-items");

        // Getting the recipe shape from the config.
        this.recipeShape = section.getStringList(".recipe-shape");

        this.isShaped = section.getBoolean(".requireShape");

        this.proficiencies = section.getStringList(".canCraft");

    }

}
