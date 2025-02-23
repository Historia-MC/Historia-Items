package dev.boooiil.historia.items.item;

import dev.boooiil.historia.items.item.component.*;
import dev.boooiil.historia.items.item.data.*;
import dev.boooiil.historia.items.registry.ItemComponentRegistry;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemComponentType<T extends ItemComponent> {

    private final Function<ConfigurationSection, T> fromConfig;

    public ItemComponentType(
            Function<ConfigurationSection, T> fromConfig
    ) {
        this.fromConfig = fromConfig;
    }

    public T fromConfig(ConfigurationSection section) {
        return fromConfig.apply(section);
    }

    static {
        ItemComponentRegistry.register("tool", new ItemComponentType<>(ToolComponent::fromConfig));
        ItemComponentRegistry.register("weapon", new ItemComponentType<>(WeaponComponent::fromConfig));
        ItemComponentRegistry.register("armor", new ItemComponentType<>(ArmorComponent::fromConfig));
        ItemComponentRegistry.register("executor", new ItemComponentType<>(ExecutorComponent::fromConfig));
        ItemComponentRegistry.register("runnable", new ItemComponentType<>(RunnableComponent::fromConfig));
    }
}
