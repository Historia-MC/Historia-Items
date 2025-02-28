package dev.boooiil.historia.items.file;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.util.HILogger;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A class for handling file input and output.
 */
public class FileIO {

    private static final List<String> configFileNames = new ArrayList<>();

    /**
     * recursively iterate over the resouces folder
     * 
     * check if file exists in plugins area
     * if not, create it
     * 
     * if it does exist, check the version of the file
     * if version mismatch, save it
     */

    public static void checkAndSaveResources(String resourcePath) {
        URL resourceURL = HistoriaItems.class.getClassLoader().getResource(resourcePath);
        if (resourceURL == null) {
            System.out.println("Resource path not found: " + resourcePath);
            return;
        }

        System.out.println("Resource URL: " + resourceURL);
        System.out.println("Protocol: " + resourceURL.getProtocol());

        if (resourceURL.getProtocol().equals("jar")) {
            scanJarResources(resourcePath); // Only use this when running from a JAR
        } else {
            File file = new File(resourceURL.getPath());

            if (file.isDirectory()) {
                scanFileSystemResources(file, resourcePath); // Scan directories
            } else {
                processFile(resourcePath); // Process single files
            }
        }
    }

    private static void scanJarResources(String resourcePath) {
        try {
            URL jarURL = HistoriaItems.class.getProtectionDomain().getCodeSource().getLocation();
            JarURLConnection jarConnection = (JarURLConnection) new URL("jar:" + jarURL + "!/").openConnection();
            JarFile jarFile = jarConnection.getJarFile();

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.startsWith(resourcePath) && !entry.isDirectory()) {
                    processFile(name);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading JAR file: " + e.getMessage());
        }
    }

    private static void scanFileSystemResources(File directory, String baseResourcePath) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist or is not a folder: " + directory);
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("No files found in directory: " + directory);
            return;
        }

        for (File file : files) {
            String relativePath = baseResourcePath + "/" + file.getName();

            if (file.isDirectory()) {
                scanFileSystemResources(file, relativePath); // Recursively process subdirectories
            } else {
                processFile(relativePath);
            }
        }
    }

    private static void processFile(String resourcePath) {
        File pluginFile = new File(HistoriaItems.plugin().getDataFolder(), resourcePath);
        if (!pluginFile.exists()) {
            HistoriaItems.plugin().saveResource(resourcePath, false);
            System.out.println("Saved missing resource: " + resourcePath + " to "
                    + pluginFile);
        } else {
            if (isVersionMismatch(pluginFile, resourcePath)) {
                HistoriaItems.plugin().saveResource(resourcePath, true);
                System.out.println("Updated resource due to version mismatch: " + resourcePath);
            } else {
                System.out.println("File is up to date: " + resourcePath);
            }
        }
    }

    private static boolean isVersionMismatch(File pluginFile, String resourcePath) {
        YamlConfiguration existingConfig = YamlConfiguration.loadConfiguration(pluginFile);
        InputStream resourceStream = HistoriaItems.plugin().getResource(resourcePath);

        if (resourceStream == null) {
            return false; // If resource doesn't exist in JAR, assume no update is needed
        }

        YamlConfiguration resourceConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(resourceStream));

        String existingVersion = existingConfig.getString("version", "unknown");
        String resourceVersion = resourceConfig.getString("version", "unknown");

        return !existingVersion.equals(resourceVersion);
    }

    static {

        for (FileKeys key : FileKeys.values()) {
            configFileNames.add(key.getKey());
        }

    }

    /* FileIO default constructor */
    private FileIO() {
    };

    /**
     * Recursively iterates over the plugins folder and loads all YML files into
     * YamlConfiguration.
     *
     * @return a list of YamlConfiguration objects for each YML file found
     */
    public static List<YamlConfiguration> loadYamlConfigurationsFromPlugins() {
        File pluginsDirectory = HistoriaItems.plugin().getDataFolder(); // Path to the 'plugins' folder
        List<YamlConfiguration> configurations = new ArrayList<>();

        // Check if the plugins directory exists
        if (!pluginsDirectory.exists() || !pluginsDirectory.isDirectory()) {
            System.out.println("Plugins directory does not exist or is not a directory.");
            return configurations;
        }

        // Start the recursive scanning process
        scanDirectoryForYmlFiles(pluginsDirectory, configurations);
        return configurations;
    }

    /**
     * Recursively scans the directory and its subdirectories for YML files.
     *
     * @param directory      the directory to scan
     * @param configurations the list to store YamlConfiguration objects
     */
    private static void scanDirectoryForYmlFiles(File directory, List<YamlConfiguration> configurations) {
        // Get all files and directories in the current directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively scan subdirectories
                    scanDirectoryForYmlFiles(file, configurations);
                } else if (file.isFile() && file.getName().endsWith(".yml")) {

                    List<String> fileBlacklist = List.of(
                            "config.yml",
                            "recipe.yml",
                            "item_descriptor.yml",
                            "items.yml",
                            "component-lore.yml");

                    // ignore non-item files
                    if (fileBlacklist.contains(file.getName()))
                        continue;

                    try {
                        // Load the YML file into a YamlConfiguration
                        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(file);
                        configurations.add(yamlConfig);
                        System.out.println("Loaded YML file: " + file.getAbsolutePath());
                    } catch (Exception e) {
                        System.out.println(
                                "Failed to load YML file: " + file.getName() + " due to " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Checks the existence and version of config files.
     */
    public static void checkFiles() {

        HILogger.infoToConsole("Checking existence and version of config files.");

        for (String fileName : configFileNames) {
            File diskFile = new File(HistoriaItems.plugin().getDataFolder(), fileName);

            if (!diskFile.exists()) {
                HILogger.infoToConsole("Missing config file: " + fileName + " has been saved to disk from resources.");
                HILogger.infoToConsole("Location: " + diskFile.getAbsolutePath());
                HistoriaItems.plugin().saveResource(fileName, false);
                continue;
            }

            YamlConfiguration diskConfig = yamlFromSource(diskFile);
            YamlConfiguration jarConfig = yamlFromSource(HistoriaItems.plugin().getResource(fileName));

            int diskVersion = diskConfig.getInt("version");
            int jarVersion = jarConfig.getInt("version");

            if (diskVersion < jarVersion) {
                HILogger.infoToConsole("Outdated config file (" + diskVersion + "): " + fileName
                        + " has been replaced on disk by the newer version " + jarVersion + ".");
                HistoriaItems.plugin().saveResource(fileName, true);
            }
        }

        HILogger.infoToConsole("Completed checks of existence and version of config files.");

    }

    /**
     * Loads a YamlConfiguration from an InputStream.
     *
     * @param is The InputStream to load the YamlConfiguration from.
     * @return The YamlConfiguration loaded from the InputStream.
     */
    public static YamlConfiguration yamlFromSource(InputStream is) {
        Reader reader = new InputStreamReader(is, Charset.defaultCharset());
        return YamlConfiguration.loadConfiguration(reader);
    }

    /**
     * Loads a YamlConfiguration from a File.
     *
     * @param file The File to load the YamlConfiguration from.
     * @return The YamlConfiguration loaded from the File.
     */
    public static YamlConfiguration yamlFromSource(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * It takes an array of files and a string, and returns true if the string is
     * the name of one of
     * the files in the array
     *
     * @param files The array of files to check
     * @param check The name of the file you want to check for.
     * @return A boolean value.
     */
    public static boolean find(File[] files, FileKeys check) {

        boolean found = false;

        for (File file : files) {

            if (file.getName().equals(check.getKey())) {
                found = true;
                break;
            }

        }

        return found;

    }

    /**
     * If the file exists in the external directory, load it from there. If it
     * doesn't, load it from
     * the internal directory
     *
     * @param check The file name to check for.
     * @return A YamlConfiguration object.
     */
    public static YamlConfiguration get(FileKeys check) {

        YamlConfiguration config;

        if (find(HistoriaItems.plugin().getDataFolder().listFiles(), check)) {

            HILogger.debugToConsole("Obtained file from external directory: ",
                    HistoriaItems.plugin().getDataFolder().getPath() + "\\" + check.getKey());

            File file = new File(HistoriaItems.plugin().getDataFolder().getPath(), check.getKey());

            config = YamlConfiguration.loadConfiguration(file);
        }

        else {

            HILogger.debugToConsole("Obtained file from internal directory: " + check.getKey());

            InputStream is = FileIO.class.getClassLoader().getResourceAsStream(check.getKey());

            Reader reader = new InputStreamReader(is);

            config = YamlConfiguration.loadConfiguration(reader);

        }

        return config;

    }

    /**
     * Searches for a YAML file by name in the plugins directory and loads it as a
     * YamlConfiguration.
     *
     * @param fileName the name of the YAML file (e.g., "tool1.yml")
     * @return YamlConfiguration object if found, otherwise null
     */
    public static YamlConfiguration findYamlConfiguration(String fileName) {
        File pluginsDirectory = HistoriaItems.plugin().getDataFolder(); // Path to the 'plugins' folder

        // Validate that the plugins directory exists
        if (!pluginsDirectory.exists() || !pluginsDirectory.isDirectory()) {
            System.out.println("Plugins directory does not exist or is not a directory.");
            return null;
        }

        // Search for the file
        File yamlFile = searchYamlFile(pluginsDirectory, fileName);
        if (yamlFile != null) {
            System.out.println("Found YAML file: " + yamlFile.getAbsolutePath());
            return YamlConfiguration.loadConfiguration(yamlFile);
        } else {
            System.out.println("YAML file not found: " + fileName);
            return null;
        }
    }

    /**
     * Recursively searches for a YAML file in a directory.
     *
     * @param directory the directory to search
     * @param fileName  the target YAML file name
     * @return the File if found, otherwise null
     */
    private static File searchYamlFile(File directory, String fileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively search in subdirectories
                    File foundFile = searchYamlFile(file, fileName);
                    if (foundFile != null) {
                        return foundFile;
                    }
                } else if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) {
                    // File found
                    return file;
                }
            }
        }
        return null;
    }

}
