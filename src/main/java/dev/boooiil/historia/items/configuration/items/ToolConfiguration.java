package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.util.Construct;
import dev.boooiil.historia.items.util.NumberUtils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * It's a class that represents tools in the game.
 */
public class ToolConfiguration extends BaseItemConfiguration {

    private Weight weight;

    private int weightValue;

    private List<Double> damageRange;
    private List<Double> speedRange;
    private List<Double> knockbackRange;
    private List<Integer> durabilityRange;

    private final ItemStack itemStack;

    ToolConfiguration(ConfigurationSection section) {

        Material material = Material.getMaterial(section.getString(".item.type"));
        int amount = section.getInt(".item.amount");
        String displayName = section.getString(".item.display-name");
        List<String> lore = section.getStringList(".item.lore");

        this.itemStack = Construct.itemStack(material, amount, displayName, new ArrayList<>(lore));

        this.recipeItems = section.getStringList(".recipe-items");
        this.recipeShape = section.getStringList(".recipe-shape");
        this.proficiencies = section.getStringList(".canCraft");

        this.weightValue = section.getInt(".weight");
        this.weight = Weight.getWeight(section.getString(".type"));
        this.damageRange = section.getDoubleList(".damage");
        this.speedRange = section.getDoubleList(".speed");
        this.knockbackRange = section.getDoubleList(".knockback");
        this.durabilityRange = section.getIntegerList(".durability");

        this.isShaped = true;

    }

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

    public double getMinSpeedValue() {
        return speedRange.get(0);
    }

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

    public double getMinKnockbackValue() {
        return knockbackRange.get(0);
    }

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

    public ItemStack getItemStack() {
        return itemStack;
    }
}