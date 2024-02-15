package dev.boooiil.historia.items.commands;

import dev.boooiil.historia.items.configuration.ConfigurationProvider;
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.ArmorConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.CustomItemConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfigurationLoader;
import dev.boooiil.historia.items.crafted.armor.Armor;
import dev.boooiil.historia.items.crafted.custom.Custom;
import dev.boooiil.historia.items.crafted.weapon.Weapon;
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

            WeaponConfigurationLoader weaponConfig = ConfigurationProvider.getWeaponConfigurationLoader();

            if (weaponConfig.isValid(args[1])) {

                WeaponConfiguration weaponConfiguration = weaponConfig.getObject(args[1]);
                Weapon weapon = new Weapon(weaponConfiguration);

                player.getWorld().dropItemNaturally(player.getLocation(), weapon.getItemStack());

            }

            else {

                sender.sendMessage("Invalid item name.");
            }

        } else if (args[0].equalsIgnoreCase("armor")) {

            ArmorConfigurationLoader armorConfig = ConfigurationProvider.getArmorConfigurationLoader();

            if (armorConfig.isValid(args[1])) {

                ArmorConfiguration armorConfiguration = armorConfig.getObject(args[1]);
                Armor armor = new Armor(armorConfiguration);

                player.getWorld().dropItemNaturally(player.getLocation(), armor.getItemStack());

            }

            else {

                sender.sendMessage("Invalid item name.");
            }

        } else if (args[0].equalsIgnoreCase("other")) {

            CustomItemConfigurationLoader customItemConfig = ConfigurationProvider.getCustomItemConfigurationLoader();

            if (customItemConfig.isValid(args[1])) {

                CustomItemConfiguration itemConfiguration = customItemConfig.getObject(args[1]);
                Custom item = new Custom(itemConfiguration);

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
