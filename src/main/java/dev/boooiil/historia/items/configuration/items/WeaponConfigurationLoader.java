package dev.boooiil.historia.items.configuration.items;

/**
 * It's a class that gets information from a configuration file.
 */
public class WeaponConfigurationLoader extends BaseItemConfigurationLoader<WeaponConfiguration> {

    /**
     * Used to create a new instance of Weapon.
     * 
     * @param weaponName - Name of the weapon to create.
     * @return Returns a Weapon object.
     */
    public WeaponConfiguration createNew(String weaponName) {

        return new WeaponConfiguration(this.configuration.getConfigurationSection(weaponName));

    }

}