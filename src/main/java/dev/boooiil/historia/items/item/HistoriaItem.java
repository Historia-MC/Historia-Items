package dev.boooiil.historia.items.item;

import java.util.*;

import dev.boooiil.historia.items.registry.ItemComponentRegistry;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.general.LoreConfiguration;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class HistoriaItem implements JSONSerializable {

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

    public static HistoriaItem fromConfig(String id, ConfigurationSection section) {
        Material baseMaterial = Material.valueOf(section.getString("material"));
        String displayName = section.getString("display-name");
        Double weight = section.getDouble("weight");

        Map<String, ItemComponent> components = new HashMap<>();
        for (String key : ItemComponentRegistry.keySet()) {
            if (section.contains(key)) {
                ItemComponentType<?> type = ItemComponentRegistry.get(key);
                ConfigurationSection componentSection = section.getConfigurationSection(key);
                components.put(key, type.fromConfig(componentSection));
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
                    lore.add(Component.text("[" + key.toUpperCase() + "}"));

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
            ItemData data = component.data();
            data.apply(stack);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("HistoriaItem");
        sb.append("{");
        sb.append(JSONUtils.fromValue("id", id) + ", ");
        sb.append(JSONUtils.fromValue("displayName", displayName) + ", ");
        sb.append(JSONUtils.fromValue("baseMaterial", baseMaterial.name().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromValue("weight", weight) + ", ");
        sb.append(JSONUtils.fromComponentList("lore", lore) + ", ");
        sb.append(JSONUtils.fromMapAsString("components", components));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("id", id) + ", ");
        sb.append(JSONUtils.fromValue("displayName", displayName) + ", ");
        sb.append(JSONUtils.fromValue("baseMaterial", baseMaterial.name().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromValue("weight", weight) + ", ");
        sb.append(JSONUtils.fromComponentList("lore", lore) + ", ");
        sb.append(JSONUtils.fromMapAsJSON("components", components));
        sb.append("}");

        return sb.toString();
    }

}
