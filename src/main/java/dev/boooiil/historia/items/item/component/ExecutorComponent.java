package dev.boooiil.historia.items.item.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ExecutorData;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.items.util.Logging;

public class ExecutorComponent implements ItemComponent {

    private HashMap<Triggers, ItemExecutable> executables;

    public ExecutorComponent(HashMap<Triggers, ItemExecutable> executables) {
        this.executables = executables;
    }

    public static ExecutorComponent fromConfig(ConfigurationSection section) {
        HashMap<Triggers, ItemExecutable> executables = new HashMap<>();
        List<Triggers> triggers = new ArrayList<>();

        // get valid triggers from the config
        for (String sTrigger : section.getKeys(false)) {

            Triggers trigger = Triggers.fromString(sTrigger);

            if (trigger == Triggers.UNKNOWN) {
                Logging.errorToConsole("Tried to get trigger", sTrigger, "from executor but it does not exist.");
                continue;
            }

            triggers.add(trigger);

        }

        for (Triggers trigger : triggers) {

            ConfigurationSection triggerSection = section.getConfigurationSection(trigger.getLowercase());

            List<String> commands = triggerSection.getStringList("commands");
            Integer cooldown = triggerSection.getInt("cooldown");
            Integer uses = triggerSection.getInt("uses");

            executables.put(trigger, new ItemExecutable(commands, cooldown, uses, false));

        }

        return new ExecutorComponent(executables);
    }

    public HashMap<Triggers, ItemExecutable> executables() {
        return this.executables;
    }

    @Override
    public void applyDefaultData(ItemStack item) {

        ExecutorData executorData = new ExecutorData(executables);
        executorData.apply(item);

    }

    @Override
    public String getKey() {
        return "executor";
    }

}
