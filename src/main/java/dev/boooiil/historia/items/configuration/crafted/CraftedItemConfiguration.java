package dev.boooiil.historia.items.configuration.crafted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Warning;

import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.items.util.Logging;

public class CraftedItemConfiguration extends BaseItemConfiguration {

    @Deprecated(forRemoval = true)
    protected List<String> recipeItems;

    /**
     * Recipe shape generally in the pattern of
     * [ "###", "###", "###" ].
     */
    private List<String> recipeShape;

    /** If the item is shape dependent. */
    @Deprecated(forRemoval = true)
    protected boolean isShaped;
    private boolean isSimple;

    private List<ProficiencyName> proficiencyRequirement = new ArrayList<>();
    private Map<Character, CraftedItemMaterialBasic> materialAssociations = new HashMap<>();

    public CraftedItemConfiguration() {
        super();

        Logging.debugToConsole("CraftedItemConfiguration constructor called.");
    }

    public void setProficiencyRequirement(ProficiencyName proficiencyRequirement) {
        this.proficiencyRequirement.add(proficiencyRequirement);
    }

    public void setRecipeShape(List<String> recipeShape) {
        // TODO: should we validate?
        this.recipeShape = recipeShape;
    }

    public void setIngredient(char key, CraftedItemMaterialBasic material) {

        if (material.hasQualities()) {
            isSimple = false;
        }

        materialAssociations.put(key, material);
    }

    public void setIngredient(Character key, CraftedItemMaterialBasic material) {

        if (material.hasQualities()) {
            isSimple = false;
        }

        materialAssociations.put(key, material);
    }

    public List<ProficiencyName> getProficiencyRequirement() {
        return proficiencyRequirement;
    }

    public boolean getCraftingEligibility(ProficiencyName proficiency) {

        return proficiencyRequirement.contains(proficiency);
    }

    public boolean isSimple() {
        return isSimple;
    }

    /**
     * Return the shape of the item recipe in a List<String> representation.
     * 
     * @return The shape of the item recipe.
     */
    public List<String> getRecipeShape() {
        return recipeShape;
    }

    /**
     * Validate recipe of items to see if it matches an armor.
     * 
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     *
     * @return If the recipe is valid.
     */
    @Warning(reason = "This method will not work and is only here for compatibility.")
    @Deprecated(forRemoval = true)
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
    @Warning(reason = "This method will not work and is only here for compatibility.")
    @Deprecated(forRemoval = true)
    public boolean isShapeDependent() {
        return isShaped;
    }

    /**
     * It returns whether the given proficiency can craft this item.
     * 
     * @param proficiency The proficiency to check.
     * @return If the proficiency can craft the item.
     */
    @Warning(reason = "This method might not work and is only here for compatibility. Use #getCraftingEligibility(ProficiencyName).")
    @Deprecated(forRemoval = true)
    public boolean canCraft(ProficiencyName proficiency) {

        if (this.proficiencyRequirement.toString().contains("ALL"))
            return true;
        else
            return this.proficiencyRequirement.toString().contains(proficiency.getKey());

    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Proficiency Requirement: ").append(proficiencyRequirement).append("\n");
        sb.append("Recipe Shape: ").append(recipeShape).append("\n");
        sb.append("Recipe Items: ").append(recipeItems).append("\n");
        sb.append("Is Shaped: ").append(isShaped).append("\n");
        sb.append("Is Simple: ").append(isSimple).append("\n");

        sb.append("----- Materials -----\n");
        for (Map.Entry<Character, CraftedItemMaterialBasic> entry : materialAssociations.entrySet()) {

            StringBuilder sbk = new StringBuilder();
            StringBuilder sbp = new StringBuilder();
            String key;
            String title;

            key = sbk.append("(").append(entry.getKey()).append(")").toString();

            if (entry.getValue() instanceof CraftedItemMaterialComplex) {

                title = sbp.append(key).append(" [COMPLEX ITEM MATERIAL]").toString();

                sb.append(title).append("\n");

                sb.append(key).append(" Material: ").append(entry.getValue().getItemStack().getType().toString())
                        .append("\n");

            }

            else {

                title = sbp.append(key).append(" [BASIC ITEM MATERIAL]").toString();

                sb.append(title).append("\n");

                sb.append(key).append(" Material: ").append(entry.getValue().getItemStack().getType().toString());

            }

            sb.append(key).append(" Modifiers: ").append(entry.getValue().toString(entry.getKey()))
                    .append("\n");

        }

        return sb.toString();
    }

}
