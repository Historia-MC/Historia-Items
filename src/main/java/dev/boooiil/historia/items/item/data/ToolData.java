package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import dev.boooiil.historia.items.item.ItemData;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import dev.boooiil.historia.items.util.KyoriUtils;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ToolData implements ItemData {
        float attackDamage;
        float attackSpeed;
        float knockback;
        int maxDurability;

        public ToolData(float attackDamage, float attackSpeed, float knockback, int maxDurability) {
                this.attackDamage = attackDamage;
                this.attackSpeed = attackSpeed;
                this.knockback = knockback;
                this.maxDurability = maxDurability;
        }

        public static ToolData fromStack(ItemStack stack) {
                float attackDamage = NumberUtils.roundFloat(
                                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-damage"),
                                                PersistentDataType.FLOAT)
                                                .orElse(0f),
                                2);

                float attackSpeed = NumberUtils.roundFloat(
                                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-speed"),
                                                PersistentDataType.FLOAT)
                                                .orElse(0f),
                                2);

                float knockback = NumberUtils.roundFloat(
                                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-knockback"),
                                                PersistentDataType.FLOAT)
                                                .orElse(0f),
                                2);

                int maxDurability = PDCUtils
                                .getFromContainer(stack, Main.getNamespacedKey("tool-durability"),
                                                PersistentDataType.INTEGER)
                                .orElse((int) stack.getType().getMaxDurability());

                return new ToolData(attackDamage, attackSpeed, knockback, maxDurability);
        }

        @Override
        public void apply(ItemStack stack) {
                // TODO: apply modified quality and other information to stack

                // should be fine since item is passed as ref
                applyData(stack);
                applyLore(stack);
        }

        protected void applyData(ItemStack stack) {

                PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT,
                                this.attackDamage - 1);
                PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT,
                                this.attackSpeed - 4);
                PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT,
                                this.knockback);
                PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER,
                                this.maxDurability);

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

        public float attackDamage() {
                return this.attackDamage;
        }

        public float attackSpeed() {
                return this.attackSpeed;
        }

        public float knockback() {
                return this.knockback;
        }

        public int maxDurability() {
                return this.maxDurability;
        }

}
