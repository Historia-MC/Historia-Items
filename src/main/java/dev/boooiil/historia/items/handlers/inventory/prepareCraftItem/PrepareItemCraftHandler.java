package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import org.bukkit.Material;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.ToolConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;
import dev.boooiil.historia.items.crafted.CraftedItemFactory;
import dev.boooiil.historia.items.crafted.weapon.Weapon;

public class PrepareItemCraftHandler {

    PrepareItemCraftEvent event;
    CraftingInventory craftingInventory;

    public PrepareItemCraftHandler(PrepareItemCraftEvent event) {
        this.event = event;
        this.craftingInventory = event.getInventory();

    }

    public void parseInventoryAndGiveResult() {

        BaseItem item = parseInventory();

        if (item == null)
            event.getInventory().setResult(event.getRecipe().getResult());
        else
            event.getInventory().setResult(item.getItemStack());

    }

    private BaseItem parseInventory() {

        PrepareItemCraftInventoryHelper helper = new PrepareItemCraftInventoryHelper(craftingInventory.getMatrix());
        PrepareItemCraftItemHelper itemHelper = new PrepareItemCraftItemHelper(helper);

        itemHelper.doMatch();

        if (itemHelper.getResult() == null)
            return new BaseItem(new ItemStack(Material.AIR), false);

        if (itemHelper.getResult() instanceof ArmorConfiguration) {
            return CraftedItemFactory.createArmor((ArmorConfiguration) itemHelper.getResult());
        }

        else if (itemHelper.getResult() instanceof WeaponConfiguration) {
            return new Weapon((WeaponConfiguration) itemHelper.getResult());
        }

        else if (itemHelper.getResult() instanceof ToolConfiguration) {
            return CraftedItemFactory.createTool((ToolConfiguration) itemHelper.getResult());
        }

        else if (itemHelper.getResult() instanceof CustomItemConfiguration) {
            return CraftedItemFactory.createCustom((CustomItemConfiguration) itemHelper.getResult());
        }

        else {
            return new BaseItem(event.getRecipe().getResult(), false);
        }

    }

}
