package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.util.KyoriUtils;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

public class WeaponData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, WeaponData> DATA_TYPE = new WeaponData.DataType();

    // private String id;
    private float sweeping;

    public WeaponData(
            // String id,
            float sweeping) {
        // this.id = id;
        this.sweeping = sweeping;
    }

    public static WeaponData fromStack(ItemStack stack) {

        return PDCUtils
                .getFromComplexContainer(stack, Main.getNamespacedKey("weapon-data"), WeaponData.DATA_TYPE)
                .orElse(new WeaponData(0));
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, Main.getNamespacedKey("weapon-data"), WeaponData.DATA_TYPE,
                this);

        ItemMeta meta = stack.getItemMeta();
        AttributeModifier sweepingAttr = new AttributeModifier(Main.getNamespacedKey("weapon-sweeping"),
                this.sweeping,
                AttributeModifier.Operation.ADD_NUMBER);

        meta.addAttributeModifier(Attribute.SWEEPING_DAMAGE_RATIO, sweepingAttr);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);

        // stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        // ItemAttributeModifiers.itemAttributes()
        // .addModifier(Attribute.SWEEPING_DAMAGE_RATIO, sweepingAttr));
    }

    protected void writeLore(ItemStack stack) {

        String configId = PDCUtils.getFromContainer(stack,
                Main.getNamespacedKey("item-id"), PersistentDataType.STRING).orElse("");

        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasLore() || meta.lore().isEmpty()) {
            Logging.debugToConsole(configId, "has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            if (KyoriUtils.contains(component, "<weapon-sweeping>")) {

                Logging.debugToConsole(configId, "has sweeping placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "weapon-sweeping", this.sweeping));

                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public String id() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public float sweeping() {
        return sweeping;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("WeaponData");
        sb.append(toJSON());

        return sb.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("sweep", sweeping));
        sb.append("}");

        return sb.toString();
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, WeaponData> {

        private static final NamespacedKey SWEEPING_KEY = Main.getNamespacedKey("sweeping");

        @Override
        public WeaponData fromPrimitive(PersistentDataContainer container,
                PersistentDataAdapterContext adapterContext) {

            float sweeping = container.get(SWEEPING_KEY, PersistentDataType.FLOAT);

            return new WeaponData(
                    NumberUtils.roundFloat(sweeping, 2));
        }

        @Override
        public Class<WeaponData> getComplexType() {
            return WeaponData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(WeaponData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(SWEEPING_KEY, PersistentDataType.FLOAT, data.sweeping());

            return container;
        }
    }
}
