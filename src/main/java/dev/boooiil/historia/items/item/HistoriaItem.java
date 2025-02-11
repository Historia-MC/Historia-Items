package dev.boooiil.historia.items.item;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.general.LoreConfiguration;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class HistoriaItem {

    private final String id;
    private final String displayName;
    private final Material baseMaterial;
    private final List<Component> lore;

    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    private final double weight;

    private final Map<String, ItemComponent> components;

    public HistoriaItem(
            String id,
            String displayName,
            Material baseMaterial,
            List<Component> lore,
            double weight,
            Map<String, ItemComponent> components) {
        this.id = id;
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.lore = lore;
        this.weight = weight;
        this.components = components;
    }

    public static HistoriaItem fromConfig(ConfigurationSection section) {
        Material baseMaterial = Material.valueOf(section.getString("material"));
        List<Integer> amount = section.getIntegerList("amount");
        String displayName = section.getString("display-name");
        Double weight = section.getDouble("weight");

        // TODO: this will need to be changed to an actual unique id key
        String id = section.getString("loc-name");

        Map<String, ItemComponent> components = new HashMap<>();
        for (String key : ItemComponents.allKeys()) {
            if (section.contains(key)) {
                ConfigurationSection componentSection = section.getConfigurationSection(key);
                components.put(key, ItemComponent.fromConfig(key, componentSection));
            }
        }

        List<Component> lore = new ArrayList<>();
        if (section.contains("lore")) {
            List<String> loreList = section.getStringList("lore");
            for (String sLore : loreList) {
                lore.add(Component.text(sLore));
            }
        }
        if (!components.isEmpty()) {

            for (String key : components.keySet()) {

                if (LoreConfiguration.contains(key)) {
                    lore.add(Component.text("[" + key.toUpperCase() + "]"));

                    HashMap<String, List<String>> cLore = LoreConfiguration.get(key);

                    for (String sLore : cLore.get("head")) {
                        lore.add(Component.text(sLore));
                    }

                    lore.add(Component.text(""));

                    for (String sLore : cLore.get("attribute")) {
                        lore.add(Component.text(sLore));
                    }

                    lore.add(Component.text(""));

                }
            }

            List<String> loreList = LoreConfiguration.get("weight").get("attribute");
            for (String sLore : loreList) {
                lore.add(Component.text(sLore));
            }

        }

        return new HistoriaItem(id, displayName, baseMaterial, lore, weight, components);
    }

    public void putComponent(String key, ItemComponent components) {
        this.components.put(key, components);
    }

    public void putComponents(HashMap<String, ItemComponent> components) {
        this.components.putAll(components);
    }

    public String getConfigurationId() {
        return this.id;
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
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the components
     */
    public Map<String, ItemComponent> getComponentHolder() {
        return this.components;
    }

    /**
     * Creates a default {@link ItemStack} with this configuration.
     *
     * @return the created {@link ItemStack}.
     *
     */
    public ItemStack createItemStack() {

        // invalid material
        assert (baseMaterial != null && baseMaterial != Material.AIR);

        ItemStack stack = new ItemStack(baseMaterial);
        ItemMeta meta = stack.getItemMeta();
        TextComponent textComponent = Component.text(getDisplayName());

        PDCUtils.setInContainer(meta, Main.getNamespacedKey("item-id"),
                PersistentDataType.STRING, id);

        meta.displayName(textComponent);
        meta.lore(lore);
        stack.setItemMeta(meta);

        for (ItemComponent component : this.components.values()) {
            component.applyDefaultData(stack);
        }

        return stack;

        // for (ItemComponent component : componentHolder.values()) {
        // component.setDefaultsToMeta(item);
        // }

        // thoughts on applying lore:
        // %placeholder%
        // %weapon.sweeping% where "weapon" is the component and can be found through
        // HistoriaItem.getValue(weapon.sweeping)

        // return item;

    }
}
