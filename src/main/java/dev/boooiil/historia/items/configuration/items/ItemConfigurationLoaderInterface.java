package dev.boooiil.historia.items.configuration.items;

/**
 * <p>
 * The ItemConfigurationLoaderFactoryInterface class is an interface for
 * creating instances of item configuration loaders within the Historia plugin.
 * It is designed to work with class loaders extending
 * {@link BaseItemConfigurationLoader} that have a definition in the
 * {@link ConfigurationProvider}.
 * </p>
 * ItemConfigurationLoaderFactoryInterface offers a single method to create
 * instances of item configuration loaders.
 * 
 * @param <T> The type of configuration loader to be created, extending
 *            {@link BaseItemConfigurationLoader}.
 * 
 * @see BaseItemConfigurationLoader
 * @see ConfigurationProvider
 */
public interface ItemConfigurationLoaderFactoryInterface<T extends BaseItemConfigurationLoader<?>> {
    public T create();
}
