package dev.boooiil.historia.items.events.inventory;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.handlers.inventory.CraftingResult;
import dev.boooiil.historia.items.util.Logging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftItemListener implements Listener {

    // TODO: this all needs to be moved to a handler and
    // CONT: should be refactored. This is a mess.

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {

        System.out.println("CraftItemEvent triggered");

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(event.getWhoClicked().getUniqueId(), false);

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
