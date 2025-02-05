package dev.boooiil.historia.items.commands;

import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.configuration.item.ItemConfiguration;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
public class CommandGive implements TabExecutor {

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

        ItemStack stack = itemConfiguration.createItemStack();
        player.getInventory().addItem(stack);

        Component message = Component.text("Gave " + stack.getAmount() + " ")
                .append(stack.displayName())
                .append(Component.text(" to "))
                .append(Component.text(player.getName()).hoverEvent(player));

        player.sendMessage(message);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Bukkit.getServer().getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2) {
            return ItemConfigurationRegistry.allKeys().stream()
                    .filter(key -> key.toLowerCase().startsWith(args[1].toLowerCase()))
                    .toList();
        }

        return null;
    }
}
