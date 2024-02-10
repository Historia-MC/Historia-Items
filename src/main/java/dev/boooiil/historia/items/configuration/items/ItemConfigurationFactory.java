package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.configuration.BaseConfigurationLoader;
import dev.boooiil.historia.items.configuration.ConfigurationFactory;

/**
 * It is a factory class that creates instances of a class extending
 * {@link BaseConfigurationLoader}.
 * 
 * @param <IC> The class type of the {@link BaseConfigurationLoader}.
 */
public class ItemConfigurationFactory<IC extends BaseConfiguration> {

    /**
     * <p>
     * Base class with extension of type {@code IC}.
     * </p>
     * <p>
     * This variable is used to store instances of a class extending {@link IC}.
     * </p>
     */
    private final BaseConfigurationLoader<IC> configurationLoader;

    /**
     * 
     * @param clazz The class type of the {@link BaseConfigurationLoader}.
     */
    public ItemConfigurationFactory(Class<IC> clazz) {
        this.configurationLoader = ConfigurationFactory.getConfigurationLoader(clazz);
    }

    public BaseConfigurationLoader<IC> getConfigurationLoader() {
        return configurationLoader;
    }

    public IC getObject(String itemName) {
        return configurationLoader.getObject(itemName);
    }

    public boolean isValid(String itemName) {
        return configurationLoader.isValid(itemName);
    }

}
