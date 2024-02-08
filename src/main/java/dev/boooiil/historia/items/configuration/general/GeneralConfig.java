package dev.boooiil.historia.items.configuration.general;

import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.file.FileKeys;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * It's a class that grabs the configuration file and stores it in a variable.
 */
public class GeneralConfig {

    public static YamlConfiguration configuration = FileIO.get(FileKeys.CONFIG);

    public static boolean debug;

    public GeneralConfig() {

        System.out.print(configuration.toString());

        debug = configuration.getBoolean("debug");

    }

}
