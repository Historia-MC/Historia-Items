package dev.boooiil.historia.items.getter;

import dev.boooiil.historia.items.classes.items.craftable.Armor;
import dev.boooiil.historia.items.classes.items.craftable.CustomItem;
import dev.boooiil.historia.items.classes.items.craftable.Tool;
import dev.boooiil.historia.items.classes.items.craftable.Weapon;
import dev.boooiil.historia.items.configuration.ConfigurationLoader;

public class ItemGetter {

    /**
     * Get an armor object from the armor config.
     * @param localizedName The localizedName of the armor.
     * @return The armor object.
     */
    public static Armor getArmor(String localizedName) {

        return ConfigurationLoader.getArmorConfig().getObject(localizedName);

    }

    /**
     * Get a custom item object from the custom item config.
     * @param localizedName The localizedName of the custom item.
     * @return The custom item object.
     */
    public static CustomItem getCustomItem(String localizedName) {

        return ConfigurationLoader.getCustomItemConfig().getObject(localizedName);

    }

    /**
     * Get a weapon object from the weapon config.
     * @param localizedName The localizedName of the weapon.
     * @return The weapon object.
     */
    public static Weapon getWeapon(String localizedName) {

        return ConfigurationLoader.getWeaponConfig().getObject(localizedName);

    }

    /**
     * Get a tool object from the tool config.
     * @param localizedName The localizedName of the tool.
     * @return The tool object.
     */
    public static Tool getTool(String localizedName) {

        return ConfigurationLoader.getToolConfig().getObject(localizedName);

    }

}
