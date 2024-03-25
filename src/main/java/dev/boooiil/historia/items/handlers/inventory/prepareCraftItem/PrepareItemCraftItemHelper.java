package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.CraftedItemConfigurationRegistry;
import dev.boooiil.historia.items.util.Logging;

import java.util.ArrayList;
import java.util.List;

/**
 * The PrepareItemCraftItemHelper class is responsible for preparing crafted
 * items
 * within the Historia plugin.
 */
public class PrepareItemCraftItemHelper {

    /** The result of the item configuration */
    private BaseItemConfiguration result;
    /** The list of matching items */
    private final List<BaseItemConfiguration> matchingItems;
    /** The list of materials */
    private final ArrayList<String> materials;
    /** The list of full materials */
    private final ArrayList<String> fullMaterials;

    /*
     * The process for this class is a little weird and will likely be changed in
     * the future.
     * 
     * It will collect all materials that match the given shape
     * unless it is a custom material (in which it will give all that
     * are not shape dependent).
     * 
     * 
     * To try and visualize the process:
     * 
     * 1. Get all items that match the shape.
     * 2. Check if the item is shape dependent.
     * 3. Call doMatch()
     * 3a. Iterate through all BaseItemConfiguration items.
     * 3a. Check if the item matches the number of materials.
     * 
     */

    /**
     * Constructs a PrepareItemCraftItemHelper object.
     */
    PrepareItemCraftItemHelper() {
        this.fullMaterials = null;
        this.matchingItems = null;
        this.materials = null;
    }

    /**
     * Constructs a PrepareItemCraftItemHelper object with the specified inspector.
     * 
     * @param inspector The inspector.
     */
    public PrepareItemCraftItemHelper(PrepareItemCraftInventoryHelper inspector) {

        ArrayList<String> shape = inspector.getPattern();
        materials = inspector.getMaterials();
        fullMaterials = inspector.getFullMaterials();

        // Getting all the items that match the pattern.
        matchingItems = CraftedItemConfigurationRegistry.getAllMatchingShape(shape);
    }

    /**
     * Perform the match for the item configuration.
     */
    public void doMatch() {

        if (matchingItems.isEmpty()) {
            return;
        }

        for (BaseItemConfiguration item : matchingItems) {

            Logging.debugToConsole("[CIM] Item: " + item.getDisplayName());

            if (item.isShapeDependent()) {

                if (itemConfigurationMatchesMaterials(item, materials)) {
                    result = item;
                }

            } else {

                if (itemConfigurationMatchesMaterials(item, materials)) {
                    result = item;
                }

            }

            if (result != null)
                break;

        }

    }

    /**
     * Get the result of the item configuration.
     * 
     * @return The item configuration.
     */
    public BaseItemConfiguration getResult() {
        return result;
    }

    /**
     * Check if the given item configuration matches the list of materials.
     * 
     * @param itemConfiguration The item configuration.
     * @param materials         The list of materials.
     * @return The item stack if it matches the materials.
     */
    private boolean itemConfigurationMatchesMaterials(BaseItemConfiguration itemConfiguration, List<String> materials) {
        String replace = "POOR_|COMMON_|PERFECT_";

        int need = materials.size();
        int matched = 0;

        if (materials.size() == itemConfiguration.getRecipeItems().size()) {

            for (String material : materials) {

                Logging.debugToConsole(
                        "[GIMM] Contains Material? " + itemConfiguration.getRecipeItems() + " "
                                + material.replaceFirst(replace, ""));

                if (itemConfiguration.getRecipeItems().contains(material.replaceFirst(replace, "")))
                    matched++;

            }

            Logging.debugToConsole("[GIMM] " + itemConfiguration.getMaterial());
            Logging.debugToConsole("[GIMM] need: " + need);
            Logging.debugToConsole("[GIMM] matched: " + matched);

            if (matched == need) {

                Logging.debugToConsole("[GIMM] Item: " + itemConfiguration.getMaterial());
                return true;

            }

        }

        return false;
    }

}
