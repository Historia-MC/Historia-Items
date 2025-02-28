package dev.boooiil.historia.items.util;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.configuration.general.GeneralConfiguration;
import net.kyori.adventure.text.Component;

/**
 * It's a class that sends messages to the console, server, or a player
 */
public class HILogger {

    private static final String messagePrefix = "§7[§9Historia§7] ";
    private static final String announcePrefix = "§7[§9Announcement§7] ";
    // private static final String debugPrefix = "§7[§cDebug§7] ";

    private static final Logger logger = HistoriaItems.plugin().getLogger();

    // It's a private constructor that throws an error if someone tries to
    // instantiate the class.
    private HILogger() {
        throw new IllegalAccessError("Static utility class.");
    }

    /**
     * Send an info message to the console.
     * 
     * @param messages The message to be sent.
     */
    public static void infoToConsole(String... messages) {

        String built = "";

        for (String message : messages) {

            built += message + " ";

        }

        logger.info(built);

    }

    /**
     * Send an info message to server.
     * 
     * @param message The message to be sent.
     */
    public static void infoToServer(String message) {

        HistoriaItems.server().sendMessage(Component.text(announcePrefix + "§7" + message));

    }

    /**
     * Send an info message to a player.
     * 
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void infoToPlayer(String message, UUID uuid) {

        Player player = Bukkit.getPlayer(uuid);

        if (player != null && player.isOnline())
            player.sendMessage(messagePrefix + "§7" + message);

    }

    /**
     * Send an info message to a player.
     * 
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void infoToPlayerNoPrefix(String message, UUID uuid) {

        Player player = Bukkit.getPlayer(uuid);

        if (player != null && player.isOnline())
            player.sendMessage("§7" + message);

    }

    /**
     * Send a warning message to the console.
     * 
     * @param messages The message to be sent.
     */
    public static void warnToConsole(String... messages) {

        String built = "";

        for (String message : messages) {

            built += message + " ";

        }

        logger.warning(built);

    }

    /**
     * Send a warning message to server.
     * 
     * @param message The message to be sent.
     */
    public static void warnToServer(String message) {

        HistoriaItems.server().sendMessage(Component.text(announcePrefix + "§6" + message));

    }

    /**
     * Send a warning message to the player.
     * 
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void warnToPlayer(String message, UUID uuid) {

        Player player = Bukkit.getPlayer(uuid);

        if (player != null && player.isOnline())
            player.sendMessage(messagePrefix + "§6" + message);

    }

    /**
     * Send an error message to the console.
     * 
     * @param messages The message to be sent.
     */
    public static void errorToConsole(String... messages) {

        String built = "";

        for (String message : messages) {

            built += message + " ";

        }

        logger.severe(built);

    }

    /**
     * Send a warning message to server.
     * 
     * @param message The message to be sent.
     */
    public static void errorToServer(String message) {

        HistoriaItems.server().sendMessage(Component.text(announcePrefix + "§6" + message));

    }

    /**
     * Send an error message to a player.
     * 
     * @param message The message to be sent.
     * @param uuid    The UUID of the player.
     */
    public static void errorToPlayer(String message, UUID uuid) {

        Player player = Bukkit.getPlayer(uuid);

        if (player != null && player.isOnline())
            player.sendMessage(messagePrefix + "§c" + message);

    }

    /**
     * Send a warning message to server.
     * 
     * @param messages The messages to be sent.
     */
    public static void debugToConsole(String... messages) {

        if (GeneralConfiguration.debug) {

            String built = "";

            for (String message : messages) {

                built += message + " ";

            }

            warnToConsole("[DEBUG] " + built);

        }

    }

}
