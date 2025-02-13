package dev.boooiil.historia.items.item.executor;

import java.util.ArrayList;
import java.util.List;

import dev.boooiil.historia.items.item.data.WeaponData;
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
import dev.boooiil.historia.items.util.Logging;

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

    private static class DataType implements PersistentDataType<PersistentDataContainer, ItemExecutable> {

        @Override
        public @NotNull ItemExecutable fromPrimitive(@NotNull PersistentDataContainer container,
                @NotNull PersistentDataAdapterContext adapterContext) {

            List<String> commands = new ArrayList<>();
            PersistentDataContainer commandContainer = container.get(Main.getNamespacedKey("commands"),
                    PersistentDataType.TAG_CONTAINER);

            for (NamespacedKey key : commandContainer.getKeys()) {
                commands.add(container.get(key, PersistentDataType.STRING));
            }

            int uses = container.get(Main.getNamespacedKey("uses"), PersistentDataType.INTEGER);
            int cooldown = container.get(Main.getNamespacedKey("cooldown"), PersistentDataType.INTEGER);
            boolean elevated = container.get(Main.getNamespacedKey("elevated"), PersistentDataType.BOOLEAN);

            return new ItemExecutable(commands, cooldown, uses, elevated, false);
        }

        @Override
        public @NotNull Class<ItemExecutable> getComplexType() {
            return ItemExecutable.class;
        }

        @Override
        public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public @NotNull PersistentDataContainer toPrimitive(@NotNull ItemExecutable data,
                @NotNull PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();
            PersistentDataContainer commandContainer = adapterContext.newPersistentDataContainer();

            for (int i = 0; i < data.commands().size(); i++) {

                commandContainer.set(Main.getNamespacedKey("command_" + i), PersistentDataType.STRING,
                        data.commands().get(i));
            }

            container.set(Main.getNamespacedKey("commands"),
                    PersistentDataType.TAG_CONTAINER, commandContainer);
            container.set(Main.getNamespacedKey("uses"), PersistentDataType.INTEGER, data.uses());
            container.set(Main.getNamespacedKey("cooldown"), PersistentDataType.INTEGER, data.cooldown());
            container.set(Main.getNamespacedKey("elevated"), PersistentDataType.BOOLEAN, data.hasElevation());

            return container;
        }
    }
}