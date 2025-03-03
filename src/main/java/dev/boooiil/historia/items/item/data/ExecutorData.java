package dev.boooiil.historia.items.item.data;

import java.util.HashMap;

import dev.boooiil.historia.items.util.HILogger;
import dev.boooiil.historia.items.util.KyoriUtils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import org.jspecify.annotations.NullMarked;

public class ExecutorData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ExecutorData> DATA_TYPE = new ExecutorData.DataType();
    public static final NamespacedKey KEY = HistoriaItems.getNamespacedKey("executor");

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

    public void execute(HumanEntity humanEntity, ItemStack item, Triggers trigger) {
        if (executables.containsKey(trigger)) {
            ItemExecutable itemExecutable = executables.get(trigger);

            // if not on cooldown
            if (!itemExecutable.hasCooldown()) {

                itemExecutable.execute(humanEntity, item);

                if (itemExecutable.uses() <= 0) {
                    HILogger.debugToConsole("Removing trigger " + trigger + " from item "
                            + KyoriUtils.content(item.getItemMeta().displayName()));
                    executables.remove(trigger);

                    if (executables.keySet().isEmpty()) {
                        HILogger.debugToConsole("Removing item "
                                + KyoriUtils.content(item.getItemMeta().displayName()));
                        humanEntity.getInventory().remove(item);

                        return; // data does not need to be written
                    }
                }

                // set cooldown
                if (itemExecutable.uses() > 0) {
                    HILogger.debugToConsole(
                            "Setting cooldown for item " + KyoriUtils.content(item.getItemMeta().displayName())
                                    + " to " + itemExecutable.cooldown());

                    // MockBukkit@1.21.4 Unimplemented
                    if (!HistoriaItems.isTesting) {
                        humanEntity.setCooldown(item, itemExecutable.cooldown());
                    }
                    System.out.println(this.toString());
                }

                HILogger.debugToConsole("before",
                        item.getItemMeta().getPersistentDataContainer().get(HistoriaItems.getNamespacedKey("executor"),
                                ExecutorData.DATA_TYPE).toString());

                writeData(item);

                HILogger.debugToConsole("after",
                        item.getItemMeta().getPersistentDataContainer().get(HistoriaItems.getNamespacedKey("executor"),
                                ExecutorData.DATA_TYPE).toString());
            }
        }

        else {
            HILogger.errorToConsole(
                    "Player " + humanEntity.getName() + " tried to execute trigger " + trigger + " on item "
                            + KyoriUtils.content(item.getItemMeta().displayName()) + " but no executable was found.");
            HILogger.errorToConsole("Possible executables: " + executables.keySet());
        }

    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, ExecutorData.KEY,
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
        sb.append(JSONUtils.fromMap("executables", executables, true));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        if (executables.entrySet().size() == 0)
            return "{}";

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromMap("executables", executables));
        sb.append("}");

        return sb.toString();

    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ExecutorData> {

        private static final NamespacedKey EXECUTABLES_KEY = HistoriaItems.getNamespacedKey("executables");

        @Override
        public ExecutorData fromPrimitive(PersistentDataContainer container,
                PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer executablesContainer = container.get(EXECUTABLES_KEY,
                    PersistentDataType.TAG_CONTAINER);

            HashMap<Triggers, ItemExecutable> executables = new HashMap<>();
            for (NamespacedKey key : executablesContainer.getKeys()) {
                Triggers trigger = Triggers.fromString(key.getKey());

                HILogger.debugToConsole("loading key: " + key);
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
                executablesContainer.set(HistoriaItems.getNamespacedKey(trigger.getLowercase()),
                        ItemExecutable.DATA_TYPE, data.executables.get(trigger));
            }

            container.set(EXECUTABLES_KEY, PersistentDataType.TAG_CONTAINER, executablesContainer);

            return container;
        }
    }
}
