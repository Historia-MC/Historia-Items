package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.generic.Ingot;
import dev.boooiil.historia.items.util.Logging;

public class newCraftingInventoryHelper {
    /*
     * 
     * Using an example of (Light Tin Sword)
     * This will give us a pattern of:
     * 
     * [
     * X A X,
     * X A X,
     * X B X
     * ]
     * 
     * And a list of materials:
     * [Light_Tin_Ingot, Stick]
     * 
     * And a list of full materials:
     * [Light_Tin_Ingot, Light_Tin_Ingot, Stick]
     */

    /** Pattern of materials [ "ABC", "DEF", "GHI" ] */
    private final ArrayList<String> pattern = new ArrayList<>();

    /** List of materials */
    private final ArrayList<String> materials = new ArrayList<>();

    /** List of full materials */
    private final ArrayList<String> fullMaterials = new ArrayList<>();

    private final String[] PATTERN_LETTERS = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

    /**
     * Parses the given crafting inventory and generates a pattern and list of
     * materials by string.
     * 
     * @param craftingInventoryItems The crafting MATRIX, not the crafting
     *                               inventory.
     */
    newCraftingInventoryHelper(ItemStack[] craftingInventoryItems) {

        StringBuilder[] rows = { new StringBuilder(), new StringBuilder(), new StringBuilder() };
        List<String> materials = new ArrayList<>();

        for (int i = 0; i < craftingInventoryItems.length; i++) {
            ItemStack item = craftingInventoryItems[i];
            String materialName = null;

            if (item != null && item.getItemMeta() != null) {
                Ingot ingot = new Ingot(item);
                materialName = ingot.isValid() ? ingot.generateReadableName() : item.getType().toString();
            }

            if (item != null) {
                Logging.debugToConsole("[CTI] Item: (" + i + ") " + materialName);
                materials.add(materialName);
            }

            int row = (i) / 3; // Calculate current row
            StringBuilder currentRow = rows[row];

            if (item == null) {
                currentRow.append(" ");
            } else {
                fullMaterials.add(materialName); // Add to full materials (for later use
                if (materials.contains(materialName)) {
                    currentRow.append(PATTERN_LETTERS[materials.indexOf(materialName)]);
                } else {
                    materials.add(materialName);
                    currentRow.append(PATTERN_LETTERS[materials.size() - 1]);
                }
            }
        }

        List<String> pattern = Arrays.stream(rows).map(StringBuilder::toString).collect(Collectors.toList());
        Logging.infoToConsole("Pattern: " + pattern);
        Logging.infoToConsole("Materials: " + materials);

    }

}
