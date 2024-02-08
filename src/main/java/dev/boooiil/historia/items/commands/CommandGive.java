package dev.boooiil.historia.items.commands;

import dev.boooiil.historia.items.configuration.ConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.ArmorConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.CustomItemConfig;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfig;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGive implements CommandExecutor {

    @Override
    // It's a method that is called when a command is executed.
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player))
            return false;
        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("weapon")) {

            WeaponConfig weaponConfig = ConfigurationLoader.getWeaponConfig();

            if (weaponConfig.isValid(args[1])) {

                WeaponConfiguration weapon = weaponConfig.getObject(args[1]);
                player.getWorld().dropItemNaturally(player.getLocation(), weapon.getItemStack());

            }

            else {

                sender.sendMessage("Invalid item name.");
            }

        } else if (args[0].equalsIgnoreCase("armor")) {

            ArmorConfigurationLoader armorConfig = ConfigurationLoader.getArmorConfig();

            if (armorConfig.isValid(args[1])) {

                ArmorConfiguration armor = armorConfig.getObject(args[1]);
                player.getWorld().dropItemNaturally(player.getLocation(), armor.getItemStack());

            }

            else {

                sender.sendMessage("Invalid item name.");
            }

        } else if (args[0].equalsIgnoreCase("other")) {

            CustomItemConfig customItemConfig = ConfigurationLoader.getCustomItemConfig();

            if (customItemConfig.isValid(args[1])) {

                CustomItemConfiguration item = customItemConfig.getObject(args[1]);
                player.getWorld().dropItemNaturally(player.getLocation(), item.getItemStack());

            }

            else {

                sender.sendMessage("Invalid item name.");
            }

        } else {
            sender.sendMessage("Syntax: /give <weapon/armor/other> <item name>");
            return false;
        }

        return false;

    }
}
