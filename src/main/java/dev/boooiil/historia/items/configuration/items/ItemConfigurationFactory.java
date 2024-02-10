package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.configuration.BaseConfigurationLoader;
import dev.boooiil.historia.items.configuration.ConfigurationProvider;

/**
 * <p>
 * The ItemConfigurationFactory class facilitates the creation of item
 * configurations within the Historia plugin.
 * It is designed to work with class loaders extending
 * {@link BaseConfigurationLoader} that have a
 * definition in the {@link ConfigurationProvider}.
 * </p>
 * ItemConfigurationFactory offers methods to retrieve the configuration loader,
 * obtain configuration objects by name, and check the validity of configuration
 * data.
 * 
 * @param <IC> The type of configuration handled by the factory, extending
 *             {@link BaseItemConfiguration}.
 * 
 * @see BaseConfigurationLoader
 * @see ConfigurationProvider
 * @see BaseItemConfiguration
 */
public class ItemConfigurationFactory<IC extends BaseItemConfiguration> {

    /**
     * BaseConfigurationLoader instance responsible for loading configurations of
     * type {@code IC}.
     * This variable is used to store instances of a class extending {@link IC}.
     */
    private final BaseConfigurationLoader<IC> configurationLoader;

    /**
     * Constructs an ItemConfigurationFactory with the specified configuration type.
     * The configuration loader is obtained from the ConfigurationProvider based on
     * the provided configuration type.
     * 
     * @param <IC>              The type of configuration handled by the factory,
     *                          extending {@link BaseItemConfiguration}.
     * @param configurationType The class type of the
     *                          {@link BaseConfigurationLoader}.
     * @return An ItemConfigurationFactory instance for the specified configuration
     *         type.
     */
    public static <IC extends BaseItemConfiguration> ItemConfigurationFactory<IC> create(Class<IC> configurationType) {
        return new ItemConfigurationFactory<>(configurationType);
    }

    /**
     * Constructs an ItemConfigurationFactory with the specified class type of the
     * configuration loader.
     * The configuration loader is obtained from the ConfigurationProvider based on
     * the provided class type.
     * 
     * @param clazz The class type of the {@link BaseConfigurationLoader}.
     */
    public ItemConfigurationFactory(Class<IC> clazz) {
        this.configurationLoader = ConfigurationProvider.getConfigurationLoader(clazz);
    }

    /**
     * Retrieves the configuration loader associated with this factory.
     * 
     * @return The configuration loader instance.
     */
    public BaseConfigurationLoader<IC> getConfigurationLoader() {
        return configurationLoader;
    }

    /**
     * Retrieves an item's configuration object by name from the associated
     * configuration loader.
     * 
     * @param itemName The name of the configured item to retrieve.
     * @return The configuration object corresponding to the specified name.
     */
    public IC getObject(String itemName) {
        return configurationLoader.getObject(itemName);
    }

    /**
     * Checks if the item name is provided within the configuration loader's map.
     * 
     * @param itemName The name of the item to validate.
     * @return {@code true} if the configuration object is valid, {@code false}
     *         otherwise.
     */
    public boolean isValid(String itemName) {
        return configurationLoader.isValid(itemName);
    }
}
