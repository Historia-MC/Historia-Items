package dev.boooiil.historia.items.configuration.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.general.LoreConfiguration;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.configuration.item.data.tool.ToolData;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ItemConfiguration {

    private String id;
    private String recipeId;
    private String displayName;

    private Material baseMaterial;
    private List<Integer> amount;
    private List<Component> lore = new ArrayList<>();

    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    private double weight;

    private List<ProficiencyName> allowedProficiencies = new ArrayList<>();

    private HashMap<String, IItemComponent> componentHolder = new HashMap<>();

    public ItemConfiguration(ConfigurationSection configuration, HashMap<String, IItemComponent> components) {
        this.componentHolder = components;

        this.baseMaterial = Material.valueOf(configuration.getString("material"));
        this.amount = configuration.getIntegerList("amount");
        this.displayName = configuration.getString("display-name");
        this.weight = configuration.getDouble("weight");
        this.recipeId = configuration.getString("recipe-id");
        // TODO: this will need to be changed to an actual unique id key
        this.id = configuration.getString("loc-name");

        if (configuration.contains("lore")) {
            List<String> loreList = configuration.getStringList("lore");
            for (String sLore : loreList) {
                lore.add(Component.text(sLore));
            }
        } else if (!components.isEmpty()) {

            for (String key : components.keySet()) {

                if (LoreConfiguration.contains(key)) {
                    List<String> loreList = LoreConfiguration.get(key);
                    for (String sLore : loreList) {
                        lore.add(Component.text(sLore));
                    }

                }
            }

            List<String> loreList = LoreConfiguration.get("foot");
            for (String sLore : loreList) {
                lore.add(Component.text(sLore));
            }

        }

        Logging.debugToConsole("" + lore);

        List<String> cProficiencies;
        cProficiencies = configuration.getStringList("canCraft");

        // add allowed proficiencies
        cProficiencies.forEach(p -> {
            allowedProficiencies.add(ProficiencyName.fromString(p));
        });

        /**
         * get comonent section map string
         */
        for (String strComponent : components.keySet()) {
            components
                    .get(strComponent)
                    .processConfiguration(configuration.getConfigurationSection(strComponent));
        }

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
        ItemMeta meta = item.getItemMeta();
        ToolData toolData = new ToolData(item);
        TextComponent textComponent = Component.text(getDisplayName());

        PDCUtils.setInContainer(meta, Main.getNamespacedKey("config-id"),
                PersistentDataType.STRING, id);

        meta.displayName(textComponent);
        meta.lore(lore);
        item.setItemMeta(meta);

        return toolData.apply();

        // for (IItemComponent component : componentHolder.values()) {
        // component.setDefaultsToMeta(item);
        // }

        // thoughts on applying lore:
        // %placeholder%
        // %weapon.sweeping% where "weapon" is the component and can be found through
        // ItemConfiguration.getValue(weapon.sweeping)

        // return item;

    }
}
