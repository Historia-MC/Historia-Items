package dev.boooiil.historia.items.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.crafted.custom.CustomConfiguration;
import dev.boooiil.historia.items.configuration.crafted.tool.ToolConfiguration;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.file.FileKeys;
import dev.boooiil.historia.items.util.Logging;

/**
 * <p>
 * This is a utility class that is used to load the item configurations from the
 * plugin's YAML files and register them in the
 * {@link ItemConfigurationRegistry}. It also provides a method to register
 * other plugins' configurations with the registry.
 * </p>
 */
public class ItemConfigurationRegistryLoader {

    /** armor yaml configuration */
    private static YamlConfiguration armorYAMLConfiguration;
    /** weapon yaml configuration */
    private static YamlConfiguration weaponYAMLConfiguration;
    /** tool yaml configuration */
    private static YamlConfiguration toolYAMLConfiguration;
    /** custom yaml configuration */
    private static YamlConfiguration customYAMLConfiguration;

    /** item configuration registry default constructor */
    private ItemConfigurationRegistryLoader() {
    };

    /**
     * Initialize the YAML configurations within this registry loader. This does not
     * directly load the configurations into the registry, but utilizes the
     * {@link ItemConfigurationRegistryLoader#load()} method after the
     * configurations are initialized.
     */
    public static void initialize() {

        Logging.debugToConsole("Initializing ItemConfigurationRegistryLoader...");

        armorYAMLConfiguration = FileIO.get(FileKeys.ARMOR);
        weaponYAMLConfiguration = FileIO.get(FileKeys.WEAPONS);
        toolYAMLConfiguration = FileIO.get(FileKeys.TOOLS);
        customYAMLConfiguration = FileIO.get(FileKeys.CUSTOM_ITEMS);

        load();

    }

    /**
     * Loads the ItemConfigurationRegistry with the YamlConfigurations provided by
     * the plugin. Other plugins will have to load their own
     * configurations with
     * {@link ItemConfigurationRegistryLoader#populate(YamlConfiguration, Class)}.
     */
    public static void load() {

        populate(armorYAMLConfiguration, ArmorConfiguration.class);
        populate(weaponYAMLConfiguration, WeaponConfiguration.class);
        populate(toolYAMLConfiguration, ToolConfiguration.class);
        populate(customYAMLConfiguration, CustomConfiguration.class);

    }

    /**
     * Reloads the {@link ItemConfigurationRegistryLoader} by reinitializing the
     * YamlConfigurations and calling the load method. This will only reload the
     * configurations provided by THIS plugin. Other plugins will have to handle
     * their own reloading.
     */
    public static void reload() {

        initialize();

    }

    /**
     * <p>
     * Populates the ItemConfigurationRegistry with the given YamlConfiguration and
     * type. This method assumes that the YAML provided follows the format of:
     * </p>
     * <blockquote>
     * 
     * <pre>{@code
     * str_key_1:
     *  configuration:
     *    ...
     * }</pre>
     * 
     * </blockquote>
     * <p>
     * ... where str_key is the unique key that will be registered and configuration
     * is the passed {@link ConfigurationSection} to the
     * {@link BaseItemConfiguration#fromConfigurationSection(ConfigurationSection)
     * fromConfigurationSection} method.
     * </p>
     * 
     * <p>
     * Assuming the overloaded method
     * {@link BaseItemConfiguration#fromConfigurationSection(ConfigurationSection)}
     * was correctly implemented, this method effectively runs as shown:
     * </p>
     * 
     * <blockquote>
     * 
     * <pre>
     * {@code
     * 
     * for (key in configuration keys) {
     *   ConfigurationSection section = configuration.getConfigurationSection(key);
     *   ProvidedConfiguration result = ProvidedConfiguration.fromConfigurationSection(section);
     * }
     * 
     * }</pre>
     * 
     * </blockquote>
     * 
     * This method is unsafe and will throw an exception if the method
     * {@link BaseItemConfiguration#fromConfigurationSection(ConfigurationSection)}
     * does not exist or is not static. It is up to you to provide functionality
     * within your configuration classes to handle the configuration section.
     * 
     * @param configuration The YamlConfiguration to populate the registry with.
     * 
     * @param type          The type of the configuration to populate the registry
     */
    public static void populate(YamlConfiguration configuration, Class<? extends BaseItemConfiguration> type) {
        for (String key : configuration.getKeys(false)) {
            if (key.equals("version"))
                continue;

            try {
                // Get the method named "fromConfigurationSection" with a YamlConfiguration
                // parameter
                Method method = type.getMethod("fromConfigurationSection", ConfigurationSection.class);

                // Invoke the static method on the type class
                Object result = method.invoke(null, configuration.getConfigurationSection(key));

                // Assuming ItemConfigurationRegistry.register() takes a String key and the
                // result
                ItemConfigurationRegistry.register(key, (BaseItemConfiguration) result);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                Main.disable();
                break;
            }
        }

    }

}
