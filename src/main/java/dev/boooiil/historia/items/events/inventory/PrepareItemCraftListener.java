package dev.boooiil.historia.items.events.inventory;

import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftHandler;
import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftInventoryHelper;
import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftItemHelper;
import dev.boooiil.historia.items.util.Logging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(
                event.getInventory().getContents());

        // should be all we need moving forward
        // remove all other code
        ItemStack resultItem = new PrepareItemCraftHandler(event).parseInventoryAndGiveResult();

        Logging.debugToConsole("[PICE] Pattern: " + inspector.getPattern());
        Logging.debugToConsole("[PICE] Materials: " + inspector.getMaterials());
        Logging.debugToConsole("[PICE] Full Materials: " + inspector.getFullMaterials());

        PrepareItemCraftItemHelper cim = new PrepareItemCraftItemHelper(inspector);
        cim.doMatch();

        if (cim.getResult() != null)
            resultItem = cim.getResult();
        else if (event.getRecipe() != null)
            resultItem = event.getRecipe().getResult();
        else
            resultItem = null;

        if (resultItem != null) {
            if (resultItem.getItemMeta() != null) {

                Logging.debugToConsole(
                        "Result: " + resultItem.getItemMeta().getLocalizedName() + " " + resultItem.getAmount());

            } else {

                Logging.debugToConsole("Result: " + resultItem.getType() + " " + resultItem.getAmount());

            }
        } else
            Logging.debugToConsole("Result: null");

        event.getInventory().setResult(resultItem);

    }

}
