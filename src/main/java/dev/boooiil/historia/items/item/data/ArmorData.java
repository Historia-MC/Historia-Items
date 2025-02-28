package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.util.HILogger;
import dev.boooiil.historia.items.util.PDCUtils;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.KyoriUtils;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

public class ArmorData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ArmorData> DATA_TYPE = new ArmorData.DataType();

    // private String id;
    private float defense;
    private int maxDurability;

    public ArmorData(
            // String id,
            float defense,
            int maxDurability) {
        // this.id = id;
        this.defense = defense;
        this.maxDurability = maxDurability;
    }

    public static ArmorData fromStack(ItemStack stack) {

        return PDCUtils
                .getFromComplexContainer(stack, HistoriaItems.getNamespacedKey("armor-data"), ArmorData.DATA_TYPE)
                .orElse(new ArmorData(0, 1));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, HistoriaItems.getNamespacedKey("armor-data"), ArmorData.DATA_TYPE,
                this);

        AttributeModifier defenseAttr = new AttributeModifier(HistoriaItems.getNamespacedKey("armor-defense"),
                this.defense, AttributeModifier.Operation.ADD_NUMBER);

        ItemMeta meta = stack.getItemMeta();
        Damageable damageable = (Damageable) meta;

        damageable.addAttributeModifier(Attribute.ARMOR, defenseAttr);

        damageable.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        damageable.setMaxDamage(this.maxDurability);

        assert maxDurability > 1;

        stack.setItemMeta(damageable);

        // stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);

        // stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        // ItemAttributeModifiers.itemAttributes()
        // .addModifier(Attribute.SWEEPING_DAMAGE_RATIO, sweepingAttr));
    }

    protected void writeLore(ItemStack stack) {

        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasLore() || meta.lore().isEmpty()) {
            HILogger.debugToConsole("Armor has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            if (KyoriUtils.contains(component, "<armor-defense>")) {

                HILogger.debugToConsole("Armor has defense placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "armor-defense", this.defense));

                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public String id() {
        throw new UnsupportedOperationException("Not imlpemented.");
    }

    public float getDefense() {
        return this.defense;
    }

    public int maxDurability() {
        return maxDurability;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ArmorData");
        sb.append(toJSON());

        return sb.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("defense", defense) + ", ");
        sb.append(JSONUtils.fromValue("maxDurability", maxDurability));

        sb.append("}");

        return sb.toString();
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ArmorData> {

        private static final NamespacedKey DEFENSE_KEY = HistoriaItems.getNamespacedKey("defense");
        private static final NamespacedKey DURABILITY_KEY = HistoriaItems.getNamespacedKey("durability");

        @Override
        public ArmorData fromPrimitive(PersistentDataContainer container, PersistentDataAdapterContext adapterContext) {

            float defense = container.get(DEFENSE_KEY, PersistentDataType.FLOAT);
            int maxDurability = container.get(DURABILITY_KEY, PersistentDataType.INTEGER);

            return new ArmorData(defense, maxDurability);
        }

        @Override
        public Class<ArmorData> getComplexType() {
            return ArmorData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ArmorData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(DEFENSE_KEY, PersistentDataType.FLOAT, data.getDefense());
            container.set(DURABILITY_KEY, PersistentDataType.INTEGER, data.maxDurability());

            return container;
        }
    }
}
