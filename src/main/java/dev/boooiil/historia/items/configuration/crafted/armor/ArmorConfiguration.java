package dev.boooiil.historia.items.configuration.crafted.armor;

import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.CraftedItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.CraftedItemMaterialBasic;
import dev.boooiil.historia.items.configuration.crafted.CraftedItemMaterialComplex;
import dev.boooiil.historia.items.configuration.crafted.MaterialMatchBy;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Quality;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.generic.Ingot;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * The ArmorConfiguration class is a subclass of the BaseConfiguration class and
 * is responsible for managing and accessing configuration data for armor items
 * within the Historia plugin.
 * </p>
 * <p>
 * ArmorConfiguration provides methods to retrieve armor-specific configuration
 * settings, such as weight, defense, and durability, and to create armor items
 * with randomized defense and durability values.
 * </p>
 * 
 * @see BaseItemConfiguration
 */
public class ArmorConfiguration extends CraftedItemConfiguration {

    private Weight weight;

    private Integer weightValue;

    private List<Double> defense;
    private List<Integer> durability;

    /**
     * Create a built {@link ArmorConfiguration} object from the configuration.
     * 
     * @param section The configuration section.
     */
    ArmorConfiguration(ConfigurationSection section) {

        this.itemType = ItemType.ARMOR;
        this.material = Material.getMaterial(section.getString(".item.type"));
        this.amount = section.getInt(".item.amount");
        this.displayName = section.getString(".item.display-name");
        this.lore = section.getStringList(".item.lore");
        // TODO: replace with .id when implemented
        this.id = section.getString(".item.loc-name");

        // Getting the weight class of the armor.
        this.weight = Weight.getWeight(section.getString(".type"));

        // Getting the weight of the armor.
        this.weightValue = section.getInt(".weight");

        // Getting the defense value of the armor.
        this.defense = section.getDoubleList(".defense");

        // Getting the durability of the armor.
        this.durability = section.getIntegerList(".durability");

        // Getting the recipe items from the config.
        this.recipeItems = section.getStringList(".recipe-items");

        super.setRecipeShape(section.getStringList(".recipe-shape"));

        for (String str_proficiency : section.getStringList(".canCraft")) {
            try {
                ProficiencyName proficiency = ProficiencyName.valueOf(str_proficiency);
                super.setProficiencyRequirement(proficiency);
            } catch (IllegalArgumentException e) {
                Logging.errorToConsole("Invalid proficiency name: " + str_proficiency + " for item: " + this.id);
            }
        }

        // TODO: there is definitely a better way to do this
        HashMap<Integer, Character> assoc = new HashMap<>();

        assoc.put(0, 'A');
        assoc.put(1, 'B');
        assoc.put(2, 'C');
        assoc.put(3, 'D');
        assoc.put(4, 'E');
        assoc.put(5, 'F');
        assoc.put(6, 'G');
        assoc.put(7, 'H');
        assoc.put(8, 'I');

        // TODO: figure out how to handle this better

        for (int i = 0; i <= 8; i++) {
            String str_item = section.getStringList(".recipe-items").get(i);

            if (str_item.contains("INGOT") || str_item.contains("LEATHER")) {
                Ingot ingot = Ingot.parseFromString(str_item);

                CraftedItemMaterialComplex<Quality> cimc_ingot = new CraftedItemMaterialComplex<>(Quality.class,
                        ingot.getItemStack());

                cimc_ingot.addModifier(Quality.POOR);
                cimc_ingot.addModifier(Quality.COMMON);
                cimc_ingot.addModifier(Quality.PERFECT);

                cimc_ingot.addMatchBy(MaterialMatchBy.MODIFIER);

                super.setIngredient(assoc.get(i), cimc_ingot);
            }

            else {
                CraftedItemMaterialBasic cimb = new CraftedItemMaterialBasic(
                        new ItemStack(Material.getMaterial(str_item)));

                super.setIngredient(assoc.get(i), cimb);
            }
        }

        this.isShaped = true;

    }

    /**
     * Get the type (weight class) of armor.
     *
     * @return Type of armor.
     */
    public Weight getWeight() {

        return this.weight;

    }

    /**
     * Get the weight of the armor.
     *
     * @return Weight of the armor.
     */
    public Integer getWeightValue() {

        return this.weightValue;

    }

    /**
     * Get the randomized defense value of the armor.
     *
     * @return Defence value.
     */
    public Double getRandomDefenseValue() {

        return NumberUtils.random(getMinDefenseValue(), getMaxDefenseValue());

    }

    /**
     * Get the randomized durability value of the armor.
     *
     * @return Durability value.
     */
    public Integer getRandomDurabilityValue() {

        return NumberUtils.randomInt(getMinDurabilityValue(), getMaxDurabilityValue());

    }

    /**
     * Get the minimum base defense value of the armor.
     *
     * @return Defense value.
     */
    public Double getMinDefenseValue() {

        return this.defense.get(0);

    }

    /**
     * Get the maximum base defense value of the armor.
     *
     * @return Defence value.
     */
    public Double getMaxDefenseValue() {

        return this.defense.get(1);

    }

    /**
     * Get the minimum base durability value of the armor.
     *
     * @return Durability value.
     */
    public Integer getMinDurabilityValue() {

        return this.durability.get(0);

    }

    /**
     * Get the minimum base durability value of the armor.
     *
     * @return Durability value.
     */
    public Integer getMaxDurabilityValue() {

        return this.durability.get(1);

    }

    /**
     * Create a new ArmorConfiguration object from a configuration section.
     * 
     * @param section The configuration section.
     * @return A new ArmorConfiguration object.
     */
    public static ArmorConfiguration fromConfigurationSection(ConfigurationSection section) {

        return new ArmorConfiguration(section);

    }
}
