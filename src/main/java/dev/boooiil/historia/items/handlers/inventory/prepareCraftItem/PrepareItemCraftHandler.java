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
