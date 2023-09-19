package dev.boooiil.historia.items.configuration.specific;

import dev.boooiil.historia.items.classes.enums.file.FileKeys;
import dev.boooiil.historia.items.util.FileIO;
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
