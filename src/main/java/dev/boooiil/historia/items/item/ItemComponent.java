package dev.boooiil.historia.items.item;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ItemComponent {

    static ItemComponent fromConfig(String componentKey, ConfigurationSection section) {
        return ItemComponents.getConstructor(componentKey).apply(section);
    }

    void applyDefaultData(ItemStack item);

    String getKey();
}
