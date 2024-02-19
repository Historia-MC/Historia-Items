package dev.boooiil.historia.items.configuration.crafted.armor;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.NumberUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

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
public class ArmorConfiguration extends BaseItemConfiguration {

    private Weight weight;

    private Integer weightValue;

    private List<Double> defense;
    private List<Integer> durability;

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

        // Getting the recipe shape from the config.
        this.recipeShape = section.getStringList(".recipe-shape");

        this.proficiencies = section.getStringList(".canCraft");

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

    public static ArmorConfiguration fromConfigurationSection(ConfigurationSection section) {

        return new ArmorConfiguration(section);

    }
}
