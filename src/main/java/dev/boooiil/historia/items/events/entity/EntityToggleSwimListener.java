package dev.boooiil.historia.items.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class EntityToggleSwimListener implements Listener {

    @EventHandler
    public void onEntityToggleSwim(EntityToggleSwimEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
