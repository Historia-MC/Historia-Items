package dev.boooiil.historia.items;

import dev.boooiil.historia.items.commands.CommandGive;
import dev.boooiil.historia.items.configuration.ConfigurationProvider;
import dev.boooiil.historia.items.events.inventory.CraftItemListener;
import dev.boooiil.historia.items.file.FileIO;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.items.util.Logging;

public class Main extends JavaPlugin {

    private static JavaPlugin instance;

    /**
     *
     * Server starts => Config loaded => passed to respective handler => other
     * classes can access the object
     *
     * Config section (class, expiry, etc) passed to respective handler that parses
     * that infromation into an accessable object.
     *
     * Player logs in and is applied stats based on the configuration stored.
     *
     * Player respawns and is applied stats based on the configuration stored.
     *
     */

    @Override
    public void onLoad() {

        instance = this;

        Logging.infoToConsole("Plugin has loaded.");

        // Check config files
        FileIO.checkFiles();

    }

    @Override
    public void onEnable() {

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        ConfigurationProvider.init();

        registerEvent(new CraftItemListener());

        registerCommand("give", new CommandGive());

        Logging.infoToConsole("Plugin Enabled.");

    }

    @Override
    public void onDisable() {

        getLogger().info("Plugin disabled.");
    }

    public static JavaPlugin plugin() {

        return instance;

    }

    public static Server server() {

        return plugin().getServer();

    }

    public static void disable(Plugin plugin) {

        plugin.getServer().getPluginManager().disablePlugin(plugin);

    }

    public static NamespacedKey getNamespacedKey(String key) {

        return new NamespacedKey(plugin(), key);

    }

    private void registerEvent(Listener event) {

        this.getServer().getPluginManager().registerEvents(event, this);

    }

    /**
     * It registers a command to the server
     *
     * @param commandName The name of the command you want to register.
     * @param command     The command to register
     */
    private void registerCommand(String commandName, CommandExecutor command) {

        if (commandName == null || command == null)
            return;

        this.getCommand(commandName).setExecutor(command);

    }

}
