package dev.boooiil.historia.items.item.data;

import java.util.HashMap;

import dev.boooiil.historia.items.util.Logging;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import org.jspecify.annotations.NullMarked;

public class ExecutorData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ExecutorData> DATA_TYPE = new ExecutorData.DataType();
    public static final NamespacedKey KEY = Main.getNamespacedKey("executor-data");

    // private String id;
    private final HashMap<Triggers, ItemExecutable> executables;

    public ExecutorData(
            // String id,
            HashMap<Triggers, ItemExecutable> executables) {
        this.executables = executables;
    };

    public static ExecutorData fromStack(ItemStack stack) {

        return PDCUtils.getFromComplexContainer(stack, ExecutorData.KEY,
                ExecutorData.DATA_TYPE).orElse(new ExecutorData(new HashMap<>()));

    }

    public static ExecutorData defaults() {
        return new ExecutorData(new HashMap<>());
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

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ExecutorData> {

        private static final NamespacedKey EXECUTABLES_KEY = Main.getNamespacedKey("executables");

        @Override
        public ExecutorData fromPrimitive(PersistentDataContainer container,
                PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer executablesContainer = container.get(EXECUTABLES_KEY,
                    PersistentDataType.TAG_CONTAINER);

            HashMap<Triggers, ItemExecutable> executables = new HashMap<>();
            for (NamespacedKey key : executablesContainer.getKeys()) {
                Triggers trigger = Triggers.fromString(key.getKey());

                Logging.debugToConsole("loading key: " + key);
                ItemExecutable executable = executablesContainer.get(key, ItemExecutable.DATA_TYPE);

                executables.put(trigger, executable);
            }

            return new ExecutorData(executables);
        }

        @Override
        public Class<ExecutorData> getComplexType() {
            return ExecutorData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ExecutorData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();
            PersistentDataContainer executablesContainer = adapterContext.newPersistentDataContainer();

            for (Triggers trigger : data.executables().keySet()) {
                executablesContainer.set(Main.getNamespacedKey(trigger.getLowercase()),
                        ItemExecutable.DATA_TYPE, data.executables.get(trigger));
            }

            container.set(EXECUTABLES_KEY, PersistentDataType.TAG_CONTAINER, executablesContainer);

            return container;
        }
    }
}
