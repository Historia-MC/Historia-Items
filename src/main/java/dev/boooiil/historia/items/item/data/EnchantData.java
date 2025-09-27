package dev.boooiil.historia.items.item.data;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.util.PDCUtils;
import dev.boooiil.historia.core.util.JSONUtils;

public class EnchantData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, EnchantData> DATA_TYPE = new EnchantData.DataType();
    public static final NamespacedKey KEY = HistoriaItems.getNamespacedKey("enchant");

    private HashMap<Enchantment, Integer> enchantments;

    public EnchantData(
            HashMap<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public static EnchantData fromStack(ItemStack stack) {
        return PDCUtils.getFromComplexContainer(stack, EnchantData.KEY, EnchantData.DATA_TYPE)
                .orElse(new EnchantData(new HashMap<>()));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
    }

    public void writeData(ItemStack stack) {
        PDCUtils.setInComplexContainer(stack, EnchantData.KEY, EnchantData.DATA_TYPE, this);
    }

    public HashMap<Enchantment, Integer> enchantments() {
        return this.enchantments;
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, EnchantData> {

        @Override
        public EnchantData fromPrimitive(PersistentDataContainer container,
                PersistentDataAdapterContext adapterContext) {

            HashMap<Enchantment, Integer> enchantments = new HashMap<>();

            for (NamespacedKey enchant : container.getKeys()) {
                Enchantment enchantment = Enchantment.getByName(enchant.getKey());

                if (enchantment == null) {
                    continue;
                }

                enchantments.put(enchantment, container.get(enchant, PersistentDataType.INTEGER));
            }

            return new EnchantData(enchantments);
        }

        @Override
        public Class<EnchantData> getComplexType() {
            return EnchantData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(EnchantData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            for (Enchantment enchant : data.enchantments.keySet()) {
                container.set(enchant.getKey(), PersistentDataType.INTEGER, data.enchantments.get(enchant));
            }

            return container;
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("EnchantData");
        sb.append("{");
        sb.append(JSONUtils.fromMap("enchantments", enchantments, true));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromMap("enchantments", enchantments));
        sb.append("}");

        return sb.toString();

    }

}
