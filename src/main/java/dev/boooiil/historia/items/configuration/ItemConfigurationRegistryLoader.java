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

public class ItemConfigurationRegistryLoader {

    private static YamlConfiguration armorYAMLConfiguration;
    private static YamlConfiguration weaponYAMLConfiguration;
    private static YamlConfiguration toolYAMLConfiguration;
    private static YamlConfiguration customYAMLConfiguration;

    public static void initialize() {

        Logging.debugToConsole("Initializing ItemConfigurationRegistryLoader...");

        armorYAMLConfiguration = FileIO.get(FileKeys.ARMOR);
        weaponYAMLConfiguration = FileIO.get(FileKeys.WEAPONS);
        toolYAMLConfiguration = FileIO.get(FileKeys.TOOLS);
        customYAMLConfiguration = FileIO.get(FileKeys.CUSTOM_ITEMS);

        load();

    }

    public static void load() {

        populate(armorYAMLConfiguration, ArmorConfiguration.class);
        populate(weaponYAMLConfiguration, WeaponConfiguration.class);
        populate(toolYAMLConfiguration, ToolConfiguration.class);
        populate(customYAMLConfiguration, CustomConfiguration.class);

    }

    public static void reload() {

        armorYAMLConfiguration = FileIO.get(FileKeys.ARMOR);
        weaponYAMLConfiguration = FileIO.get(FileKeys.WEAPONS);
        toolYAMLConfiguration = FileIO.get(FileKeys.TOOLS);
        customYAMLConfiguration = FileIO.get(FileKeys.CUSTOM_ITEMS);

        load();

    }

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
