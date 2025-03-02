package dev.boooiil.historia.items.item;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.component.*;
import dev.boooiil.historia.items.item.data.*;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemComponentType<T extends ItemComponent> {

        private final Function<ConfigurationSection, T> fromConfig;
        private final Supplier<? extends ItemData> defaultData;

        public ItemComponentType(
                        Function<ConfigurationSection, T> fromConfig,
                        Supplier<? extends ItemData> defaultData) {
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
                HistoriaItems.COMPONENT_REGISTRY.register(
                                HistoriaItems.getNamespacedKey("tool"),
                                new ItemComponentType<>(
                                                ToolComponent::fromConfig,
                                                () -> new ToolData(1, 1, 1, 1)));

                HistoriaItems.COMPONENT_REGISTRY.register(
                                HistoriaItems.getNamespacedKey("weapon"),
                                new ItemComponentType<>(
                                                WeaponComponent::fromConfig,
                                                () -> new WeaponData(1)));

                HistoriaItems.COMPONENT_REGISTRY.register(
                                HistoriaItems.getNamespacedKey("armor"),
                                new ItemComponentType<>(
                                                ArmorComponent::fromConfig,
                                                () -> new ArmorData(1, 1)));

                HistoriaItems.COMPONENT_REGISTRY.register(
                                HistoriaItems.getNamespacedKey("executor"),
                                new ItemComponentType<>(
                                                ExecutorComponent::fromConfig,
                                                () -> new ExecutorData(new HashMap<>())));

                HistoriaItems.COMPONENT_REGISTRY.register(
                                HistoriaItems.getNamespacedKey("runnable"),
                                new ItemComponentType<>(
                                                RunnableComponent::fromConfig,
                                                () -> new RunnableData(0, "", "")));

                HistoriaItems.COMPONENT_REGISTRY.register(
                                HistoriaItems.getNamespacedKey("enchant"),
                                new ItemComponentType<>(
                                                EnchantComponent::fromConfig,
                                                () -> new EnchantData(new HashMap<>())));
        }
}
