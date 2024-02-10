package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.configuration.BaseConfigurationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It's a class that gets information from a configuration file.
 */
public class WeaponConfigurationLoader extends BaseConfigurationLoader<WeaponConfiguration> {

    /**
     * Used to create a new instance of Weapon.
     * 
     * @param weaponName - Name of the weapon to create.
     * @return Returns a Weapon object.
     */
    public WeaponConfiguration createNew(String weaponName) {

        return new WeaponConfiguration(this.configuration.getConfigurationSection(weaponName));

    }

    /**
     * It takes a list of items and a list of shapes, and returns a weapon if the
     * items and shapes
     * match a weapon recipe
     * 
     * @param inputItems A list of strings that represent the items used to craft
     *                   the weapon.
     * @param inputShape A list of strings that represent the shape of the weapon.
     * @return A Weapon object.
     */
    public WeaponConfiguration getObject(List<String> inputItems, List<String> inputShape) {

        WeaponConfiguration weapon = null;

        for (Map.Entry<String, WeaponConfiguration> entry : map.entrySet()) {

            boolean armorValid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (armorValid) {
                weapon = entry.getValue();
                break;
            }

        }

        return weapon;

    }

    /**
     * If the weapon name is valid, return the weapon object from the map.
     * Otherwise, return null
     * 
     * @param weaponName The name of the weapon you want to get.
     * @return A Weapon object.
     */
    public WeaponConfiguration getObject(String weaponName) {

        if (isValid(weaponName))
            return map.get(weaponName);
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

        for (WeaponConfiguration weapon : map.values()) {

            if (weapon.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }

    /**
     * It returns a list of all the recipes for the weapons
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for (WeaponConfiguration weapon : map.values()) {

            set.add(weapon.getRecipeShape());
        }

        return set;

    }

    /**
     * It returns a list of all the items that have the same recipe shape as the one
     * passed in
     * 
     * @param shape A list of strings that represent the shape of the recipe.
     * @return A list of all the weapon items that match the shape.
     */
    public List<BaseConfiguration> getAllMatchingShape(List<String> shape) {

        List<BaseConfiguration> set = new ArrayList<>();

        for (WeaponConfiguration weapon : map.values()) {

            if (weapon.getRecipeShape().equals(shape)) {

                set.add(weapon);

            }
        }

        return set;

    }
}