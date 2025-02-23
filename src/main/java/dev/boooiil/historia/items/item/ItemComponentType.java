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
    private final Supplier<? extends ItemData> defaultData;

    public ItemComponentType(
            Function<ConfigurationSection, T> fromConfig,
            Supplier<? extends ItemData> defaultData
    ) {
        this.fromConfig = fromConfig;
        this.defaultData = defaultData;
    }

    public T fromConfig(ConfigurationSection section) {
        return fromConfig.apply(section);
    }

    public ItemData getData() {
        return defaultData.get();
    }

    static {
        ItemComponentRegistry.register("tool", new ItemComponentType<>(
                ToolComponent::fromConfig,
                () -> new ToolData(1, 1, 1, 1)));

        ItemComponentRegistry.register("weapon", new ItemComponentType<>(
                WeaponComponent::fromConfig,
                () -> new WeaponData(1)));

        ItemComponentRegistry.register("armor", new ItemComponentType<>(
                ArmorComponent::fromConfig,
                () -> new ArmorData(1, 1)));

        ItemComponentRegistry.register("executor", new ItemComponentType<>(
                ExecutorComponent::fromConfig,
                () -> new ExecutorData(new HashMap<>())));

        ItemComponentRegistry.register("runnable", new ItemComponentType<>(
                RunnableComponent::fromConfig,
                () -> new RunnableData(0, "", "")));
    }
}
