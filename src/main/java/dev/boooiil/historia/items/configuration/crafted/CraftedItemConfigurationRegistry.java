package dev.boooiil.historia.items.configuration.crafted;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.util.Logging;

/**
 * <p>
 * The CraftedItemConfigurationRegistry class is an abstract class that extends
 * on the {@link ItemConfigurationRegistry} class. It provides various methods
 * for shape and material matching for crafted items.
 * </p>
 * 
 * <p>
 * This class should be used as an extension for configurations that have a
 * recipe, such as weapons, armor, tools, and custom items.
 * </p>
 * 
 * @see ItemConfigurationRegistry
 * @see BaseItemConfiguration
 * @see dev.boooiil.historia.items.configuration.RegistryLoader
 *      ConfigurationProvider
 */
public abstract class CraftedItemConfigurationRegistry extends ItemConfigurationRegistry {

    /**
     * It returns the object that matches the input items and shape
     * 
     * @param inputItems A list of strings that represent the items in the recipe.
     * @param inputShape A list of strings that represent the shape of the recipe.
     * @return The object that matches the input items and shape.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends BaseItemConfiguration> T getTyped(List<String> inputItems, List<String> inputShape) {

        T object = null;

        for (Map.Entry<String, BaseItemConfiguration> entry : registry.entrySet()) {

            boolean objectValid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (objectValid) {
                object = (T) entry.getValue();
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

        for (BaseItemConfiguration object : registry.values()) {

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

        for (BaseItemConfiguration object : registry.values()) {

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
    public List<BaseItemConfiguration> getAllMatchingShape(List<String> shape) {

        List<BaseItemConfiguration> set = new ArrayList<>();

        for (BaseItemConfiguration object : registry.values()) {

            if (!object.isShapeDependent())
                set.add(object);
            else if (object.getRecipeShape().equals(shape)) {

                Logging.debugToConsole(object.getDisplayName());

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
    public List<BaseItemConfiguration> getAllMatchingMaterials(List<String> materials) {

        List<BaseItemConfiguration> set = new ArrayList<>();

        for (BaseItemConfiguration object : registry.values()) {

            if (new HashSet<>(object.getRecipeItems()).containsAll(materials)) {

                Logging.debugToConsole(object.toString());

                set.add(object);

            }
        }

        return set;

    }

}
