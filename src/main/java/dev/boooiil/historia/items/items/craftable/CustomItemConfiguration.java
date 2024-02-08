package dev.boooiil.historia.items.items.craftable;

import dev.boooiil.historia.items.util.Construct;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class CustomItemConfiguration extends CraftableItemConfiguration {

    public CustomItemConfiguration(ConfigurationSection section) {

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
