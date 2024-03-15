package dev.boooiil.historia.items.configuration.crafted.weapon;

import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.crafted.ItemType;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.NumberUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import java.util.List;

/**
 * <p>
 * The ToolConfiguration class is a subclass of the BaseConfiguration class and
 * is responsible for managing and accessing configuration data for tool items
 * within the Historia plugin.
 * </p>
 * <p>
 * ToolConfiguration provides methods to retrieve tool-specific configuration
 * settings, such as weight, damage, speed, knockback, and durability, and to
 * create tool items with randomized damage, speed, knockback, and durability
 * values.
 * </p>
 * 
 * @see BaseItemConfiguration
 */
public class WeaponConfiguration extends BaseItemConfiguration {

    private Weight weight;

    private int weightValue;

    private List<Double> damageRange;
    private List<Double> speedRange;
    private List<Double> knockbackRange;
    private List<Double> sweepRange;
    private List<Integer> durabilityRange;

    /**
     * Create a built {@link WeaponConfiguration} object from the configuration.
     * 
     * @param section The configuration section.
     */
    WeaponConfiguration(ConfigurationSection section) {

        this.itemType = ItemType.WEAPON;
        this.material = Material.getMaterial(section.getString(".item.type"));
        this.amount = section.getInt(".item.amount");
        this.displayName = section.getString(".item.display-name");
        this.lore = section.getStringList(".item.lore");
        // TODO: replace with .id when implemented
        this.id = section.getString(".item.loc-name");

        this.recipeItems = section.getStringList(".recipe-items");
        this.recipeShape = section.getStringList(".recipe-shape");

        this.weightValue = section.getInt(".weight");
        this.weight = Weight.getWeight(section.getString(".type"));
        this.damageRange = section.getDoubleList(".damage");
        this.speedRange = section.getDoubleList(".speed");
        this.knockbackRange = section.getDoubleList(".knockback");
        this.sweepRange = section.getDoubleList(".sweeping");
        this.durabilityRange = section.getIntegerList(".durability");

        this.isShaped = true;

        this.proficiencies = section.getStringList(".canCraft");

    }

    /**
     * This function updates the weapon stats.
     * 
     * @param lore The lore of the weapon.
     */
    public void updateWeaponStats(List<String> lore) {

    }

    /**
     * This function returns the damage range of the weapon.
     * 
     * @return The damage range of the weapon.
     */
    public List<Double> getDamageRange() {
        return damageRange;
    }

    /**
     * This function returns the first element of the damageRange ArrayList
     * 
     * @return The first element of the damageRange array.
     */
    public Double getMinDamageValue() {

        return this.damageRange.get(0);

    }

    /**
     * It returns the maximum damage value of the damage range
     * 
     * @return The second value in the damageRange array.
     */
    public Double getMaxDamageValue() {

        return this.damageRange.get(1);

    }

    /**
     * It returns a random integer between the minimum and maximum damage values
     * 
     * @return A random number between the min and max damage values.
     */
    public double getDamageRandomValue() {

        return NumberUtils.random(getMinDamageValue(), getMaxDamageValue());

    }

    /**
     * This function returns the speed range of the car
     * 
     * @return The speedRange list.
     */
    public List<Double> getSpeedRange() {
        return speedRange;
    }

    /**
     * This function returns the minimum speed value of the weapon
     * 
     * @return The minimum speed value of the weapon.
     */
    public double getMinSpeedValue() {
        return speedRange.get(0);
    }

    /**
     * This function returns the maximum speed value of the weapon
     * 
     * @return The maximum speed value of the weapon.
     */
    public double getMaxSpeedValue() {
        return speedRange.get(1);
    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public double getSpeedRandomValue() {

        return NumberUtils.random(getMinSpeedValue(), getMaxSpeedValue());

    }

    /**
     * It returns a list of doubles
     * 
     * @return The knockbackRange list.
     */
    public List<Double> getKnockbackRange() {
        return knockbackRange;
    }

    /**
     * This function returns the minimum knockback value of the weapon
     * 
     * @return The minimum knockback value of the weapon.
     */
    public double getMinKnockbackValue() {
        return knockbackRange.get(0);
    }

    /**
     * This function returns the maximum knockback value of the weapon
     * 
     * @return The maximum knockback value of the weapon.
     */
    public double getMaxKnockbackValue() {
        return knockbackRange.get(1);
    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public double getKnockbackRandomValue() {

        return NumberUtils.random(getMinKnockbackValue(), getMaxKnockbackValue());

    }

    /**
     * This function returns a list of doubles that represent the sweep range
     * 
     * @return The sweepRange list is being returned.
     */
    public List<Double> getSweepRange() {
        return sweepRange;
    }

    /**
     * This function returns the first element of the sweepRange ArrayList
     * 
     * @return The first element of the sweepRange array.
     */
    public double getMinSweepValue() {
        return sweepRange.get(0);
    }

    /**
     * This function returns the maximum sweep value of the sweep range
     * 
     * @return The second value in the sweepRange array.
     */
    public double getMaxSweepValue() {
        return sweepRange.get(1);
    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public double getSweepRandomValue() {

        return NumberUtils.random(getMinSweepValue(), getMaxSweepValue());

    }

    /**
     * This function returns a list of integers that represent the durability range
     * of the item
     * 
     * @return The durabilityRange variable is being returned.
     */
    public List<Integer> getDurabilityRange() {
        return durabilityRange;
    }

    /**
     * This function returns the first value in the durabilityRange array
     * 
     * @return The first value in the durabilityRange array.
     */
    public Integer getMinDurabilityValue() {

        return this.durabilityRange.get(0);

    }

    /**
     * This function returns the maximum durability value of the item
     * 
     * @return The second value in the durabilityRange array.
     */
    public Integer getMaxDurabilityValue() {

        return this.durabilityRange.get(1);

    }

    /**
     * It returns a random integer between the minimum and maximum durability values
     * 
     * @return A random number between the min and max durability values.
     */
    public Integer getRandomDurabilityValue() {

        return NumberUtils.randomInt(getMinDurabilityValue(), getMaxDurabilityValue());

    }

    /**
     * This function returns the type of the current object
     * 
     * @return The type of the object.
     */
    public Weight getWeight() {

        return this.weight;

    }

    /**
     * This function returns the weight of the object
     * 
     * @return The weight of the object.
     */
    public int getWeightValue() {

        return this.weightValue;

    }

    /**
     * This function returns a new WeaponConfiguration object from the configuration
     * section
     * 
     * @param section The configuration section.
     * @return A new ToolConfiguration object.
     */
    public static WeaponConfiguration fromConfigurationSection(ConfigurationSection section) {
        return new WeaponConfiguration(section);
    }

}