package dev.boooiil.historia.items.item;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.core.util.JSONSerializable;

@NullMarked
public interface ItemComponent extends JSONSerializable {

    static ItemComponent fromConfig(String componentKey, ConfigurationSection section) {
        return ItemComponents.getConstructor(componentKey).apply(section);
    }

    ItemData apply();

    ItemData apply(float qualityModifier);

    String getKey();

}
