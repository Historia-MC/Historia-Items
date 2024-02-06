package dev.boooiil.historia.items.handlers.inventory.prepareCraftItem;

import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.ores.classes.items.generic.Ingot;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PrepareItemCraftInventoryHelper {

    private final ArrayList<String> pattern = new ArrayList<>();
    private final ArrayList<String> materials = new ArrayList<>();
    private final ArrayList<String> fullMaterials = new ArrayList<>();

    public PrepareItemCraftInventoryHelper(ItemStack[] craftingTableInventory) {

        Logging.debugToConsole("[CTI] Table Size: " + craftingTableInventory.length);

        String[] options = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        StringBuilder third = new StringBuilder();

        // result is now in slot 0
        // so matlab rules apply on this array
        for (int i = 1; i < craftingTableInventory.length; i++) {

            // if item in slot i is null or the item meta is null then set item to null
            ItemStack item = (craftingTableInventory[i] == null || craftingTableInventory[i].getItemMeta() == null)
                    ? null
                    : craftingTableInventory[i];
            String materialName = null;

            // check if the item could be a custom item
            if (item != null && item.getItemMeta() != null) {

                Ingot ingot = new Ingot(item);

                if (ingot.isValid()) {
                    materialName = ingot.generateReadableName();
                } else {
                    materialName = item.getType().toString();
                }

            }

            // add the item to the full material list if it is not null
            if (item != null) {

                Logging.debugToConsole("[CTI] Item: (" + i + ") " + materialName);

                fullMaterials.add(materialName);

            }

            if (i <= 3) {

                if (item == null)
                    first.append(" ");

                else if (materials.contains(materialName)) {

                    first.append(options[materials.indexOf(materialName)]);

                } else {

                    materials.add(materialName);
                    first.append(options[materials.size() - 1]);

                }

            }

            if (i >= 4 && i <= 6) {

                if (item == null)
                    second.append(" ");

                else if (materials.contains(materialName)) {

                    second.append(options[materials.indexOf(materialName)]);

                } else {

                    materials.add(materialName);
                    second.append(options[materials.size() - 1]);

                }

            }

            if (i >= 7 && i <= 9) {

                if (item == null)
                    third.append(" ");

                else if (materials.contains(materialName)) {

                    third.append(options[materials.indexOf(materialName)]);

                } else {

                    materials.add(materialName);
                    third.append(options[materials.size() - 1]);

                }

            }

        }

        pattern.add(first.toString());
        pattern.add(second.toString());
        pattern.add(third.toString());

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
