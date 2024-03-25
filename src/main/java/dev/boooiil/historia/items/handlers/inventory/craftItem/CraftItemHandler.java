package dev.boooiil.historia.items.handlers.inventory.craftItem;

import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.handlers.inventory.CraftingResult;
import dev.boooiil.historia.items.util.Logging;

/**
 * The CraftItemHandler class is responsible for handling the crafting of items
 * within the Historia plugin.
 */
public class CraftItemHandler {

    private final HistoriaPlayer historiaPlayer;
    private CraftItemEvent event;

    /**
     * Constructs a CraftItemHandler object with the specified CraftItemEvent.
     *
     * @param event The CraftItemEvent to be set as the event variable.
     */
    public CraftItemHandler(CraftItemEvent event) {
        this.event = event;
        this.historiaPlayer = PlayerStorage.getPlayer(event.getWhoClicked().getUniqueId(), false);
    }

    /**
     * Handles the crafting of an item.
     */
    public void handle() {
        ItemStack item = event.getClickedInventory().getItem(0);
        BaseItem baseItem = new BaseItem(item);

        Logging.debugToConsole("[onCraftResult] Crafting a " + baseItem.getDisplayName());

        if (!baseItem.isValid())
            return;

        if (baseItem.isValid()) {

            Logging.debugToConsole("[onCraftItem] Crafting a valid item");

            CraftingResult craftingResult = new CraftingResult(
                    event.getInventory(),
                    item,
                    ItemConfigurationRegistry.get(baseItem.getID()),
                    historiaPlayer);

            if (!craftingResult.getCraftedItem().canCraft(historiaPlayer.getProficiency().getName()))
                event.setCancelled(true);
            else
                craftingResult.generateRandomModifiers();

        }

    }

}
