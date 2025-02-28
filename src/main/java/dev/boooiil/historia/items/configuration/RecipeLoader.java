package dev.boooiil.historia.items.configuration;

import dev.boooiil.historia.items.HistoriaItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Predicate;

import javax.annotation.processing.Generated;

public final class RecipeLoader {

    @Generated(value = "Static Utility")
    private RecipeLoader() {
    }

    public static void load() {
        // EXAMPLE
        final ShapedRecipe shaped = new ShapedRecipe(HistoriaItems.getNamespacedKey("example"),
                new ItemStack(Material.PUMPKIN));
        shaped.shape(" # ", "#$#", " # ");

        shaped.setIngredient('#', customTypeChoice(HistoriaItems.getNamespacedKey("Common_Light_Bronze_Ingot")));
        shaped.setIngredient('$', Material.STICK);

        Bukkit.addRecipe(shaped);

    }

    static RecipeChoice.PredicateChoice customTypeChoice(NamespacedKey id) {
        NamespacedKey idKey = HistoriaItems.getNamespacedKey("item-id");

        Predicate<ItemStack> predicate = stack -> {
            boolean isCustom = stack.hasItemMeta()
                    && stack.getItemMeta().getPersistentDataContainer().has(idKey, PersistentDataType.STRING);
            if (isCustom) {
                String stackId = stack.getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
                return stackId.equals(id.getKey());
            }
            return stack.getType().equals(Material.getMaterial(id.getKey()));
        };

        ItemStack stack;
        if (HistoriaItems.ITEM_REGISTRY.contains(id)) {
            stack = HistoriaItems.ITEM_REGISTRY.get(id).createItemStack();
        } else {
            stack = ItemStack.of(Material.getMaterial(id.getKey()));
        }

        return RecipeChoice.predicateChoice(predicate, stack);
    }
}
