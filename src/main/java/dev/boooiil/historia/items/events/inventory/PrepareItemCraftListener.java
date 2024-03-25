package dev.boooiil.historia.items.events.inventory;

import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

/**
 * The PrepareItemCraftListener class is responsible for listening for
 * PrepareItemCraftEvents
 * within the Historia plugin.
 */
public class PrepareItemCraftListener implements Listener {

    /**
     * prepare item craft listener default constructor
     */
    public PrepareItemCraftListener() {
    }

    /**
     * Listens for PrepareItemCraftEvents and handles them.
     *
     * @param event The PrepareItemCraftEvent to be handled.
     */
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {

        new PrepareItemCraftHandler(event).parseInventoryAndGiveResult();

    }

}
