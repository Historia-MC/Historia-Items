package dev.boooiil.historia.items.configuration.item.data.tool;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.configuration.item.components.tool.ToolComponent;
import dev.boooiil.historia.items.configuration.item.data.IItemData;
import dev.boooiil.historia.items.util.NumberUtils;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class ToolData implements IItemData {

    private ItemStack item;

    public ToolData(ItemStack item) {
        this.item = item;
    }

    @Override
    public ToolComponent getItemComponent() {
        return (ToolComponent) ItemConfigurationRegistry.get(this.getConfigurationId()).getComponentHolder()
                .get("tool");
    }

    @Override
    public ItemStack apply() {

        // TODO: apply modified quality and other information to stack

        // should be fine since item is passed as ref
        applyToDataContainer();
        applyToLorePlaceholder();
        return item;
    }

    public ItemStack applyToDataContainer() {

        ItemMeta meta = getItemMeta(item);
        PersistentDataContainer container = PDCUtils.getContainer(meta);
        ToolComponent tc = getItemComponent();

        float damage = NumberUtils
                .roundFloat(NumberUtils.random(tc.getDamageRange().get(0), tc.getDamageRange().get(1)), 2);
        float speed = NumberUtils
                .roundFloat(NumberUtils.random(tc.getSpeedRange().get(0), tc.getSpeedRange().get(1)), 2);
        float knockback = NumberUtils
                .roundFloat(NumberUtils.random(tc.getKnockbackRange().get(0), tc.getKnockbackRange().get(1)), 2);
        int durability = NumberUtils.randomInt(tc.getDurabilityRange().get(0), tc.getDurabilityRange().get(1));

        container.set(Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT,
                damage);
        container.set(Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT,
                speed);
        container.set(Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT,
                knockback);
        container.set(Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER,
                durability);

        item.setItemMeta(meta);

        return item;

    }

    public ItemStack applyToLorePlaceholder() {

        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();

        for (Component component : lore) {

            TextComponent textComponent = (TextComponent) component;

            if (textComponent.content().contains("%tool.damage%")) {
                textComponent.content(
                        textComponent
                                .content()
                                .replace("%tool.damage%", "" + getDamage()));
                continue;
            }
            if (textComponent.content().contains("%tool.speed%")) {
                textComponent.content(
                        textComponent
                                .content()
                                .replace("%tool.speed%", "" + getSpeed()));
                continue;
            }
            if (textComponent.content().contains("%tool.knockback%")) {
                textComponent.content(
                        textComponent
                                .content()
                                .replace("%tool.knockback%", "" + getKnockback()));
                continue;
            }
            if (textComponent.content().contains("%tool.durability%")) {
                textComponent.content(
                        textComponent
                                .content()
                                .replace("%tool.durability%", "" + getDurability()));
                continue;
            }

        }

        item.setItemMeta(meta);
        return item;
    }

    public ToolData fromItemStack(ItemStack item) {
        this.item = item;
        return this;
    }

    @Override
    public String getConfigurationId() {
        return PDCUtils.getFromContainer(PDCUtils.getContainer(item), Main.getNamespacedKey("config-id"),
                PersistentDataType.STRING);
    }

    public float getDamage() {
        return NumberUtils.roundFloat(
                PDCUtils.getFromContainer(this.item, Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT),
                2);
    }

    public float getSpeed() {
        return NumberUtils.roundFloat(
                PDCUtils.getFromContainer(this.item, Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT), 2);
    }

    public float getKnockback() {
        return NumberUtils.roundFloat(
                PDCUtils.getFromContainer(this.item, Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT),
                2);

    }

    public int getDurability() {
        return PDCUtils.getFromContainer(this.item, Main.getNamespacedKey("tool-durability"),
                PersistentDataType.INTEGER);

    }

    private ItemMeta getItemMeta(ItemStack item) {
        return item.getItemMeta();
    }

}
