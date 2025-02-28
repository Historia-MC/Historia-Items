package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.types.Qualities;
import dev.boooiil.historia.items.item.types.Weights;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.KyoriUtils;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

public class ModifierData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ModifierData> DATA_TYPE = new ModifierData.DataType();

    // private String id;
    private Weights weight;
    private Qualities quality;

    public ModifierData(
            // String id,
            Weights weight,
            Qualities quality) {
        // this.id = id;
        this.weight = weight;
        this.quality = quality;
    }

    public static ModifierData fromStack(ItemStack stack) {

        return PDCUtils.getFromComplexContainer(stack, HistoriaItems.getNamespacedKey("modifier-data"),
                ModifierData.DATA_TYPE).orElse(new ModifierData(Weights.LIGHT, Qualities.POOR));

    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, HistoriaItems.getNamespacedKey("modifier-data"),
                ModifierData.DATA_TYPE, this);

    }

    protected void writeLore(ItemStack stack) {

        String configId = PDCUtils.getFromContainer(stack,
                HistoriaItems.getNamespacedKey("item-id"), PersistentDataType.STRING).orElse("");

        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasLore() || meta.lore().isEmpty()) {
            Logging.debugToConsole(configId, "has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            if (KyoriUtils.contains(component, "<modifier-weight>")) {

                Logging.debugToConsole(configId, "has modifier weight placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "modifier-weight", this.weight.getDisplayName()));

                continue;
            }

            if (KyoriUtils.contains(component, "<modifier-quality>")) {

                Logging.debugToConsole(configId, "has modifier quality placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "modifier-quality", this.quality.getDisplayName()));

                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public String id() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public Weights weight() {
        return this.weight;
    }

    public Qualities quality() {
        return this.quality;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ModifierData");
        sb.append(toJSON());

        return sb.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("weight", weight.lowercase()) + ", ");
        sb.append(JSONUtils.fromValue("quality", quality.lowercase()));

        sb.append("}");

        return sb.toString();
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ModifierData> {

        private static final NamespacedKey WEIGHT_KEY = HistoriaItems.getNamespacedKey("weight");
        private static final NamespacedKey QUALITY_KEY = HistoriaItems.getNamespacedKey("quality");

        @Override
        public ModifierData fromPrimitive(PersistentDataContainer container,
                PersistentDataAdapterContext adapterContext) {

            Weights weight = Weights
                    .fromString(container.get(WEIGHT_KEY, PersistentDataType.STRING));
            Qualities quality = Qualities
                    .fromString(container.get(QUALITY_KEY, PersistentDataType.STRING));

            return new ModifierData(weight, quality);
        }

        @Override
        public Class<ModifierData> getComplexType() {
            return ModifierData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ModifierData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(WEIGHT_KEY, PersistentDataType.STRING, data.weight.lowercase());
            container.set(QUALITY_KEY, PersistentDataType.STRING, data.quality().lowercase());

            return container;
        }
    }
}
