package dev.boooiil.historia.items.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

import dev.boooiil.historia.items.handlers.executor.ExecutorTriggerHandler;

public class PlayerJumpListener implements Listener {

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {

        ExecutorTriggerHandler.executeAction(event);

    }

}
