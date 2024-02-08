package dev.boooiil.historia.items.crafted.getter;

import dev.boooiil.historia.items.configuration.ConfigurationFactory;

public class ItemValidator {

    /**
     * Check if the armor is valid.
     * 
     * @param localizedName The localizedName of the armor.
     * @return True if the armor is valid, false if not.
     */
    public static boolean validArmor(String localizedName) {

        return ConfigurationFactory.getArmorConfigurationLoader().isValid(localizedName);

    }

    /**
     * Check if the custom item is valid.
     * 
     * @param localizedName The localizedName of the custom item.
     * @return True if the custom item is valid, false if not.
     */
    public static boolean validCustomItem(String localizedName) {

        return ConfigurationFactory.getCustomItemConfigurationLoader().isValid(localizedName);

    }

    /**
     * Check if the weapon is valid.
     * 
     * @param localizedName The localizedName of the weapon.
     * @return True if the weapon is valid, false if not.
     */
    public static boolean validWeapon(String localizedName) {

        return ConfigurationFactory.getWeaponConfigurationLoader().isValid(localizedName);

    }

    /**
     * Check if the tool is valid.
     * 
     * @param localizedName The localizedName of the tool.
     * @return True if the tool is valid, false if not.
     */
    public static boolean validTool(String localizedName) {

        return ConfigurationFactory.getToolConfigurationLoader().isValid(localizedName);

    }

}
