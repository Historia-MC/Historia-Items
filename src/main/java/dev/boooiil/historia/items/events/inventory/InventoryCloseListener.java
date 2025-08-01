package dev.boooiil.historia.items.events.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
