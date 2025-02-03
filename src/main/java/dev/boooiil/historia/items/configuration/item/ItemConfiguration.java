package dev.boooiil.historia.items.configuration.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.util.PDCUtils;

public class ItemConfiguration {

    private String id;
    private String recipeId;
    private String displayName;

    private Material baseMaterial;
    private List<Integer> amount;

    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    private double weight;

    private List<ProficiencyName> allowedProficiencies = new ArrayList<>();

    private HashMap<String, IItemComponent> componentHolder = new HashMap<>();

    public ItemConfiguration(YamlConfiguration configuration, HashMap<String, IItemComponent> components) {
        this.componentHolder = components;

        configuration.getKeys(false).forEach(cKey -> {

            List<String> cProficiencies;

            this.baseMaterial = Material.valueOf(configuration.getString(cKey + ".material"));
            this.amount = configuration.getIntegerList(cKey + ".amount");
            this.displayName = configuration.getString(cKey + ".display-name");
            this.weight = configuration.getDouble(cKey + ".weight");
            this.recipeId = configuration.getString("recipe-id");
            // TODO: this will need to be changed to an actual unique id key
            this.id = configuration.getString(cKey + ".loc-name");

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
            componentHolder.keySet().forEach(key -> {
                componentHolder
                        .get(key)
                        .processConfiguration(configuration.getConfigurationSection(cKey + "." + key));
            });

        });

    }

    public String getConfigurationId() {
        return this.id;
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
     * @return the allowedProficiencies
     */
    public List<ProficiencyName> getAllowedProficiencies() {
        return allowedProficiencies;
    }

    /**
     * @return the components
     */
    public HashMap<String, IItemComponent> getComponentHolder() {
        return componentHolder;
    }

    /**
     * Creates a default {@link ItemStack} with this configuration.
     * 
     * @return the created {@link ItemStack}.
     *
     */
    public ItemStack createItemStack() {

        // invalid material
        assert (baseMaterial != null | baseMaterial != Material.AIR);

        ItemStack item = new ItemStack(baseMaterial);

        PDCUtils.setInContainer(item, Main.getNamespacedKey("config-id"), PersistentDataType.STRING, id);

        for (IItemComponent component : componentHolder.values()) {
            component.setDefaultsToMeta(item);
        }

        // thoughts on applying lore:
        // %placeholder%
        // %weapon.sweeping% where "weapon" is the component and can be found through
        // ItemConfiguration.getValue(weapon.sweeping)

        return item;

    }
}
