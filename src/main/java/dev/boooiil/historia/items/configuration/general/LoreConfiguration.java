package dev.boooiil.historia.items.configuration.general;

import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.items.file.FileIO;

public class LoreConfiguration {

    private static YamlConfiguration loreConfiguration;

    private static HashMap<String, List<String>> loreMap = new HashMap<>();

    static {
        loreConfiguration = FileIO.findYamlConfiguration("component-lore.yml");
    }

    public static void initLoreMap() {
        for (String key : loreConfiguration.getKeys(false)) {

            loreMap.put(key, loreConfiguration.getStringList(key));

        }
    }

    public static List<String> get(String key) {
        if (loreMap.containsKey(key)) {
            return loreMap.get(key);

        }
        return null;
    }

    public static boolean contains(String key) {
        return loreMap.containsKey(key);
    }

}
