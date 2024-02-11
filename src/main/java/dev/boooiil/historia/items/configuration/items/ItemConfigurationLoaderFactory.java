package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.file.FileKeys;

/**
 * <p>
 * The ItemConfigurationLoaderFactory class is a factory for creating
 * instances of item configuration loaders within the Historia plugin.
 * This class will instantiate a new configuration loader and load
 * the configuration file for the specified type.
 * </p>
 * <p>
 * This class should only be used if you are wanting a NEW instance of a
 * configuration loader. If you are looking for an already instantiated
 * configuration loader, use the
 * {@link dev.boooiil.historia.items.configuration.ConfigurationProvider
 * ConfigurationProvider} class.
 * 
 * @see dev.boooiil.historia.items.configuration.ConfigurationProvider
 *      ConfigurationProvider
 * @see ArmorConfigurationLoader
 * @see WeaponConfigurationLoader
 * @see CustomItemConfigurationLoader
 * @see ToolConfigurationLoader
 * 
 */
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
        ArmorConfigurationLoader armorConfigurationLoader = new ArmorConfigurationLoader();
        armorConfigurationLoader.loadConfiguration(FileKeys.ARMOR);
        return armorConfigurationLoader;
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
        WeaponConfigurationLoader weaponConfigurationLoader = new WeaponConfigurationLoader();
        weaponConfigurationLoader.loadConfiguration(FileKeys.WEAPONS);
        return weaponConfigurationLoader;
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
        CustomItemConfigurationLoader customItemConfigurationLoader = new CustomItemConfigurationLoader();
        customItemConfigurationLoader.loadConfiguration(FileKeys.CUSTOM_ITEMS);
        return customItemConfigurationLoader;
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
        ToolConfigurationLoader toolConfigurationLoader = new ToolConfigurationLoader();
        toolConfigurationLoader.loadConfiguration(FileKeys.TOOLS);
        return toolConfigurationLoader;
    }

}
