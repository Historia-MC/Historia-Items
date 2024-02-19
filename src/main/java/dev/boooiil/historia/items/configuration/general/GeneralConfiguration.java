package dev.boooiil.historia.items.configuration.general;

import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.file.FileKeys;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * It's a class that grabs the configuration file and stores it in a variable.
 */
public class GeneralConfiguration {

    public static YamlConfiguration configuration;

    public static boolean debug;

    static {
        configuration = FileIO.get(FileKeys.CONFIG);
        debug = configuration.getBoolean("debug");
    }
}
