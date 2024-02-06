package dev.boooiil.historia.items.events.inventory;

import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {

        new PrepareItemCraftHandler(event).parseInventoryAndGiveResult();

    }

}
