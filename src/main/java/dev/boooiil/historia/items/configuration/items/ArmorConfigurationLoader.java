package dev.boooiil.historia.items.configuration.items;

public class ArmorConfigurationLoader extends BaseItemConfigurationLoader<ArmorConfiguration> {

    /**
     * Create a built {@link ArmorConfiguration} object from the configuration file.
     */
    @Override
    public ArmorConfiguration createNew(String armorName) {
        return new ArmorConfiguration(this.configuration.getConfigurationSection(armorName));
    }

}
