package dev.boooiil.historia.items.configuration.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.crafted.modifiers.Weight;

public class ItemConfiguration {

    String recipeId;
    String displayName;

    Material baseMaterial;
    List<Integer> amount;

    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    double weight;

    Weight type;
    List<ProficiencyName> allowedProficiencies = new ArrayList<>();

    HashMap<String, IItemComponent> components = new HashMap<>();

    ItemConfiguration(YamlConfiguration configuration, HashMap<String, IItemComponent> components) {
        this.components = components;

        configuration.getKeys(false).forEach(cKey -> {

            List<String> cProficiencies;

            this.baseMaterial = Material.valueOf(configuration.getString(cKey + ".material"));
            this.amount = configuration.getIntegerList(cKey + ".amount");
            this.displayName = configuration.getString(cKey + ".display-name");
            this.weight = configuration.getDouble(cKey + ".weight");
            this.type = Weight.getWeight(configuration.getString(cKey + ".type"));
            this.recipeId = configuration.getString("recipe-id");

            cProficiencies = configuration.getStringList("canCraft");

            // add allowed proficiencies
            cProficiencies.forEach(p -> {
                allowedProficiencies.add(ProficiencyName.fromString(p));
            });

            // components.forEach(component -> {
            // component.processConfiguration(configuration.getConfigurationSection(key +
            // ".component"));
            // });

            // temp component processing until we get the registry
            components.keySet().forEach(key -> {
                components.get(key).processConfiguration(configuration.getConfigurationSection(cKey + "." + key));
            });

        });

    }

    /**
     * @return the recipeId
     */
    public String getRecipeId() {
        return recipeId;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the baseMaterial
     */
    public Material getBaseMaterial() {
        return baseMaterial;
    }

    /**
     * @return the amount
     */
    public List<Integer> getAmount() {
        return amount;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the type
     */
    public Weight getType() {
        return type;
    }

    /**
     * @return the allowedProficiencies
     */
    public List<ProficiencyName> getAllowedProficiencies() {
        return allowedProficiencies;
    }

    /**
     * @return the components
     */
    public HashMap<String, IItemComponent> getComponents() {
        return components;
    }

    /**
     * Creates an {@link ItemStack} from this configuration with a quality modifier
     * of 0.
     * 
     * @return the created {@link ItemStack}.
     *
     */
    public ItemStack createItemStack() {

        return createItemStack(0);

    }

    public ItemStack createItemStack(float qualityModifier) {

        // invalid material
        assert (baseMaterial != null | baseMaterial != Material.AIR);

        ItemStack item = new ItemStack(baseMaterial);

        for (IItemComponent component : components.values()) {
            component.applyToItemStack(item, qualityModifier);
        }

        // thoughts on applying lore:
        // %placeholder%
        // %weapon.sweeping% where "weapon" is the component and can be found through
        // ItemConfiguration.getValue(weapon.sweeping)

        return item;

    }
}
