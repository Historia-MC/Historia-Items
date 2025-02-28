package dev.boooiil.historia.items.configuration.general;

import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.file.FileKeys;

import javax.annotation.processing.Generated;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * It's a class that grabs the configuration file and stores it in a variable.
 */
public final class GeneralConfiguration {

    /** The configuration file. */
    public static YamlConfiguration configuration;

    /** If the plugin is in debug mode. */
    public static boolean debug;

    static {
        configuration = FileIO.get(FileKeys.CONFIG);
        debug = configuration.getBoolean("debug");
    }

    @Generated(value = "Static Utility")
    private GeneralConfiguration() {
    }
}
