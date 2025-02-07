package dev.boooiil.historia.items.item.data;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorCompletionService;

import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.ItemRegistry;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.component.ExecutorComponent;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.PDCUtils;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.UseCooldown;

public class ExecutorData implements ItemData {

    private final HashMap<Triggers, ItemExecutable> executables;

    public ExecutorData(
            HashMap<Triggers, ItemExecutable> executables) {
        this.executables = executables;
    };

    public static ExecutorData fromStack(ItemStack stack) {

        String id = PDCUtils.getFromContainer(stack, Main.getNamespacedKey("config-id"), PersistentDataType.STRING)
                .orElse("");

        HashMap<Triggers, ItemExecutable> executables = new HashMap<>();
        HistoriaItem historiaItem = ItemRegistry.get(id);
        ExecutorComponent ec = (ExecutorComponent) historiaItem.getComponentHolder().get("executor");
        Set<Triggers> triggers = ec.executables().keySet();

        for (Triggers trigger : triggers) {

            int uses = PDCUtils.getFromContainer(stack,
                    Main.getNamespacedKey("executor-" + trigger.getLowercase() + "-uses"), PersistentDataType.INTEGER)
                    .orElse(1);

            boolean hasCooldown = Main.isTesting ? false : stack.getItemMeta().hasUseCooldown();

            executables.put(
                    trigger,
                    new ItemExecutable(
                            ec.executables().get(trigger).commands(),
                            ec.executables().get(trigger).cooldown(),
                            uses,
                            hasCooldown));
        }

        return new ExecutorData(executables);
    }

    public ItemStack execute(ItemStack item, Triggers trigger) {
        if (executables.containsKey(trigger)) {
            ItemExecutable itemExecutable = executables.get(trigger);

            if (!itemExecutable.hasCooldown()) {
                return itemExecutable.execute(item);
            }
        }

        return item;
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {

        String id = PDCUtils.getFromContainer(stack, Main.getNamespacedKey("config-id"), PersistentDataType.STRING)
                .orElse("");

        HistoriaItem historiaItem = ItemRegistry.get(id);
        ExecutorComponent ec = (ExecutorComponent) historiaItem.getComponentHolder().get("executor");
        Set<Triggers> triggers = ec.executables().keySet();

        for (Triggers trigger : triggers) {
            PDCUtils.setInContainer(stack, Main.getNamespacedKey("executor-" + trigger.getLowercase() + "-uses"),
                    PersistentDataType.INTEGER, executables.get(trigger).uses());
        }
    }

    public HashMap<Triggers, ItemExecutable> executables() {
        return this.executables;
    }

}
