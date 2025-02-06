package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
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

public class WeaponData implements ItemData {

    private float sweeping;

    public WeaponData(float sweeping) {
        this.sweeping = sweeping;
    }

    public static WeaponData fromStack(ItemStack stack) {

        float sweeping = PDCUtils
                .getFromContainer(stack, Main.getNamespacedKey("weapon-sweeping"), PersistentDataType.FLOAT)
                .orElseThrow();

        return new WeaponData(sweeping);
    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("weapon-sweeping"), PersistentDataType.FLOAT,
                this.sweeping);

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

            if (textComponent.content().contains("<weapon-sweeping>")) {

                Logging.debugToConsole(configId, "has sweeping placeholder.");

                nLore.add(MiniMessage.miniMessage().deserialize(textComponent.content(),
                        Placeholder.component("weapon-sweeping",
                                Component.text(this.sweeping))));
                continue;
            }

            nLore.add(component);

        }

        meta.lore(nLore);
        stack.setItemMeta(meta);
    }

    public float sweeping() {
        return sweeping;
    }

}
