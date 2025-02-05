package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import dev.boooiil.historia.items.item.ItemData;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ToolData implements ItemData {
    float attackDamage;
    float attackSpeed;
    float knockback;
    int maxDurability;

    public ToolData() {
        this(0, 0, 0, 1);
    }

    public ToolData(float attackDamage, float attackSpeed, float knockback, int maxDurability) {
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.knockback = knockback;
        this.maxDurability = maxDurability;
    }

    @Override
    public void read(ItemStack stack) {
        this.attackDamage = NumberUtils.roundFloat(
                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT)
                        .orElse(0f),
                2);

        this.attackSpeed = NumberUtils.roundFloat(
                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT)
                        .orElse(0f),
                2);

        this.knockback = NumberUtils.roundFloat(
                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT)
                        .orElse(0f),
                2);

        this.maxDurability = PDCUtils.getFromContainer(stack, Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER)
                .orElse((int) stack.getType().getMaxDurability());
    }

    @Override
    public void apply(ItemStack stack) {
        // TODO: apply modified quality and other information to stack

        // should be fine since item is passed as ref
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT,
                this.attackDamage);
        PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT,
                this.attackSpeed);
        PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT,
                this.knockback);
        PDCUtils.setInContainer(stack, Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER,
                this.maxDurability);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);

        AttributeModifier damageAttr = new AttributeModifier(Main.getNamespacedKey("tool-damage"),
                this.attackDamage - 1,
                AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier speedAttr = new AttributeModifier(Main.getNamespacedKey("tool-speed"),
                this.attackSpeed - 4,
                AttributeModifier.Operation.ADD_NUMBER);
        AttributeModifier knockbackAttr = new AttributeModifier(Main.getNamespacedKey("tool-knockback"),
                this.knockback,
                AttributeModifier.Operation.ADD_NUMBER);

        stack.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.itemAttributes()
                .addModifier(Attribute.ATTACK_DAMAGE, damageAttr)
                .addModifier(Attribute.ATTACK_SPEED, speedAttr)
                .addModifier(Attribute.ATTACK_KNOCKBACK, knockbackAttr)
        );
        stack.setData(DataComponentTypes.MAX_DAMAGE, this.maxDurability);
    }

    protected void writeLore(ItemStack stack) {

        String configId = PDCUtils.getFromContainer(PDCUtils.getContainer(stack), Main.getNamespacedKey("config-id"), PersistentDataType.STRING);


        ItemMeta meta = stack.getItemMeta();


        if (!meta.hasLore() || meta.lore().isEmpty()) {
            Logging.debugToConsole(configId, "has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            TextComponent textComponent = (TextComponent) component;

            Logging.debugToConsole(configId, "" + textComponent);

            if (textComponent.content().contains("<tool-damage>")) {

                Logging.debugToConsole(configId, "has damage placeholder.");

                nLore.add(MiniMessage.miniMessage().deserialize(textComponent.content(),
                        Placeholder.component("tool-damage", Component.text(this.attackDamage))));
                continue;
            }
            if (textComponent.content().contains("<tool-speed>")) {

                Logging.debugToConsole(configId, "has speed placeholder.");

                nLore.add(MiniMessage.miniMessage().deserialize(textComponent.content(),
                        Placeholder.component("tool-speed", Component.text(this.attackSpeed))));
                continue;
            }
            if (textComponent.content().contains("<tool-knockback>")) {

                Logging.debugToConsole(configId, "has knockback placeholder.");

                nLore.add(MiniMessage.miniMessage().deserialize(textComponent.content(),
                        Placeholder.component("tool-knockback", Component.text(this.knockback))));
                continue;
            }
            if (textComponent.content().contains("<tool-durability>")) {

                Logging.debugToConsole(configId, "has durability placeholder.");

                nLore.add(MiniMessage.miniMessage().deserialize(textComponent.content(),
                        Placeholder.component("tool-damage", Component.text(this.maxDurability))));
                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }
}
