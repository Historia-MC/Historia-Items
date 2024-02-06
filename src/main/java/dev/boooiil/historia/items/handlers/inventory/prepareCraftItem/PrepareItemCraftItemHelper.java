package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import dev.boooiil.historia.items.configuration.ConfigurationLoader;
import dev.boooiil.historia.items.items.craftable.CraftedItem;
import dev.boooiil.historia.items.util.Logging;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PrepareItemCraftItemHelper {

    private ItemStack result;
    private final List<CraftedItem> matchingItems;
    private final ArrayList<String> materials;
    private final ArrayList<String> fullMaterials;

    public PrepareItemCraftItemHelper(PrepareItemCraftInventoryHelper inspector) {

        ArrayList<String> patterns = inspector.getPattern();
        materials = inspector.getMaterials();
        fullMaterials = inspector.getFullMaterials();

        // Getting all the items that match the pattern.
        matchingItems = ConfigurationLoader.getArmorConfig().getAllMatchingShape(patterns);
        matchingItems.addAll(ConfigurationLoader.getWeaponConfig().getAllMatchingShape(patterns));
        matchingItems.addAll(ConfigurationLoader.getCustomItemConfig().getAllMatchingShape(materials));

    }

    public void doMatch() {

        if (!matchingItems.isEmpty()) {

            for (CraftedItem item : matchingItems) {

                Logging.debugToConsole("[CIM] Item: " + item.getItemStack().getItemMeta().getLocalizedName());

                if (item.isShapeDependent()) {

                    result = getItemMatchingMaterials(item, materials);

                } else {

                    result = getItemMatchingMaterials(item, fullMaterials);

                }

                if (result != null)
                    break;

            }

        }

    }

    public ItemStack getResult() {
        return result;
    }

    private ItemStack getItemMatchingMaterials(CraftedItem item, List<String> materials) {
        String replace = "LOW_|MEDIUM_|HIGH_";

        int need = materials.size();
        int matched = 0;

        if (materials.size() == item.getRecipeItems().size()) {

            for (String material : materials) {

                Logging.debugToConsole(
                        "[GIMM] Contains Material? " + item.getRecipeItems() + " "
                                + material.replaceFirst(replace, ""));

                if (item.getRecipeItems().contains(material.replaceFirst(replace, "")))
                    matched++;

            }

            Logging.debugToConsole("[GIMM] " + item.getItemStack().getType());
            Logging.debugToConsole("[GIMM] need: " + need);
            Logging.debugToConsole("[GIMM] matched: " + matched);

            if (matched == need) {

                Logging.debugToConsole("[GIMM] Item: " + item.getItemStack().getType());
                return item.getItemStack();

            }

        }

        return null;
    }

}
