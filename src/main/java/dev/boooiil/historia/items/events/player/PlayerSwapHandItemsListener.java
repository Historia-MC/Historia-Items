package dev.boooiil.historia.items.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class PlayerSwapHandItemsListener implements Listener {

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
