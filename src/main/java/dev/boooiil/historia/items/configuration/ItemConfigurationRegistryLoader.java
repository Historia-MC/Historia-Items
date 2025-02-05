package dev.boooiil.historia.items.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.items.configuration.item.ItemConfiguration;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.configuration.item.components.tool.ToolComponent;
import dev.boooiil.historia.items.file.FileIO;
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

    private static List<YamlConfiguration> configurations;

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

        load();

    }

    /**
     * Loads the ItemConfigurationRegistry with the YamlConfigurations provided by
     * the plugin. Other plugins will have to load their own
     * configurations with
     */
    public static void load() {
        configurations = FileIO.loadYamlConfigurationsFromPlugins();

        populate();
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
    public static void populate() {

        for (YamlConfiguration configuration : configurations) {

            Set<String> keys = configuration.getKeys(false);

            for (String key : keys) {

                Logging.debugToConsole("Key", key);

                HashMap<String, IItemComponent> components = new HashMap<>();

                ConfigurationSection section = configuration.getConfigurationSection(key);

                if (section.contains("tool")) {
                    Logging.debugToConsole(key, "had tool component.");
                    components.put("tool", new ToolComponent());
                }

                if (section.contains("weapon")) {
                    Logging.debugToConsole(key, "had weapon component.");
                    // TODO: add weapon
                }

                if (section.contains("armor")) {
                    Logging.debugToConsole(key, "had armor component.");
                    // TODO: add armor
                }

                if (section.contains("modifier")) {
                    Logging.debugToConsole(key, "had modifier component.");
                    // TODO: add armor
                }

                if (section.contains("smelting")) {
                    Logging.debugToConsole(key, "had smelting component.");
                    // TODO: add armor
                }

                if (section.contains("executable")) {
                    Logging.debugToConsole(key, "had executable component.");
                    // TODO: add executable
                }

                ItemConfigurationRegistry.register(key, new ItemConfiguration(section, components));

            }

        }
        // throw new Error("Unimplemented method.");

    }

}
