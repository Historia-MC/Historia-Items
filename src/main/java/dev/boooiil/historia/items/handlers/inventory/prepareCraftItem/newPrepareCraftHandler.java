package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;

/**
 * The newPrepareCraftHandler class is responsible for handling the preparation
 * of crafted items within the Historia plugin.
 */
public class newPrepareCraftHandler {

    private final PrepareItemCraftEvent event;
    private final CraftingInventory craftingInventory;

    newPrepareCraftHandler() {
        this.event = null;
        this.craftingInventory = null;
    }

    /**
     * Constructs a newPrepareCraftHandler object with the specified
     * PrepareItemCraftEvent.
     *
     * @param event The PrepareItemCraftEvent to be set as the event variable.
     */
    newPrepareCraftHandler(PrepareItemCraftEvent event) {
        this.event = event;
        this.craftingInventory = event.getInventory();

    }

    /**
     * Parses the inventory and gives the result of the crafting operation.
     */
    private BaseItemConfiguration parseInventory() {

        return null;

    }

    /**
     * Handles the preparation of a crafted item.
     */
    public void getResult() {

    }
}
