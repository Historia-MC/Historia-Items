package dev.boooiil.historia.items.item;

import dev.boooiil.historia.items.item.component.ArmorComponent;
import dev.boooiil.historia.items.item.component.ToolComponent;
import dev.boooiil.historia.items.item.component.WeaponComponent;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@NullMarked
public class ItemComponents {
    private static final Map<String, Function<ConfigurationSection, ? extends ItemComponent>> ALL_COMPONENTS = new HashMap<>();

    public static Set<String> allKeys() {
        return ALL_COMPONENTS.keySet();
    }

    public static <T extends ItemComponent> void register(String key, Function<ConfigurationSection, T> supplier) {
        ALL_COMPONENTS.put(key, supplier);
    }

    static Function<ConfigurationSection, ? extends ItemComponent> getConstructor(String componentKey) {
        return ALL_COMPONENTS.get(componentKey);
    }

    static {
        register("tool", ToolComponent::fromConfig);
        register("weapon", WeaponComponent::fromConfig);
        register("armor", ArmorComponent::fromConfig);
    }
}
