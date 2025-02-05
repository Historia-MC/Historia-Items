package dev.boooiil.historia.items.events.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev.boooiil.historia.items.util.Logging;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();

        Logging.debugToConsole(attacker.getName(), defender.getName());
        Logging.debugToConsole(attacker.getName(), "attacked with " + event.getDamage(), "damage.");

    }
}
