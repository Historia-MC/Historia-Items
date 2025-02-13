package dev.boooiil.historia.items.item.executor;

import java.util.ArrayList;
import java.util.List;

import dev.boooiil.historia.items.util.Logging;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.KyoriUtils;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ItemExecutable implements JSONSerializable {

    public static final PersistentDataType<PersistentDataContainer, ItemExecutable> DATA_TYPE = new ItemExecutable.DataType();

    private List<String> commands;
    // ticks
    private int cooldown;
    private int uses;
    private boolean hasElevation;
    private boolean hasCooldown;

    public ItemExecutable(
            List<String> commands,
            int cooldown,
            int uses,
            boolean hasElevation,
            boolean hasCooldown) {
        this.commands = commands;
        this.cooldown = cooldown;
        this.uses = uses;
        this.hasCooldown = hasCooldown;

    }

    public ItemStack execute(Player player, ItemStack item) {

        for (String command : applyCommandPlaceholder(player)) {

            if (hasElevation)
                Main.server().dispatchCommand(Main.server().getConsoleSender(), command);
            else
                player.performCommand(command);
        }

        this.uses--;

        return item;
    }

    private List<String> applyCommandPlaceholder(Player player) {
        List<String> nCommands = new ArrayList<>();

        for (String command : commands) {

            String nCommand = KyoriUtils.replace(command, "player", player.getName());

            nCommands.add(nCommand);
        }

        return nCommands;
    }

    public List<String> commands() {
        return this.commands;
    }

    public int cooldown() {
        return this.cooldown;
    }

    public int uses() {
        return this.uses;
    }

    public boolean hasElevation() {
        return this.hasElevation;
    }

    public boolean hasCooldown() {
        return this.hasCooldown;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ItemExecutable");
        sb.append(toJSON());

        return sb.toString();
    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromStringList("commands", commands) + ", ");
        sb.append(JSONUtils.fromValue("uses", this.uses) + ", ");
        sb.append(JSONUtils.fromValue("cooldown", this.cooldown) + ", ");
        sb.append(JSONUtils.fromValue("elevated", this.hasElevation) + ", ");
        sb.append(JSONUtils.fromValue("hasCooldown", this.hasCooldown));
        sb.append("}");

        return sb.toString();
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ItemExecutable> {

        private static final NamespacedKey COMMANDS_KEY = Main.getNamespacedKey("commands");
        private static final NamespacedKey USES_KEY = Main.getNamespacedKey("uses");
        private static final NamespacedKey COOLDOWN_KEY = Main.getNamespacedKey("cooldown");
        private static final NamespacedKey ELEVATED_KEY = Main.getNamespacedKey("elevated");

        @Override
        public ItemExecutable fromPrimitive(PersistentDataContainer container, PersistentDataAdapterContext adapterContext) {

            List<String> commands = container.get(COMMANDS_KEY, PersistentDataType.LIST.listTypeFrom(STRING));
            int uses = container.get(USES_KEY, PersistentDataType.INTEGER);
            int cooldown = container.get(COOLDOWN_KEY, PersistentDataType.INTEGER);
            boolean elevated = container.get(ELEVATED_KEY, PersistentDataType.BOOLEAN);

            return new ItemExecutable(commands, cooldown, uses, elevated, false);
        }

        @Override
        public Class<ItemExecutable> getComplexType() {
            return ItemExecutable.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ItemExecutable data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(COMMANDS_KEY, PersistentDataType.LIST.listTypeFrom(STRING), data.commands);
            container.set(USES_KEY, PersistentDataType.INTEGER, data.uses());
            container.set(COOLDOWN_KEY, PersistentDataType.INTEGER, data.cooldown());
            container.set(ELEVATED_KEY, PersistentDataType.BOOLEAN, data.hasElevation());

            return container;
        }
    }
}