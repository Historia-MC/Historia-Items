package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class ArmorData implements ItemData {

    private float defense;
    private int maxDurability;

    public ArmorData(float defense, int maxDurability) {
        this.defense = defense;
        this.maxDurability = maxDurability;
    }

    public static ArmorData fromStack(ItemStack stack) {

        float defense = PDCUtils
                .getFromContainer(stack, Main.getNamespacedKey("armor-defense"), PersistentDataType.FLOAT).orElse(0f);

        int durability = PDCUtils
                .getFromContainer(stack, Main.getNamespacedKey("armor-durability"), PersistentDataType.INTEGER)
                .orElse(1);

        return new ArmorData(defense, durability);
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("armor-defense"), PersistentDataType.FLOAT,
                this.defense);

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("armor-durability"), PersistentDataType.INTEGER,
                this.maxDurability);

        AttributeModifier defenseAttr = new AttributeModifier(Main.getNamespacedKey("armor-defense"),
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

        String configId = PDCUtils.getFromContainer(stack,
                Main.getNamespacedKey("config-id"), PersistentDataType.STRING).orElse("");

        ItemMeta meta = stack.getItemMeta();

        if (!meta.hasLore() || meta.lore().isEmpty()) {
            Logging.debugToConsole(configId, "has no lore, skipping placeholder.");
            return;
        }

        List<Component> lore = meta.lore();
        List<Component> nLore = new ArrayList<>();

        for (Component component : lore) {

            TextComponent textComponent = (TextComponent) component;

            if (textComponent.content().contains("<armor-defense>")) {

                Logging.debugToConsole(configId, "has defense placeholder.");

                nLore.add(MiniMessage.miniMessage().deserialize(textComponent.content(),
                        Placeholder.component("armor-defense",
                                Component.text(this.defense))));
                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public float getDefense() {
        return this.defense;
    }

    public int maxDurability() {
        return maxDurability;
    }
}
