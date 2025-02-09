package dev.boooiil.historia.items.configuration;

import dev.boooiil.historia.items.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Predicate;

public class RecipeLoader {

    public static void load() {
        // EXAMPLE
        final ShapedRecipe shaped = new ShapedRecipe(Main.getNamespacedKey("example"), new ItemStack(Material.PUMPKIN));
        shaped.shape(" # ", "#$#", " # ");

        shaped.setIngredient('#', customTypeChoice("Common_Light_Bronze_Ingot"));
        shaped.setIngredient('$', Material.STICK);

        Bukkit.addRecipe(shaped);
    }

    static RecipeChoice.PredicateChoice customTypeChoice(String id) {
        NamespacedKey idKey = Main.getNamespacedKey("config-id");

        Predicate<ItemStack> predicate = stack -> {
            boolean isCustom = stack.hasItemMeta() && stack.getItemMeta().getPersistentDataContainer().has(idKey, PersistentDataType.STRING);
            if (isCustom) {
                String stackId = stack.getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
                return stackId.equals(id);
            }
            return stack.getType().equals(Material.getMaterial(id));
        };

        ItemStack stack;
        if (ItemRegistry.contains(id)) {
            stack = ItemRegistry.get(id).createItemStack();
        } else {
            stack = ItemStack.of(Material.getMaterial(id));
        }

        return RecipeChoice.predicateChoice(predicate, stack);
    }
}
