package dev.boooiil.historia.items.configuration.items;

public class ItemConfigurationLoaderFactory {

    public static ArmorConfigurationLoader getArmorConfigurationLoader() {
        return new ArmorConfigurationLoader();
    }

    public static WeaponConfigurationLoader getWeaponConfigurationLoader() {
        return new WeaponConfigurationLoader();
    }

    public static CustomItemConfigurationLoader getCustomItemConfigurationLoader() {
        return new CustomItemConfigurationLoader();
    }

    public static ToolConfigurationLoader getToolConfigurationLoader() {
        return new ToolConfigurationLoader();
    }

}
