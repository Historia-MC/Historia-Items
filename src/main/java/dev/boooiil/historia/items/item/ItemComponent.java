package dev.boooiil.historia.items.item;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ItemComponent {

    static ItemComponent fromConfig(String componentKey, ConfigurationSection section) {
        return ItemComponents.getConstructor(componentKey).apply(section);
    }

    ItemData applyWithDefault();

    ItemData applyWithQuality(float qualityModifier);

    String getKey();
}
