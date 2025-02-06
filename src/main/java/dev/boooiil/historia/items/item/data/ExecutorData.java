package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.types.Actions;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.event.HoverEvent.Action;

public class ExecutorData implements ItemData {

    private List<Actions> actions;
    private String command;

    public ExecutorData(
            List<Actions> actions,
            String command) {
        this.actions = actions;
        this.command = command;
    }

    public static ExecutorData fromStack(ItemStack stack) {
        List<Actions> actions = new ArrayList<>();

        int[] pdcActions = PDCUtils
                .getFromContainer(stack, Main.getNamespacedKey("executor-actions"), PersistentDataType.INTEGER_ARRAY)
                .orElse(new int[] {});

        String command = PDCUtils
                .getFromContainer(stack, Main.getNamespacedKey("executor-command"), PersistentDataType.STRING)
                .orElse("");

        for (int i : pdcActions) {
            Actions action = Actions.fromId(i);

            if (action == Actions.UNKNOWN) {
                Logging.errorToConsole("Tried to get action with id: " + i, "that did not exist, skipping.");
                continue;
            }

            actions.add(action);
        }

        return new ExecutorData(actions, command);
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {

        int[] intActions = actions.stream().mapToInt(Actions::getId).toArray();

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("executor-actions"), PersistentDataType.INTEGER_ARRAY,
                intActions);
        PDCUtils.setInContainer(stack, Main.getNamespacedKey("executor-command"), PersistentDataType.STRING, command);

    }

    public List<Actions> actions() {
        return this.actions;
    }

    public String command() {
        return this.command;
    }

}
