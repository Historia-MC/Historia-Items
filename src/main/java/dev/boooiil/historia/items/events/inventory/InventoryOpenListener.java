package dev.boooiil.historia.items.events.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class InventoryOpenListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
