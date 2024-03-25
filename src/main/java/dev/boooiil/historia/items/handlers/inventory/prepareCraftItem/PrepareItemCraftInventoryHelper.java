package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import dev.boooiil.historia.items.generic.Ingot;
import dev.boooiil.historia.items.util.Logging;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The PrepareItemCraftInventoryHelper class is responsible for parsing the
 * inventory of a crafting table and preparing the materials for crafting within
 * the Historia plugin.
 */
public class PrepareItemCraftInventoryHelper {

    /** The pattern of the crafting table */
    private final ArrayList<String> pattern = new ArrayList<>();
    /** The unique list of materials in the crafting pattern */
    private final ArrayList<String> materials = new ArrayList<>();
    /** The list of all materials in the crafting pattern */
    private final ArrayList<String> fullMaterials = new ArrayList<>();

    /**
     * Constructs a PrepareItemCraftInventoryHelper object with the specified
     * crafting table inventory.
     * 
     * @param craftingTableInventory The crafting table inventory.
     */
    public PrepareItemCraftInventoryHelper(ItemStack[] craftingTableInventory) {
        Logging.debugToConsole("[CTI] Table Size: " + craftingTableInventory.length);

        String[] options = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        StringBuilder[] rows = { new StringBuilder(), new StringBuilder(), new StringBuilder() };
        List<String> materials = new ArrayList<>();

        for (int i = 1; i < craftingTableInventory.length; i++) {
            ItemStack item = craftingTableInventory[i];
            String materialName = null;

            if (item != null && item.getItemMeta() != null) {
                Ingot ingot = new Ingot(item);
                materialName = ingot.isValid() ? ingot.generateReadableName() : item.getType().toString();
            }

            if (item != null) {
                Logging.debugToConsole("[CTI] Item: (" + i + ") " + materialName);
                materials.add(materialName);
            }

            int row = (i - 1) / 3; // Calculate current row
            StringBuilder currentRow = rows[row];

            if (item == null) {
                currentRow.append(" ");
            } else {
                if (materials.contains(materialName)) {
                    currentRow.append(options[materials.indexOf(materialName)]);
                } else {
                    materials.add(materialName);
                    currentRow.append(options[materials.size() - 1]);
                }
            }
        }

        List<String> pattern = Arrays.stream(rows).map(StringBuilder::toString).collect(Collectors.toList());
        Logging.infoToConsole("Pattern: " + pattern);
        Logging.infoToConsole("Materials: " + materials);
    }

    /**
     * Return the pattern of this crafting table.
     * Pattern is of the form ["ABC", "DEF", "GHI"]
     * 
     * @return A list of strings
     */
    public ArrayList<String> getPattern() {
        return pattern;
    }

    /**
     * Return the list of materials in this crafting table. Duplicate materials are
     * removed.
     * 
     * @return A list of materials
     */
    public ArrayList<String> getMaterials() {
        return materials;
    }

    /**
     * Return the list of all materials in this crafting table
     * 
     * @return A list of materials
     */
    public ArrayList<String> getFullMaterials() {
        return fullMaterials;
    }

}
