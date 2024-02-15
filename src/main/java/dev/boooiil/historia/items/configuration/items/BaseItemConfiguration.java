package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.util.Logging;

import java.util.List;

import org.bukkit.Material;

/**
 * <p>
 * The BaseConfiguration class is an abstract class that serves as the base for
 * all item configuration classes within the Historia plugin.
 * </p>
 * <p>
 * BaseConfiguration provides methods to retrieve and validate recipe items and
 * shapes, to check if a given proficiency can craft an item, and to retrieve
 * the item stack of an item.
 * </p>
 */
public class BaseItemConfiguration {

    /**
     * Recipe shape generally in the pattern of
     * [ "###", "###", "###" ].
     */
    protected List<String> recipeShape;

    /** Materials that are present in the creation of this item. */
    protected List<String> recipeItems;

    /** The proficiency that can craft this item. */
    protected List<String> proficiencies;

    /** If the item is shape dependent. */
    protected boolean isShaped;

    /** The material of the item. */
    protected Material material;

    /** The amount of the item. */
    protected int amount;

    /** The display name of the item. */
    protected String displayName;

    /** The lore of the item. */
    protected List<String> lore;

    /**
     * This function returns the list of items that are required to craft the item
     *
     * @return The list of recipe items.
     */
    public List<String> getRecipeItems() {

        return this.recipeItems;

    }

    /**
     * It returns the recipe shape
     *
     * @return The recipe shape.
     */
    public List<String> getRecipeShape() {

        return this.recipeShape;

    }

    /**
     * Validate recipe of items to see if it matches an armor.
     * 
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     *
     * @return If the recipe is valid.
     */
    public Boolean isValidRecipe(List<String> inputItems, List<String> inputShape) {

        boolean validItems = this.recipeItems.equals(inputItems);
        boolean validShape = this.recipeShape.equals(inputShape);

        return validItems && validShape;

    }

    /**
     * It returns if the item is shape dependent.
     *
     * @return If the item is shape dependent.
     */
    public boolean isShapeDependent() {
        return isShaped;
    }

    /**
     * It returns whether the given proficiency can craft this item.
     * 
     * @param proficiency The proficiency to check.
     * @return If the proficiency can craft the item.
     */
    public boolean canCraft(String proficiency) {

        Logging.debugToConsole("[CraftedItem] Proficiencies: " + this.proficiencies.toString());

        if (this.proficiencies.contains("ALL"))
            return true;
        else
            return this.proficiencies.contains(proficiency);

    }

    /**
     * It returns the material of the item.
     *
     * @return The material of the item.
     */
    public Material getMaterial() {

        return this.material;

    }

    /**
     * It returns the amount of the item.
     *
     * @return The amount of the item.
     */
    public int getAmount() {

        return this.amount;

    }

    /**
     * It returns the display name of the item.
     *
     * @return The display name of the item.
     */
    public String getDisplayName() {

        return this.displayName;

    }

    /**
     * It returns the lore of the item.
     *
     * @return The lore of the item.
     */
    public List<String> getLore() {

        return this.lore;

    }

    public String toString() {
        return "Material: " + this.material + " Amount: " + this.amount + " Display Name: " + this.displayName
                + " Lore: " + this.lore.toString();
    }

}
