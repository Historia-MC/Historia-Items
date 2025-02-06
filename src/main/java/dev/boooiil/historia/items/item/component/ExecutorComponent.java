package dev.boooiil.historia.items.item.component;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ExecutorData;
import dev.boooiil.historia.items.item.types.Actions;
import dev.boooiil.historia.items.util.Logging;

public class ExecutorComponent implements ItemComponent {

    private final List<Actions> actions;
    private final String command;

    public ExecutorComponent(List<Actions> actions, String command) {
        this.actions = actions;
        this.command = command;
    }

    public static ExecutorComponent fromConfig(ConfigurationSection section) {
        List<Actions> actions = new ArrayList<>();
        String command = section.getString("command");

        for (String sAction : section.getStringList("actions")) {

            Actions action = Actions.fromString(sAction);

            if (action == Actions.UNKNOWN) {
                Logging.errorToConsole("Tried to get action", sAction, "from executor:", command);
                continue;
            }

            actions.add(action);

        }

        return new ExecutorComponent(
                actions, command);
    }

    public List<Actions> actions() {
        return this.actions;
    }

    public String command() {
        return this.command;
    }

    @Override
    public void applyDefaultData(ItemStack item) {

        ExecutorData executorData = new ExecutorData(actions, command);
        executorData.apply(item);

    }

    @Override
    public String getKey() {
        return "executor";
    }

}
