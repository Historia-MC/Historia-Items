package dev.boooiil.historia.items.configuration;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.items.BaseItemConfiguration;
import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.file.FileKeys;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Set;

/**
 * It takes a class and a variable number of arguments and creates a new
 * instance of the class for each
 * key in the set and puts it in the map
 */
public abstract class BaseConfigurationLoader<T extends BaseItemConfiguration> {

    protected YamlConfiguration configuration;
    protected Set<String> set;
    protected HashMap<String, T> map;

    @SuppressWarnings("unchecked")
    public Class<T> typeParameterClass() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = superClass.getActualTypeArguments()[0];

        return (Class<T>) type;
    }

    /**
     * It loads a YAML file from the plugin's data folder, and then populates a
     * HashMap with the keys
     * and values from the YAML file
     * 
     * @param fileName The name of the file you want to load.
     */
    public void loadConfiguration(FileKeys fileName) {

        // @sonatype-lift ignore
        this.configuration = FileIO.yamlFromSource(new File(Main.plugin().getDataFolder(), fileName.getKey()));
        this.set = configuration.getKeys(false);
        this.map = new HashMap<>();

        this.populateMap();

    }

    /**
     * It returns the configuration file
     * 
     * @return The configuration.
     */
    public YamlConfiguration getConfiguration() {

        return configuration;

    }

    /**
     * Get a set (unordered list) of all keys described in the configuration.
     * 
     * 
     * @return Set of all keys described in the configuration.
     * 
     * @see <a href=
     *      "https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    public Set<String> getSet() {

        return set;

    }

    /**
     * This function returns a HashMap of String keys and Object values
     * 
     * @return A HashMap
     */
    public HashMap<String, T> getMap() {

        return map;

    }

    /**
     * If the key is in the configuration.
     * 
     * @param key - Name of the object to check.
     * @return If the object is provided in the configuration.
     */
    public boolean isValid(String key) {

        return set.contains(key);

    }

    /**
     * It takes a class and a variable number of arguments and creates a new
     * instance of the class for
     * each key in the set and puts it in the map
     *
     */
    private void populateMap() {

        for (String key : set)
            if (!key.equals("version"))
                map.put(key, createNew(key));

    }

    public T getObject(String name) {

        if (isValid(name))
            return map.get(name);
        else
            return null;

    }

    public abstract T createNew(String name);
}
