package dev.boooiil.historia.items.configuration.crafted;

import java.util.List;
import java.util.Map;

public class CraftedItemConfiguration extends BaseItemConfiguration {

    private String proficiencyRequirement;

    private List<String> recipeShape;
    private List<String> recipeItems;

    private boolean isShaped;
    private boolean isSimple;

    private Map<Character, CraftedItemMaterial<?>> materialAssociations;

    public CraftedItemConfiguration() {
        super();
    }

    public void setProficiencyRequirement(String proficiencyRequirement) {
        this.proficiencyRequirement = proficiencyRequirement;
    }

    public void setRecipeShape(List<String> recipeShape) {
        // TODO: should we validate?
        this.recipeShape = recipeShape;
    }

    public void setIngredient(char key, CraftedItemMaterial<?> material) {

        if (material.hasQualities()) {
            isSimple = false;
        }

        materialAssociations.put(key, material);
    }

    public void setIngredient(Character key, CraftedItemMaterial<?> material) {

        if (material.hasQualities()) {
            isSimple = false;
        }

        materialAssociations.put(key, material);
    }

    public String getProficiencyRequirement() {
        return proficiencyRequirement;
    }

    public boolean isSimple() {
        return isSimple;
    }

    public List<String> getRecipeShape() {
        return recipeShape;
    }

    public List<String> getRecipeItems() {
        return recipeItems;
    }

}
