package dev.boooiil.historia.items.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.item.ItemData;
import dev.boooiil.historia.items.item.types.Qualities;
import dev.boooiil.historia.items.item.types.Weights;
import dev.boooiil.historia.items.util.KyoriUtils;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.PDCUtils;
import net.kyori.adventure.text.Component;

public class ModifierData implements ItemData {

    private Weights weight;
    private Qualities quality;

    public ModifierData(
            Weights weight,
            Qualities quality) {
        this.weight = weight;
        this.quality = quality;
    }

    public static ModifierData fromStack(ItemStack stack) {

        Weights weight = Weights.fromString(
                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("modifier-weight"), PersistentDataType.STRING)
                        .orElse(""));

        Qualities quality = Qualities.fromString(
                PDCUtils.getFromContainer(stack, Main.getNamespacedKey("modifier-quality"), PersistentDataType.STRING)
                        .orElse(""));

        return new ModifierData(weight, quality);

    }

    @Override
    public void apply(ItemStack stack) {
        writeData(stack);
        writeLore(stack);
    }

    protected void writeData(ItemStack stack) {

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("modifier-weight"), PersistentDataType.STRING,
                this.weight.lowercase());

        PDCUtils.setInContainer(stack, Main.getNamespacedKey("modifier-quality"), PersistentDataType.STRING,
                this.quality.lowercase());

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

    public Weights weight() {
        return this.weight;
    }

    public Qualities quality() {
        return this.quality;
    }

    @Override
    public String toString() {
        return "ModifierData[" +
                "weight=" + weight.lowercase() + ", " +
                "quality=" + quality.lowercase() + ']';
    }
}
