package dev.boooiil.historia.items.item.data;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.util.PDCUtils;
import dev.boooiil.historia.core.util.JSONUtils;

public class RunnableData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, RunnableData> DATA_TYPE = new RunnableData.DataType();

    public static final NamespacedKey KEY = Main.getNamespacedKey("runnable-data");

    private int ticks;
    private String command;
    private String permission;

    public RunnableData(
            int ticks,
            String command,
            String permission) {

    }

    public static RunnableData fromStack(ItemStack stack) {
        return PDCUtils.getFromComplexContainer(stack, RunnableData.KEY, RunnableData.DATA_TYPE)
                .orElse(new RunnableData(-1, "", ""));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {
        PDCUtils.setInComplexContainer(stack, RunnableData.KEY, RunnableData.DATA_TYPE, this);
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, RunnableData> {

        private static final NamespacedKey TICKS_KEY = Main.getNamespacedKey("ticks");
        private static final NamespacedKey COMMAND_KEY = Main.getNamespacedKey("command");
        private static final NamespacedKey PERMISSION_KEY = Main.getNamespacedKey("permission");

        @Override
        public RunnableData fromPrimitive(PersistentDataContainer container,
                PersistentDataAdapterContext adapterContext) {

            int ticks = container.get(TICKS_KEY, PersistentDataType.INTEGER);
            String command = container.get(COMMAND_KEY, PersistentDataType.STRING);
            String permission = container.get(PERMISSION_KEY, PersistentDataType.STRING);

            return new RunnableData(ticks, command, permission);
        }

        @Override
        public Class<RunnableData> getComplexType() {
            return RunnableData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(RunnableData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(TICKS_KEY, PersistentDataType.INTEGER, data.ticks);
            container.set(COMMAND_KEY, PersistentDataType.STRING, data.command);
            container.set(PERMISSION_KEY, PersistentDataType.STRING, data.permission);

            return container;
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("RunnableData");
        sb.append("{");
        sb.append(JSONUtils.fromValue("ticks", ticks) + ", ");
        sb.append(JSONUtils.fromValue("command", command) + ", ");
        sb.append(JSONUtils.fromValue("permission", permission));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("ticks", ticks) + ", ");
        sb.append(JSONUtils.fromValue("command", command) + ", ");
        sb.append(JSONUtils.fromValue("permission", permission));
        sb.append("}");

        return sb.toString();

    }

}
