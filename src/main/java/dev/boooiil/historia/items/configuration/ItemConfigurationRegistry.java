package dev.boooiil.historia.items.configuration;

import java.util.HashMap;

import javax.annotation.Nullable;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.util.Logging;

public abstract class ItemConfigurationRegistry {
    protected static HashMap<String, BaseItemConfiguration> registry = new HashMap<>();

    public static void initialize() {
    }

    public static void register(String key, BaseItemConfiguration configuration) {
        Logging.debugToConsole("Registering " + key + " to registry.");
        registry.put(key, configuration);
    }

    public static void deregister(String key) {
        Logging.debugToConsole("Removing " + key + " from registry.");
        registry.remove(key);
    }

    public static void update(String key, BaseItemConfiguration configuration) {
        Logging.debugToConsole("Updating " + key + " in registry.");
        registry.put(key, configuration);
    }

    public static boolean contains(String key) {
        return registry.containsKey(key);
    }

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

    public static BaseItemConfiguration get(String key) {
        return registry.get(key);
    }

}
