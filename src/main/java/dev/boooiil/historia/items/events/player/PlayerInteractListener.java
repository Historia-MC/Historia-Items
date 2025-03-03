package dev.boooiil.historia.items.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
