package dev.boooiil.historia.items.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
