package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.configuration.BaseConfigurationLoader;
import dev.boooiil.historia.items.configuration.ConfigurationFactory;

public class ItemConfigurationFactory<IC extends BaseConfiguration> {

    private final BaseConfigurationLoader<IC> configurationLoader;

    public ItemConfigurationFactory(Class<IC> clazz) {
        this.configurationLoader = ConfigurationFactory.getConfigurationLoader(clazz);
    }

    public BaseConfigurationLoader<IC> getConfigurationLoader() {
        return configurationLoader;
    }

    public IC getObject(String itemName) {
        return configurationLoader.getObject(itemName);
    }

    public boolean isValid(String itemName) {
        return configurationLoader.isValid(itemName);
    }

}
