package dev.boooiil.historia.items.commands;

import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.crafted.custom.CustomConfiguration;
import dev.boooiil.historia.items.configuration.crafted.tool.ToolConfiguration;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.configuration.item.ItemConfiguration;
import dev.boooiil.historia.items.crafted.armor.Armor;
import dev.boooiil.historia.items.crafted.custom.Custom;
import dev.boooiil.historia.items.crafted.tool.Tool;
import dev.boooiil.historia.items.crafted.weapon.Weapon;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * <p>
 * The CommandGive class is a subclass of the {@link CommandExecutor} class and
 * is responsible for managing and executing the /give command within the
 * Historia plugin.
 * </p>
 * <p>
 * CommandGive provides methods to give items to players using the /give
 * command.
 * </p>
 * 
 * @see CommandExecutor
 * @see Command
 * @see CommandSender
 * @see Player
 * @see Bukkit
 * @see BaseItemConfiguration
 * @see ItemConfigurationRegistry
 */
public class CommandGive implements CommandExecutor {

    /** command give default constructor */
    public CommandGive() {
    }

    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 2) {
            sender.sendMessage("Syntax: /give <player> <item name>");
            return false;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("That player could not be found.");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        ItemConfiguration itemConfiguration = ItemConfigurationRegistry.get(args[1]);

        if (itemConfiguration == null) {
            sender.sendMessage("Invalid item name.");
            return false;
        }

        player.getInventory().addItem(itemConfiguration.createItemStack());

        return false;

    }
}
