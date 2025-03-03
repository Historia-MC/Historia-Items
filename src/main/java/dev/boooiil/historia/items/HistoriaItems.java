package dev.boooiil.historia.items;

import dev.boooiil.historia.core.registry.Registry;
import dev.boooiil.historia.items.commands.CommandGive;
import dev.boooiil.historia.items.configuration.ItemRegistryLoader;
import dev.boooiil.historia.items.configuration.RecipeLoader;
import dev.boooiil.historia.items.configuration.general.LoreConfiguration;
import dev.boooiil.historia.items.events.entity.EntityDamageByEntityListener;
import dev.boooiil.historia.items.events.entity.EntityDropItemListener;
import dev.boooiil.historia.items.events.entity.EntityInteractListener;
import dev.boooiil.historia.items.events.entity.EntityPickupItemListener;
import dev.boooiil.historia.items.events.entity.EntityToggleSwimListener;
import dev.boooiil.historia.items.events.entity.ProjectileLaunchListener;
import dev.boooiil.historia.items.events.inventory.InventoryCloseListener;
import dev.boooiil.historia.items.events.inventory.InventoryOpenListener;
import dev.boooiil.historia.items.events.player.PlayerItemConsumeListener;
import dev.boooiil.historia.items.events.player.PlayerSwapHandItemsListener;
import dev.boooiil.historia.items.events.player.PlayerToggleSneakListener;
import dev.boooiil.historia.items.events.player.PlayerToggleSprintListener;
import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.ItemComponentType;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.items.util.HILogger;

/**
 * The Main class is responsible for initializing the Historia plugin.
 */
public class HistoriaItems extends JavaPlugin {

    public static final Registry<HistoriaItem> ITEM_REGISTRY = new Registry<>();
    public static final Registry<ItemComponentType<? extends ItemComponent>> COMPONENT_REGISTRY = new Registry<>();

    public static boolean isTesting = false;
    /** Singleton instance of the plugin */
    private static JavaPlugin instance;

    /**
     * Default constructor for the Main class.
     */
    public HistoriaItems() {
    }

    /**
     * The onLoad method is called when the plugin is loaded.
     */
    @Override
    public void onLoad() {

        instance = this;

        HILogger.infoToConsole("Plugin has loaded.");

        HILogger.infoToConsole("RUNNING VERSION: " + Bukkit.getVersion());

        if (Bukkit.getVersion().contains("MockBukkit")) {
            System.out.println("RUNNING IN TEST MODE");
            isTesting = true;
        }

        // Check config files
        // FileIO.checkFiles();

        FileIO.checkAndSaveResources("config.yml");
        FileIO.checkAndSaveResources("items");

        LoreConfiguration.initLoreMap();
        ItemRegistryLoader.load();
        RecipeLoader.load();
    }

    /**
     * The onEnable method is called when the plugin is enabled.
     */
    @Override
    public void onEnable() {

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        registerCommand("give", new CommandGive());

        registerEvent(new EntityDamageByEntityListener());
        registerEvent(new EntityDropItemListener());
        registerEvent(new EntityInteractListener());
        registerEvent(new EntityPickupItemListener());
        registerEvent(new EntityToggleSwimListener());
        registerEvent(new ProjectileLaunchListener());
        registerEvent(new InventoryCloseListener());
        registerEvent(new InventoryOpenListener());
        // registerEvent(new PlayerInteractListener());
        registerEvent(new PlayerItemConsumeListener());
        registerEvent(new PlayerSwapHandItemsListener());
        registerEvent(new PlayerToggleSneakListener());
        registerEvent(new PlayerToggleSprintListener());

        HILogger.infoToConsole("Plugin Enabled.");

    }

    /**
     * The onDisable method is called when the plugin is disabled.
     */
    @Override
    public void onDisable() {

        getLogger().info("Plugin disabled.");
    }

    /**
     * Returns the instance of the plugin.
     *
     * @return The instance of the plugin.
     */
    public static JavaPlugin plugin() {

        return instance;

    }

    /**
     * Returns the server instance.
     *
     * @return The server instance.
     */
    public static Server server() {

        return plugin().getServer();

    }

    /**
     * Disables the plugin.
     */
    public static void disable() {

        plugin().getServer().getPluginManager().disablePlugin(HistoriaItems.plugin());

    }

    /**
     * Returns a NamespacedKey object with the given key using this plugins
     * namespace.
     *
     * @param key The key to create the NamespacedKey with.
     * @return The NamespacedKey object.
     */
    public static NamespacedKey getNamespacedKey(String key) {

        return new NamespacedKey(plugin(), key);

    }

    /**
     * Registers an event to the server
     *
     * @param event The event to register
     */
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
