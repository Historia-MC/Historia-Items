package dev.boooiil.historia.items.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class PlayerToggleSprintListener implements Listener {

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
