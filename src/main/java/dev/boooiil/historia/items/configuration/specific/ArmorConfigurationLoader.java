package dev.boooiil.historia.items.configuration.specific;

import dev.boooiil.historia.items.configuration.BaseConfiguration;
import dev.boooiil.historia.items.items.craftable.ArmorConfiguration;
import dev.boooiil.historia.items.items.craftable.CraftedItem;
import dev.boooiil.historia.items.util.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It's a configuration class that extends the Configuration class and adds a
 * method to get armor based
 * on recipe items and shape.
 */
public class ArmorConfigurationLoader extends BaseConfiguration<ArmorConfiguration> {

    public ArmorConfiguration createNew(String armorName) {
        return new ArmorConfiguration(this.configuration.getConfigurationSection(armorName));
    }

    /**
     * Get armor based on recipe items and shape.
     * 
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     * @return Armor object.
     */
    public ArmorConfiguration getObject(List<String> inputItems, List<String> inputShape) {

        ArmorConfiguration armor = null;

        for (Map.Entry<String, ArmorConfiguration> entry : map.entrySet()) {

            boolean armorValid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (armorValid) {
                armor = entry.getValue();
                break;
            }

        }

        return armor;

    }

    /**
     * Get armor based on armor name.
     * 
     * @param armorName Armor name.
     * @return Armor object.
     */
    public ArmorConfiguration getObject(String armorName) {

        if (isValid(armorName))
            return map.get(armorName);
        else
            return null;

    }

    /**
     * It checks if the shape of the recipe is valid
     * 
     * @param shape The shape of the recipe.
     * @return A boolean value.
     */
    public boolean validShape(List<String> shape) {

        boolean found = false;

        for (ArmorConfiguration armor : map.values()) {

            if (armor.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }

    /**
     * It returns a list of all the recipes for the armor
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for (ArmorConfiguration armor : map.values()) {

            set.add(armor.getRecipeShape());
        }

        return set;

    }

    /**
     * It returns a list of all the items that have the same recipe shape as the one
     * passed in
     * 
     * @param shape A list of strings that represent the shape of the recipe.
     * @return A list of all the armor items that match the shape.
     */
    public List<CraftedItem> getAllMatchingShape(List<String> shape) {

        List<CraftedItem> set = new ArrayList<>();

        for (ArmorConfiguration armor : map.values()) {

            if (armor.getRecipeShape().equals(shape)) {

                Logging.debugToConsole(armor.getItemStack().getItemMeta().toString());

                set.add(armor);

            }
        }

        return set;

    }

}
