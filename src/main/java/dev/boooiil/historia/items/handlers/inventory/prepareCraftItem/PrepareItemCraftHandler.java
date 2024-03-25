package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import org.bukkit.Material;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.crafted.custom.CustomConfiguration;
import dev.boooiil.historia.items.configuration.crafted.tool.ToolConfiguration;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.CraftedItemFactory;

/**
 * The PrepareItemCraftHandler class is responsible for handling the preparation
 * of crafted items within the Historia plugin.
 */
public class PrepareItemCraftHandler {

    /**
     * The PrepareItemCraftEvent to be handled by the PrepareItemCraftHandler.
     */
    private PrepareItemCraftEvent event;

    /**
     * The CraftingInventory to be handled by the PrepareItemCraftHandler.
     */
    private CraftingInventory craftingInventory;

    /**
     * prepare item craft handler default constructor
     */
    PrepareItemCraftHandler() {
    }

    /**
     * Constructs a PrepareItemCraftHandler object with the specified
     * PrepareItemCraftEvent.
     *
     * @param event The PrepareItemCraftEvent to be set as the event variable.
     */
    public PrepareItemCraftHandler(PrepareItemCraftEvent event) {
        this.event = event;
        this.craftingInventory = event.getInventory();

    }

    /**
     * Parses the inventory and gives the result of the crafting operation.
     */
    public void parseInventoryAndGiveResult() {

        BaseItem item = parseInventory();

        if (item == null)
            event.getInventory().setResult(event.getRecipe().getResult());
        else
            event.getInventory().setResult(item.getItemStack());

    }

    /**
     * Parses the inventory and returns the result of the crafting operation.
     *
     * @return The result of the crafting operation.
     */
    private BaseItem parseInventory() {

        PrepareItemCraftInventoryHelper helper = new PrepareItemCraftInventoryHelper(craftingInventory.getMatrix());
        PrepareItemCraftItemHelper itemHelper = new PrepareItemCraftItemHelper(helper);

        itemHelper.doMatch();

        if (itemHelper.getResult() == null)
            return new BaseItem(new ItemStack(Material.AIR));

        if (itemHelper.getResult() instanceof ArmorConfiguration) {
            return CraftedItemFactory.createArmor((ArmorConfiguration) itemHelper.getResult());
        }

        else if (itemHelper.getResult() instanceof WeaponConfiguration) {
            return CraftedItemFactory.createWeapon((WeaponConfiguration) itemHelper.getResult());
        }

        else if (itemHelper.getResult() instanceof ToolConfiguration) {
            return CraftedItemFactory.createTool((ToolConfiguration) itemHelper.getResult());
        }

        else if (itemHelper.getResult() instanceof CustomConfiguration) {
            return CraftedItemFactory.createCustom((CustomConfiguration) itemHelper.getResult());
        }

        else {
            return new BaseItem(event.getRecipe().getResult());
        }

    }

}
