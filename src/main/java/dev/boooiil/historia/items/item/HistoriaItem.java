package dev.boooiil.historia.items.item;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.configuration.general.LoreConfiguration;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class HistoriaItem implements JSONSerializable {

    private final NamespacedKey id;
    private final String displayName;
    private final Material baseMaterial;
    private final List<Component> lore;

    /**
     * The weight of the item in KG. We are a metric society, damn the imperialists.
     */
    private final double weight;

    private final Map<NamespacedKey, ItemComponent> components;

    public HistoriaItem(
            NamespacedKey id,
            String displayName,
            Material baseMaterial,
            List<Component> lore,
            double weight,
            Map<NamespacedKey, ItemComponent> components) {
        this.id = id;
        this.displayName = displayName;
        this.baseMaterial = baseMaterial;
        this.lore = lore;
        this.weight = weight;
        this.components = components;
    }

    public static HistoriaItem fromConfig(NamespacedKey id, ConfigurationSection section) {
        Material baseMaterial = Material.valueOf(section.getString("material"));
        String displayName = section.getString("display-name");
        Double weight = section.getDouble("weight");

        Map<NamespacedKey, ItemComponent> components = new HashMap<>();
        for (NamespacedKey key : HistoriaItems.COMPONENT_REGISTRY.allKeys()) {
            if (section.contains(key.getKey())) {
                ItemComponentType<?> type = HistoriaItems.COMPONENT_REGISTRY.get(key);
                ConfigurationSection componentSection = section.getConfigurationSection(key.getKey());
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

            for (NamespacedKey key : components.keySet()) {

                String s_key = key.getKey();

                if (LoreConfiguration.contains(s_key)) {
                    lore.add(Component.text("[" + s_key.toUpperCase() + "}"));

                    HashMap<String, List<String>> cLore = LoreConfiguration.get(s_key);

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

    public void putComponent(NamespacedKey key, ItemComponent components) {
        this.components.put(key, components);
    }

    public void putComponents(HashMap<NamespacedKey, ItemComponent> components) {
        this.components.putAll(components);
    }

    public NamespacedKey getConfigurationId() {
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
    public Map<NamespacedKey, ItemComponent> getComponentHolder() {
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

        PDCUtils.setInContainer(meta, HistoriaItems.getNamespacedKey("item-id"),
                PersistentDataType.STRING, id.getKey());

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
        sb.append(JSONUtils.fromValue("id", id.getKey()) + ", ");
        sb.append(JSONUtils.fromValue("displayName", displayName) + ", ");
        sb.append(JSONUtils.fromValue("baseMaterial", baseMaterial.name().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromValue("weight", weight) + ", ");
        sb.append(JSONUtils.fromComponentList("lore", lore) + ", ");
        sb.append(JSONUtils.fromMap("components", components, true));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("id", id.getKey()) + ", ");
        sb.append(JSONUtils.fromValue("displayName", displayName) + ", ");
        sb.append(JSONUtils.fromValue("baseMaterial", baseMaterial.name().toLowerCase()) + ", ");
        sb.append(JSONUtils.fromValue("weight", weight) + ", ");
        sb.append(JSONUtils.fromComponentList("lore", lore) + ", ");
        sb.append(JSONUtils.fromMap("components", components));
        sb.append("}");

        return sb.toString();
    }

}
