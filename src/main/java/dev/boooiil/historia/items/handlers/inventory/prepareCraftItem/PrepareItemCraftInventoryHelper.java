package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.items.generic.Ingot;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrepareItemCraftInventoryHelper {

    private final ArrayList<String> pattern = new ArrayList<>();
    private final ArrayList<String> materials = new ArrayList<>();
    private final ArrayList<String> fullMaterials = new ArrayList<>();

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

    public ArrayList<String> getPattern() {
        return pattern;
    }

    public ArrayList<String> getMaterials() {
        return materials;
    }

    public ArrayList<String> getFullMaterials() {
        return fullMaterials;
    }

}
