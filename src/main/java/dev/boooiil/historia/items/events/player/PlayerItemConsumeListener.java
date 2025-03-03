package dev.boooiil.historia.items.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
