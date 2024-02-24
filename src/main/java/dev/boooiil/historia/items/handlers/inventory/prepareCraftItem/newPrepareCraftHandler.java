package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;

public class newPrepareCraftHandler {

    private final PrepareItemCraftEvent event;
    private final CraftingInventory craftingInventory;

    newPrepareCraftHandler(PrepareItemCraftEvent event) {
        this.event = event;
        this.craftingInventory = event.getInventory();

    }

    private BaseItemConfiguration parseInventory() {

        return null;

    }

    public void getResult() {

    }
}
