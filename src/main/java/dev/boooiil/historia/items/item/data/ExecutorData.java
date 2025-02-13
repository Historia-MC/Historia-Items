package dev.boooiil.historia.items.item.data;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.ItemRegistry;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.component.ExecutorComponent;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.PDCUtils;

public class ExecutorData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ExecutorData> DATA_TYPE = new ExecutorData.DataType();

    // private String id;
    private final HashMap<Triggers, ItemExecutable> executables;

    public ExecutorData(
            // String id,
            HashMap<Triggers, ItemExecutable> executables) {
        this.executables = executables;
    };

    public static ExecutorData fromStack(ItemStack stack) {

        return PDCUtils.getFromComplexContainer(stack, Main.getNamespacedKey("executor-data"),
                ExecutorData.DATA_TYPE).orElse(new ExecutorData(new HashMap<>()));

    }

    public void execute(Player player, ItemStack item, Triggers trigger) {
        if (executables.containsKey(trigger)) {
            ItemExecutable itemExecutable = executables.get(trigger);

            // if not on cooldown
            if (!itemExecutable.hasCooldown()) {

                itemExecutable.execute(player, item);

                // set cooldown
                if (itemExecutable.uses() > 0) {
                    player.setCooldown(item, itemExecutable.cooldown());
                    writeData(item);
                }

                // remove trigger from item
                else {
                    executables.remove(trigger);

                    if (executables.keySet().isEmpty()) {
                        player.getInventory().remove(item);
                    } else {

                    }

                }
            }
        }

    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, Main.getNamespacedKey("executor-data"),
                ExecutorData.DATA_TYPE, this);
    }

    public String id() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public HashMap<Triggers, ItemExecutable> executables() {
        return this.executables;
    }

    @Override
    public String toString() {

        if (executables.entrySet().size() == 0)
            return "ExecutorComponent{}";

        StringBuilder sb = new StringBuilder();

        sb.append("ExecutorComponent");
        sb.append("{");
        sb.append(JSONUtils.fromMapAsString("executables", executables));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        if (executables.entrySet().size() == 0)
            return "{}";

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromMapAsJSON("executables", executables));
        sb.append("}");

        return sb.toString();

    }

    private static class DataType implements PersistentDataType<PersistentDataContainer, ExecutorData> {

        @Override
        public @NotNull ExecutorData fromPrimitive(@NotNull PersistentDataContainer container,
                @NotNull PersistentDataAdapterContext adapterContext) {

            HashMap<Triggers, ItemExecutable> executables = new HashMap<>();

            // I was thinking about making ItemExecutable also be a persistent data
            // container type
            // so that we could just store
            // PDC.add(<TRIGGER_STR, ItemExecutableType, ItemExecutable);
            // and then iterate over the container keys if possible.

            // for (String key : container.getKeys())

            PersistentDataContainer triggerContainer = container.get(Main.getNamespacedKey("triggers"),
                    PersistentDataType.TAG_CONTAINER);

            for (NamespacedKey key : triggerContainer.getKeys()) {
                Triggers trigger = Triggers.fromString(key.asMinimalString());

                ItemExecutable executable = container.get(key, ItemExecutable.DATA_TYPE);

                executables.put(trigger, executable);
            }

            return new ExecutorData(executables);
        }

        @Override
        public @NotNull Class<ExecutorData> getComplexType() {
            return ExecutorData.class;
        }

        @Override
        public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public @NotNull PersistentDataContainer toPrimitive(@NotNull ExecutorData data,
                @NotNull PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();
            PersistentDataContainer triggerContainer = adapterContext.newPersistentDataContainer();

            for (Triggers trigger : data.executables().keySet()) {

                triggerContainer.set(Main.getNamespacedKey(trigger.getLowercase()),
                        ItemExecutable.DATA_TYPE, data.executables.get(trigger));

            }

            container.set(Main.getNamespacedKey("triggers"), PersistentDataType.TAG_CONTAINER, triggerContainer);

            return container;
        }
    }
}
