package dev.boooiil.historia.items.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class EntityDropItemListener implements Listener {

    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
