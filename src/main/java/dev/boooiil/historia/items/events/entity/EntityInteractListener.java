package dev.boooiil.historia.items.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
