package dev.boooiil.historia.items.configuration;

import java.util.HashMap;

import javax.annotation.Nullable;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.util.Logging;

public abstract class ItemConfigurationRegistry {
    protected static HashMap<String, BaseItemConfiguration> registry = new HashMap<>();

    public static void initialize() {
    }

    /**
     * Register a new item configuration with the registry.
     * 
     * @param key           The key to register the configuration under.
     * @param configuration The configuration to register.
     */
    public static void register(String key, BaseItemConfiguration configuration) {
        Logging.debugToConsole("Registering " + key + " to item configuration registry.");
        registry.put(key, configuration);
    }

    /**
     * Deregister an item configuration from the registry.
     * 
     * @param key The key to deregister.
     */
    public static void deregister(String key) {
        Logging.debugToConsole("Removing " + key + " from item configuration registry.");
        registry.remove(key);
    }

    /**
     * Update an item configuration in the registry.
     * 
     * @param key           The key to update.
     * @param configuration The new configuration.
     */
    public static void update(String key, BaseItemConfiguration configuration) {
        Logging.debugToConsole("Updating " + key + " in item configuration registry.");
        registry.put(key, configuration);
    }

    /**
     * Check if the registry contains a configuration with the given key.
     * 
     * @param key The key to check for.
     * @return True if the registry contains the key, false otherwise.
     */
    public static boolean contains(String key) {
        return registry.containsKey(key);
    }

    /**
     * Get a typed item configuration from the registry.
     * 
     * @param <T> The type of the configuration that the
     *            {@link BaseItemConfiguration} will be casted to.
     * @param key The key to get the configuration for.
     * @return The configuration if it exists, null otherwise.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends BaseItemConfiguration> T getTyped(String key) {

        BaseItemConfiguration configuration = registry.get(key);

        if (configuration == null)
            return null;

        try {
            T typedConfiguration = (T) configuration;
            return typedConfiguration;
        } catch (ClassCastException e) {
            Logging.debugToConsole("Error casting configuration to type " + key + " - " + e.getMessage());
            return null;
        }

    }

    /**
     * Get a base item configuration from the registry.
     * 
     * @param key The key to get the configuration for.
     * @return The configuration if it exists, null otherwise.
     */
    public static BaseItemConfiguration get(String key) {
        return registry.get(key);
    }

}
