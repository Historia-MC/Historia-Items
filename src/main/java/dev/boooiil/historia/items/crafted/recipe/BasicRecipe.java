package dev.boooiil.historia.items.crafted.recipe;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;

public class BasicRecipe {

    private Material baseMaterial;
    private String style;
    private List<String> pattern;
    private Map<Character, MaterialLike> ingredientMap;

    BasicRecipe() {

    }

    /**
     * Set the base material of the recipe.
     * 
     * @param baseMaterial The base material of the recipe.
     */
    public void setBaseMaterial(Material baseMaterial) {
        this.baseMaterial = baseMaterial;
    }

    /**
     * Set the item style of the recipe.
     * 
     * @param style The style of the recipe.
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Set the pattern of the recipe.
     * 
     * @param pattern The pattern of the recipe.
     */
    public void setPattern(List<String> pattern) {
        this.pattern = pattern;
    }

    /**
     * Set the ingredient map of the recipe.
     * 
     * @param ingredientMap The ingredient map of the recipe.
     */
    public void setIngredientMap(Map<Character, MaterialLike> ingredientMap) {
        this.ingredientMap = ingredientMap;
    }

    /**
     * Add an ingredient to the recipe.
     * 
     * @param key          The char association of the ingredient.
     * @param materialLike The material like of the ingredient.
     */
    public void addIngredient(Character key, MaterialLike materialLike) {
        ingredientMap.put(key, materialLike);
    }

    /**
     * Get the base material of the recipe.
     * 
     * @return The base material of the recipe.
     */
    public Material getBaseMaterial() {
        return baseMaterial;
    }

    /**
     * Get the style of the recipe.
     * 
     * @return The style of the recipe.
     */
    public String getStyle() {
        return style;
    }

    /**
     * Get the pattern of the recipe.
     * 
     * @return The pattern of the recipe.
     */
    public List<String> getPattern() {
        return pattern;
    }

    /**
     * Get the ingredient map of the recipe.
     * 
     * @return The ingredient map of the recipe.
     */
    public Map<Character, MaterialLike> getIngredientMap() {
        return ingredientMap;
    }

    public void matcher() {
        // todo
    }

}
