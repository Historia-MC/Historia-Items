package dev.boooiil.historia.items.item.data;

import dev.boooiil.historia.core.util.JSONUtils;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.util.KyoriUtils;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
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
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

/**
 * ToolData class for interfacing with tool data in an item.
 */
@NullMarked
public class ToolData implements ItemData {

    public static final PersistentDataType<PersistentDataContainer, ToolData> DATA_TYPE = new ToolData.DataType();

    // private String id;
    private float attackDamage;
    private float attackSpeed;
    private float knockback;
    private int maxDurability;

    /**
     * Constructor for ToolData class.
     *
     * @param attackDamage  Attack damage of the tool.
     * @param attackSpeed   Attack speed of the tool.
     * @param knockback     Knockback of the tool.
     * @param maxDurability Max durability of the tool.
     */

    public ToolData(
            // String id,
            float attackDamage,
            float attackSpeed,
            float knockback,
            int maxDurability) {
        // this.id = id;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.knockback = knockback;
        this.maxDurability = maxDurability;
    }

    /**
     * Get tool data from an item stack.
     *
     * @param stack ItemStack to get data from.
     * @return ToolData object containing the tool data.
     */
    public static ToolData fromStack(ItemStack stack) {

        return PDCUtils.getFromComplexContainer(stack, Main.getNamespacedKey("tool-data"),
                ToolData.DATA_TYPE).orElse(new ToolData(0, 0, 0, 1));

    }

    /**
     * Applies the tool data to an item stack.
     *
     * @param stack The item stack to apply the data to.
     */
    @Override
    public void apply(ItemStack stack) {
        // TODO: apply modified quality and other information to stack

        // should be fine since item is passed as ref
        applyData(stack);
        applyLore(stack);
    }

    /**
     * Apply the item's data to the given {@link ItemStack}'s
     * {@link PersistentDataContainer}.
     *
     * @param stack the {@link ItemStack} to apply the data to.
     */

    protected void applyData(ItemStack stack) {

        PDCUtils.setInComplexContainer(stack, Main.getNamespacedKey("tool-data"),
                ToolData.DATA_TYPE, this);

        AttributeModifier damageAttr = new AttributeModifier(Main.getNamespacedKey("tool-damage"),
                this.attackDamage - 1, AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier speedAttr = new AttributeModifier(Main.getNamespacedKey("tool-speed"),
                this.attackSpeed - 4, AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier knockbackAttr = new AttributeModifier(Main.getNamespacedKey("tool-knockback"),
                this.knockback, AttributeModifier.Operation.ADD_NUMBER);

        ItemMeta meta = stack.getItemMeta();
        Damageable damageable = (Damageable) meta;

        damageable.addAttributeModifier(Attribute.ATTACK_DAMAGE, damageAttr);
        damageable.addAttributeModifier(Attribute.ATTACK_SPEED, speedAttr);
        damageable.addAttributeModifier(Attribute.ATTACK_KNOCKBACK, knockbackAttr);

        damageable.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        damageable.setMaxDamage(this.maxDurability);

        stack.setItemMeta(damageable);
        // stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);

        // stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        // ItemAttributeModifiers.itemAttributes()
        // .addModifier(Attribute.ATTACK_DAMAGE, damageAttr)
        // .addModifier(Attribute.ATTACK_SPEED, speedAttr)
        // .addModifier(Attribute.ATTACK_KNOCKBACK, knockbackAttr)
        // );
        // stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);
    }

    /**
     * Apply the item's lore to the given {@link ItemStack}.
     *
     * @param stack the {@link ItemStack} to apply the lore to.
     */
    protected void applyLore(ItemStack stack) {

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

            if (KyoriUtils.contains(component, "<tool-damage>")) {

                Logging.debugToConsole(configId, "has damage placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "tool-damage",
                        attackDamage));
                continue;
            }
            if (KyoriUtils.contains(component, "<tool-speed>")) {

                Logging.debugToConsole(configId, "has speed placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "tool-speed",
                        attackSpeed));
                continue;
            }
            if (KyoriUtils.contains(component, "<tool-knockback>")) {

                Logging.debugToConsole(configId, "has knockback placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "tool-knockback",
                        knockback));
                continue;
            }
            if (KyoriUtils.contains(component, "<tool-durability>")) {

                Logging.debugToConsole(configId, "has durability placeholder.");

                nLore.add(KyoriUtils.replaceComponent(component, "tool-durability",
                        maxDurability));
                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    /**
     * Get the Registry ID of the item.
     *
     * @return The ID of the item.
     */
    public String id() {
        throw new UnsupportedOperationException("Not imlpemented.");
    }

    /**
     * Get the attack damage of the item.
     *
     * @return The attack damage of the item.
     */
    public float attackDamage() {
        return this.attackDamage;
    }

    /**
     * Get the attack speed of the item.
     *
     * @return The attack speed of the item.
     */
    public float attackSpeed() {
        return this.attackSpeed;
    }

    /**
     * Get the knockback of the item.
     *
     * @return The knockback of the item.
     */
    public float knockback() {
        return this.knockback;
    }

    /**
     * Get the max durability of the item.
     *
     * @return The max durability of the item.
     */
    public int maxDurability() {
        return this.maxDurability;
    }

    /**
     * Return the formatted string representation of the ToolData object.
     *
     * @return The formatted string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ToolData{");
        sb.append(toJSON());

        return sb.toString();
    }

    /**
     * Convert the ToolData object to a JSON formatted string.
     *
     * @return The JSON formatted string.
     */
    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("attackDamage", attackDamage) + ", ");
        sb.append(JSONUtils.fromValue("attackSpeed", attackSpeed) + ", ");
        sb.append(JSONUtils.fromValue("knockback", knockback) + ", ");
        sb.append(JSONUtils.fromValue("maxDurability", maxDurability));
        sb.append("}");

        return sb.toString();
    }

    @NullMarked
    private static class DataType implements PersistentDataType<PersistentDataContainer, ToolData> {

        private static final NamespacedKey DAMAGE_KEY = Main.getNamespacedKey("damage");
        private static final NamespacedKey SPEED_KEY = Main.getNamespacedKey("speed");
        private static final NamespacedKey KNOCKBACK_KEY = Main.getNamespacedKey("knockback");
        private static final NamespacedKey DURABILITY_KEY = Main.getNamespacedKey("durability");
            
        @Override
        public ToolData fromPrimitive(PersistentDataContainer container, PersistentDataAdapterContext adapterContext) {

            float damage = container.get(DAMAGE_KEY, PersistentDataType.FLOAT);
            float speed = container.get(SPEED_KEY, PersistentDataType.FLOAT);
            float knockback = container.get(KNOCKBACK_KEY, PersistentDataType.FLOAT);
            int durability = container.get(DURABILITY_KEY, PersistentDataType.INTEGER);

            return new ToolData(
                    NumberUtils.roundFloat(damage, 2),
                    NumberUtils.roundFloat(speed, 2),
                    NumberUtils.roundFloat(knockback, 2),
                    durability);
        }

        @Override
        public Class<ToolData> getComplexType() {
            return ToolData.class;
        }

        @Override
        public Class<PersistentDataContainer> getPrimitiveType() {
            return PersistentDataContainer.class;
        }

        @Override
        public PersistentDataContainer toPrimitive(ToolData data, PersistentDataAdapterContext adapterContext) {

            PersistentDataContainer container = adapterContext.newPersistentDataContainer();

            container.set(DAMAGE_KEY, PersistentDataType.FLOAT, data.attackDamage() - 1);
            container.set(SPEED_KEY, PersistentDataType.FLOAT, data.attackSpeed() - 4);
            container.set(KNOCKBACK_KEY, PersistentDataType.FLOAT, data.knockback());
            container.set(DURABILITY_KEY, PersistentDataType.INTEGER, data.maxDurability());

            return container;
        }
    }
}
