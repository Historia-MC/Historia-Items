package dev.boooiil.historia.items.crafted.getter;

import dev.boooiil.historia.items.configuration.ConfigurationProvider;
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.ToolConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;

public class ItemGetter {

    /**
     * Get an armor object from the armor config.
     * 
     * @param localizedName The localizedName of the armor.
     * @return The armor object.
     */
    public static ArmorConfiguration getArmor(String localizedName) {

        return ConfigurationProvider.getArmorConfigurationLoader().getObject(localizedName);

    }

    /**
     * Get a custom item object from the custom item config.
     * 
     * @param localizedName The localizedName of the custom item.
     * @return The custom item object.
     */
    public static CustomItemConfiguration getCustomItem(String localizedName) {

        return ConfigurationProvider.getCustomItemConfigurationLoader().getObject(localizedName);

    }

    /**
     * Get a weapon object from the weapon config.
     * 
     * @param localizedName The localizedName of the weapon.
     * @return The weapon object.
     */
    public static WeaponConfiguration getWeapon(String localizedName) {

        return ConfigurationProvider.getWeaponConfigurationLoader().getObject(localizedName);

    }

    /**
     * Get a tool object from the tool config.
     * 
     * @param localizedName The localizedName of the tool.
     * @return The tool object.
     */
    public static ToolConfiguration getTool(String localizedName) {

        return ConfigurationProvider.getToolConfigurationLoader().getObject(localizedName);

    }

}
