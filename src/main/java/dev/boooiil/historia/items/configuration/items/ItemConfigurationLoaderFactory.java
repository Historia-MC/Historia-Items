package dev.boooiil.historia.items.configuration.items;

public class ItemConfigurationLoaderFactory {

    /**
     * This method will instantiate a new {@link ArmorConfigurationLoader}.
     * 
     * <p>
     * If you are looking for an already instantiated
     * {@link ArmorConfigurationLoader}, use
     * {@link dev.boooiil.historia.items.configuration.ConfigurationProvider#getArmorConfigurationLoader()
     * ConfigurationProvider#getArmorConfigurationLoader()}
     * </p>
     * 
     * @return A new {@link ArmorConfigurationLoader} instance.
     */
    public static ArmorConfigurationLoader getArmorConfigurationLoader() {
        return new ArmorConfigurationLoader();
    }

    /**
     * This method will instantiate a new {@link WeaponConfigurationLoader}.
     * 
     * <p>
     * If you are looking for an already instantiated
     * {@link WeaponConfigurationLoader}, use
     * {@link dev.boooiil.historia.items.configuration.ConfigurationProvider#getWeaponConfigurationLoader()
     * ConfigurationProvider#getWeaponConfigurationLoader()}
     * </p>
     * 
     * @return A new {@link WeaponConfigurationLoader} instance.
     */
    public static WeaponConfigurationLoader getWeaponConfigurationLoader() {
        return new WeaponConfigurationLoader();
    }

    /**
     * This method will instantiate a new {@link CustomItemConfigurationLoader}.
     * 
     * <p>
     * If you are looking for an already instantiated
     * {@link CustomItemConfigurationLoader}, use
     * {@link dev.boooiil.historia.items.configuration.ConfigurationProvider#getCustomItemConfigurationLoader()
     * ConfigurationProvider#getCustomItemConfigurationLoader()}
     * </p>
     * 
     * @return A new {@link CustomItemConfigurationLoader} instance.
     */
    public static CustomItemConfigurationLoader getCustomItemConfigurationLoader() {
        return new CustomItemConfigurationLoader();
    }

    /**
     * This method will instantiate a new {@link ToolConfigurationLoader}.
     * 
     * <p>
     * If you are looking for an already instantiated
     * {@link ToolConfigurationLoader}, use
     * {@link dev.boooiil.historia.items.configuration.ConfigurationProvider#getToolConfigurationLoader()
     * ConfigurationProvider#getToolConfigurationLoader()}
     * </p>
     * 
     * @return A new {@link ToolConfigurationLoader} instance.
     */
    public static ToolConfigurationLoader getToolConfigurationLoader() {
        return new ToolConfigurationLoader();
    }

}
