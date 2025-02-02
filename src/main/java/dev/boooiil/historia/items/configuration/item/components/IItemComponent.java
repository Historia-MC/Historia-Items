package dev.boooiil.historia.items.configuration.item.components;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public interface IItemComponent {

    public void processConfiguration(ConfigurationSection section);

    public void processConfiguration(YamlConfiguration configuration);

    public void setDefaultsToMeta(ItemStack item);

}
