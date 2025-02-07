package dev.boooiil.historia.items.item.data;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.ItemRegistry;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.component.ExecutorComponent;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.items.util.PDCUtils;

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

        int[] triggerIDs = PDCUtils
                .getFromContainer(stack, Main.getNamespacedKey("executor-triggers"), PersistentDataType.INTEGER_ARRAY)
                .orElse(new int[0]);

        for (int triggerId : triggerIDs) {

            Triggers trigger = Triggers.fromId(triggerId);

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
                            ec.executables().get(trigger).hasElevation(),
                            hasCooldown));
        }

        return new ExecutorData(executables);
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

        int[] triggerIDs = executables.keySet().stream().mapToInt(Triggers::getId).toArray();

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("executor-triggers"), PersistentDataType.INTEGER_ARRAY,
                triggerIDs);

        for (Triggers trigger : executables.keySet()) {
            PDCUtils.setInContainer(stack, Main.getNamespacedKey("executor-" + trigger.getLowercase() + "-uses"),
                    PersistentDataType.INTEGER, executables.get(trigger).uses());
        }
    }

    public HashMap<Triggers, ItemExecutable> executables() {
        return this.executables;
    }

}
