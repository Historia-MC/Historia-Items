package dev.boooiil.historia.items.registry;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.ItemComponentType;
import dev.boooiil.historia.items.util.HILogger;

import java.util.HashMap;
import java.util.Set;

public abstract class ItemComponentRegistry {
    private static final HashMap<String, ItemComponentType<? extends ItemComponent>> registry = new HashMap<>();

    public static void register(String key, ItemComponentType<? extends ItemComponent> componentType) {
        key = key.toLowerCase();
        HILogger.debugToConsole("Registering " + key + " to item component type registry.");
        registry.put(key, componentType);
    }

    public static boolean contains(String key) {
        key = key.toLowerCase();
        return registry.containsKey(key);
    }

    public static ItemComponentType<? extends ItemComponent> get(String key) {
        key = key.toLowerCase();
        HILogger.debugToConsole("Obtaining:", key, "from registry.");
        return registry.get(key);
    }

    public static Set<String> keySet() {
        return registry.keySet();
    }
}
