package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import dev.boooiil.historia.items.configuration.ConfigurationProvider;
import dev.boooiil.historia.items.configuration.items.BaseItemConfiguration;
import dev.boooiil.historia.items.util.Logging;

import java.util.ArrayList;
import java.util.List;

public class PrepareItemCraftItemHelper {

    private BaseItemConfiguration result;
    private final List<BaseItemConfiguration> matchingItems;
    private final ArrayList<String> materials;
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

    public PrepareItemCraftItemHelper(PrepareItemCraftInventoryHelper inspector) {

        ArrayList<String> patterns = inspector.getPattern();
        materials = inspector.getMaterials();
        fullMaterials = inspector.getFullMaterials();

        // Getting all the items that match the pattern.
        matchingItems = ConfigurationProvider.getArmorConfigurationLoader().getAllMatchingShape(patterns);
        matchingItems.addAll(ConfigurationProvider.getWeaponConfigurationLoader().getAllMatchingShape(patterns));
        matchingItems.addAll(ConfigurationProvider.getCustomItemConfigurationLoader().getAllMatchingShape(patterns));

    }

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
