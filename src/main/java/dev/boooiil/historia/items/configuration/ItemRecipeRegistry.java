package dev.boooiil.historia.items.configuration;

import java.util.HashMap;

import javax.annotation.Nullable;

import dev.boooiil.historia.items.crafted.recipe.BasicRecipe;
import dev.boooiil.historia.items.util.Logging;

public class ItemRecipeRegistry {

    /** The recipe registry. */
    protected static HashMap<String, BasicRecipe> registry = new HashMap<>();

    /** recipe registry default constructor */
    public ItemRecipeRegistry() {
    }

    /**
     * Initialize the recipe registry.
     */
    public static void initialize() {
    }

    /**
     * Register a new recipe with the registry.
     * 
     * @param key    The key to register the recipe under.
     * @param recipe The recipe to register.
     */
    public static void register(String key, BasicRecipe recipe) {
        Logging.debugToConsole("Registering " + key + " to recipe registry.");
        registry.put(key, recipe);
    }

    /**
     * Deregister an recipe from the registry.
     * 
     * @param key The key to deregister.
     */
    public static void deregister(String key) {
        Logging.debugToConsole("Removing " + key + " from recipe registry.");
        registry.remove(key);
    }

    /**
     * Update an recipe in the registry.
     * 
     * @param key    The key to update.
     * @param recipe The new recipe.
     */
    public static void update(String key, BasicRecipe recipe) {
        Logging.debugToConsole("Updating " + key + " in recipe registry.");
        registry.put(key, recipe);
    }

    /**
     * Check if the registry contains a recipe with the given key.
     * 
     * @param key The key to check for.
     * @return True if the registry contains the key, false otherwise.
     */
    public static boolean contains(String key) {
        return registry.containsKey(key);
    }

    /**
     * Get a typed recipe from the registry.
     * 
     * @param <T> The type of the recipe that the
     *            {@link BasicRecipe} will be casted to.
     * @param key The key to get the recipe for.
     * @return The recipe if it exists, null otherwise.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends BasicRecipe> T getTyped(String key) {

        BasicRecipe recipe = registry.get(key);

        if (recipe == null)
            return null;

        try {
            T typedConfiguration = (T) recipe;
            return typedConfiguration;
        } catch (ClassCastException e) {
            Logging.debugToConsole("Error casting recipe to type " + key + " - " + e.getMessage());
            return null;
        }

    }

    /**
     * Get a base recipe from the registry.
     * 
     * @param key The key to get the recipe for.
     * @return The recipe if it exists, null otherwise.
     */
    public static BasicRecipe get(String key) {
        return registry.get(key);
    }
}
