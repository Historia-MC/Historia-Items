package dev.boooiil.historia.items.configuration.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import dev.boooiil.historia.items.configuration.BaseConfigurationLoader;
import dev.boooiil.historia.items.util.Logging;

public abstract class BaseItemConfigurationLoader<T extends BaseConfiguration>
        extends BaseConfigurationLoader<T> {

    /**
     * It returns the object that matches the input items and shape
     * 
     * @param inputItems A list of strings that represent the items in the recipe.
     * @param inputShape A list of strings that represent the shape of the recipe.
     * @return The object that matches the input items and shape.
     */
    public T getObject(List<String> inputItems, List<String> inputShape) {

        T object = null;

        for (Map.Entry<String, T> entry : map.entrySet()) {

            boolean objectValid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (objectValid) {
                object = entry.getValue();
                break;
            }

        }

        return object;

    }

    /**
     * It checks if the shape of the recipe is valid
     * 
     * @param shape The shape of the recipe.
     * @return True - If the shape is valid.
     */
    public boolean validShape(List<String> shape) {

        boolean found = false;

        for (T object : map.values()) {

            if (object.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }

    /**
     * It returns a list of all the recipes for the item configuration
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for (T object : map.values()) {

            set.add(object.getRecipeShape());
        }

        return set;

    }

    /**
     * It returns a list of all the items that have the same recipe shape as the one
     * passed in
     * 
     * @param shape A list of strings that represent the shape of the recipe.
     * @return A list of all the item configurations items that match the shape.
     */
    public List<BaseConfiguration> getAllMatchingShape(List<String> shape) {

        List<BaseConfiguration> set = new ArrayList<>();

        for (T object : map.values()) {

            if (object.getRecipeShape().equals(shape)) {

                Logging.debugToConsole(object.getItemStack().getItemMeta().toString());

                set.add(object);

            }
        }

        return set;

    }

    /**
     * Matches all items that have the same materials as the ones passed in
     * 
     * @param materials A list of strings that represent the materials in the
     *                  recipe.
     * @return A list of all the item configurations that match the materials.
     */
    public List<BaseConfiguration> getAllMatchingMaterials(List<String> materials) {

        List<BaseConfiguration> set = new ArrayList<>();

        for (T object : map.values()) {

            if (new HashSet<>(object.getRecipeItems()).containsAll(materials)) {

                Logging.debugToConsole(object.getItemStack().getItemMeta().getAsString());

                set.add(object);

            }
        }

        return set;

    }
}
