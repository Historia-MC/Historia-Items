package dev.boooiil.historia.items.commands;

import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.crafted.custom.CustomConfiguration;
import dev.boooiil.historia.items.configuration.crafted.tool.ToolConfiguration;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.crafted.armor.Armor;
import dev.boooiil.historia.items.crafted.custom.Custom;
import dev.boooiil.historia.items.crafted.tool.Tool;
import dev.boooiil.historia.items.crafted.weapon.Weapon;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGive implements CommandExecutor {

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
        BaseItemConfiguration baseConfiguration = ItemConfigurationRegistry.get(args[1]);

        if (baseConfiguration == null) {
            sender.sendMessage("Invalid item name.");
            return false;
        }

        switch (baseConfiguration.getItemType()) {
            case WEAPON:
                WeaponConfiguration weaponConfiguration = (WeaponConfiguration) baseConfiguration;
                Weapon weapon = new Weapon(weaponConfiguration);
                player.getInventory().addItem(weapon.getItemStack());
                break;
            case ARMOR:
                ArmorConfiguration armorConfiguration = (ArmorConfiguration) baseConfiguration;
                Armor armor = new Armor(armorConfiguration);
                player.getInventory().addItem(armor.getItemStack());
                break;
            case CUSTOM:
                CustomConfiguration customConfiguration = (CustomConfiguration) baseConfiguration;
                Custom custom = new Custom(customConfiguration);
                player.getInventory().addItem(custom.getItemStack());
                break;
            case TOOL:
                ToolConfiguration toolConfiguration = (ToolConfiguration) baseConfiguration;
                Tool tool = new Tool(toolConfiguration);
                player.getInventory().addItem(tool.getItemStack());
                break;
            default:
                sender.sendMessage("Invalid item name.");
                return false;
        }

        return false;

    }
}
