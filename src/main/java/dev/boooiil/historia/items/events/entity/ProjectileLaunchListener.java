package dev.boooiil.historia.items.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
