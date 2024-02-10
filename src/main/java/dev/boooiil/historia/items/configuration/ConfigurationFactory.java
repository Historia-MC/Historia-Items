
package dev.boooiil.historia.items.configuration;

import dev.boooiil.historia.items.configuration.general.GeneralConfig;
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.ArmorConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.BaseConfiguration;
import dev.boooiil.historia.items.configuration.items.CustomItemConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.ItemConfigurationLoaderFactory;
import dev.boooiil.historia.items.configuration.items.ToolConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.WeaponConfigurationLoader;
import dev.boooiil.historia.items.file.FileKeys;

/**
 * <p>
 * The ConfigurationProvider class serves as a central hub for managing and
 * accessing configuration files within the Historia plugin.
 * It offers static methods to retrieve instances of specific configuration
 * classes, such as armor, weapons, custom items, and more,
 * facilitating easy access to configuration data across various modules of the
 * plugin.
 * </p>
 * <p>
 * ConfigurationProvider initializes and reloads configuration files, ensuring
 * that the plugin operates with up-to-date configuration settings.
 * Configuration files are loaded from the plugin's resources folder and are
 * stored in YAML format for easy readability and maintenance.
 * </p>
 * 
 * @see ArmorConfigurationLoader
 * @see WeaponConfigurationLoader
 * @see CustomItemConfigurationLoader
 * @see ToolConfigurationLoader
 * @see GeneralConfig
 */
public class ConfigurationFactory {

    private static final ArmorConfigurationLoader armorConfigurationLoader = ItemConfigurationLoaderFactory
            .getArmorConfigurationLoader();
    private static final WeaponConfigurationLoader weaponConfigurationLoader = ItemConfigurationLoaderFactory
            .getWeaponConfigurationLoader();
    private static final GeneralConfig generalConfig = new GeneralConfig();
    private static final CustomItemConfigurationLoader customItemConfigurationLoader = ItemConfigurationLoaderFactory
            .getCustomItemConfigurationLoader();
    private static final ToolConfigurationLoader toolConfigurationLoader = ItemConfigurationLoaderFactory
            .getToolConfigurationLoader();

    /**
     * It returns the armorConfig variable
     * 
     * @return The armorConfig variable.
     */
    public static ArmorConfigurationLoader getArmorConfigurationLoader() {
        return armorConfigurationLoader;
    }

    /**
     * It returns the weaponConfig variable
     * 
     * @return The weaponConfig object.
     */
    public static WeaponConfigurationLoader getWeaponConfigurationLoader() {
        return weaponConfigurationLoader;
    }

    /**
     * This function returns the generalConfig object
     * 
     * @return The generalConfig object.
     */
    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    /**
     * It returns the customItemConfig variable
     * 
     * @return The customItemConfig variable.
     */
    public static CustomItemConfigurationLoader getCustomItemConfigurationLoader() {
        return customItemConfigurationLoader;
    }

    public static ToolConfigurationLoader getToolConfigurationLoader() {

        return toolConfigurationLoader;
    }

    /**
     * It returns the configuration loader that has a base configuration extension
     * matching the class.
     * 
     * @param <T>   The type of the configuration.
     * @param clazz The class of the configuration.
     * @return The configuration loader.
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseConfiguration> BaseConfigurationLoader<T> getConfigurationLoader(
            Class<T> clazz) {

        if (ArmorConfiguration.class.isAssignableFrom(clazz)) {
            return (BaseConfigurationLoader<T>) armorConfigurationLoader;
        } else if (WeaponConfigurationLoader.class.isAssignableFrom(clazz)) {
            return (BaseConfigurationLoader<T>) weaponConfigurationLoader;
        } else if (CustomItemConfigurationLoader.class.isAssignableFrom(clazz)) {
            return (BaseConfigurationLoader<T>) customItemConfigurationLoader;
        } else if (ToolConfigurationLoader.class.isAssignableFrom(clazz)) {
            return (BaseConfigurationLoader<T>) toolConfigurationLoader;
        }

        return null;
    }

    /**
     * It loads the configuration files
     */
    public static void init() {

        armorConfigurationLoader.loadConfiguration(FileKeys.ARMOR);
        weaponConfigurationLoader.loadConfiguration(FileKeys.WEAPONS);
        customItemConfigurationLoader.loadConfiguration(FileKeys.CUSTOM_ITEMS);
        toolConfigurationLoader.loadConfiguration(FileKeys.TOOLS);

    }

    /**
     * It's a function that reloads the config file
     */
    public static void reload() {

        init();

    }

}
