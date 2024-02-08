package dev.boooiil.historia.items.items.generic;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Warning;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.items.modifiers.Quality;
import dev.boooiil.historia.items.items.modifiers.Weight;
import dev.boooiil.historia.items.items.BaseItem;
import dev.boooiil.historia.items.util.Construct;

/**
 * This class represents an ingot as a child of the
 * BaseItem class. It contains all the information
 * that is needed to create and smelt an ingot.
 */
public class Ingot extends BaseItem {

    private Material material;
    private Weight weight;
    private Quality quality;

    private boolean isDirty;

    /**
     * Ingot builder from ItemStack.
     * 
     * @param itemStack
     */
    public Ingot(ItemStack itemStack) {

        if (!itemStack.hasItemMeta()) {
            this.valid = false;
            return;
        }

        this.itemStack = itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (container.has(Main.getNamespacedKey("ingot-name"), PersistentDataType.STRING)) {
            this.valid = true;
            this.material = itemStack.getType();
            this.weight = Weight
                    .getWeight(container.get(Main.getNamespacedKey("ingot-weight"), PersistentDataType.STRING));
            this.quality = Quality
                    .getQuality(container.get(Main.getNamespacedKey("ingot-quality"), PersistentDataType.STRING));

            this.isDirty = container.get(Main.getNamespacedKey("ingot-dirtiness"), PersistentDataType.BOOLEAN);

        }

    }

    public Ingot(Material drop, String name, Weight weight, Quality quality) {

        switch (drop) {

            case RAW_IRON:
            case RAW_COPPER:
            case RAW_GOLD:
            case IRON_INGOT:
            case COPPER_INGOT:
            case GOLD_INGOT:
            case COAL:
            case GLOWSTONE_DUST:
            case LEATHER:
                break;

            default:
                this.valid = false;
                return;
        }

        this.material = drop;
        this.weight = weight;
        this.quality = quality;

        List<String> lore = List.of(
                "",
                "§7Weight - " + weight.getWeightColor(),
                "§7Quality - " + quality.getProperNameColored());

        this.itemStack = Construct.itemStack(drop, 1, name, lore);

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("ingot-name"), PersistentDataType.STRING, name);
        container.set(Main.getNamespacedKey("ingot-weight"), PersistentDataType.STRING, weight.getKey());
        container.set(Main.getNamespacedKey("ingot-quality"), PersistentDataType.STRING, quality.getKey());

        itemStack.setItemMeta(meta);

        this.valid = true;

    }

    /**
     * This function returns the material variable.
     * 
     * @return The material variable.
     */
    public Material getMaterial() {

        return this.material;

    }

    /**
     * This function returns the quality variable.
     * 
     * @return The quality variable.
     */
    public Quality getQuality() {

        return this.quality;

    }

    /**
     * This function returns the tier variable.
     * 
     * @return The tier variable.
     */
    public Weight getWeight() {

        return this.weight;

    }

    @Warning(reason = "This function is not yet implemented.")
    public boolean isDirty() {

        return this.isDirty;

    }

    public boolean isRaw() {

        switch (this.material) {

            case RAW_IRON:
            case RAW_COPPER:
            case RAW_GOLD:
                return true;

            default:
                return false;

        }

    }

    public String generateReadableName() {
        return this.quality.getKey().toUpperCase() + "_" + this.weight.getKey().toUpperCase() + "_"
                + this.material.name();
    }

    /**
     * Get an Ingot from a string representation. This MUST follow the format of
     * "quality_weight_material". For example, "common_light_iron_ingot".
     * 
     * @param string - The string representation of the ingot.
     * @return The ingot object.
     */
    @Warning(reason = "This method is unsafe. Use the Ingot(Material, String, Weight, Quality) constructor instead.")
    public static Ingot parseFromString(String string) {

        Logging.debugToConsole("Parsing string: " + string);

        string = string.toLowerCase();

        String[] split = string.split("_");

        if (split.length < 3) {
            return null;
        }

        Quality quality = null;
        Weight weight = null;
        String name = null;
        Material material = null;
        StringBuilder materialBuilder = new StringBuilder();

        try {
            quality = Quality.getQuality(split[0]);
        } catch (IllegalArgumentException e) {
            Logging.debugToConsole("Invalid quality");
            return new Ingot(Material.AIR, "Invalid Ingot", Weight.LIGHT, Quality.POOR);
        }

        try {
            weight = Weight.getWeight(split[1]);
        } catch (IllegalArgumentException e) {
            Logging.debugToConsole("Invalid weight");
            return new Ingot(Material.AIR, "Invalid Ingot", Weight.LIGHT, Quality.POOR);
        }

        // Extract material
        for (int i = 2; i < split.length; i++) {
            materialBuilder.append(split[i]);
            if (i == 2)
                name = split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
            ;
            if (i < split.length - 1) {
                materialBuilder.append("_"); // Add underscore if it's not the last part
            }
        }

        switch (materialBuilder.toString().toLowerCase()) {
            case "sulphur":
                material = Material.GLOWSTONE_DUST;
                break;
            case "tin_ingot":
            case "iron_ingot":
                material = Material.IRON_INGOT;
                break;
            case "copper_ingot":
                material = Material.COPPER_INGOT;
                break;
            case "bronze_ingot":
            case "gold_ingot":
                material = Material.GOLD_INGOT;
                break;

            case "sheep_leather":
            case "pig_leather":
            case "horse_leather":
            case "cow_leather":
                material = Material.LEATHER;
                break;

            default:
                try {
                    material = Material.valueOf(materialBuilder.toString().toUpperCase());
                } catch (IllegalArgumentException e) {
                    Logging.debugToConsole("Invalid material");
                    return new Ingot(Material.AIR, "Invalid Ingot", Weight.LIGHT, Quality.POOR);
                }
                break;
        }

        return new Ingot(material, quality.getQualityColor() + name, weight, quality);

    }
}