package dev.boooiil.historia.items.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.registry.ItemComponentRegistry;

@NullMarked
public class HistoriaItemData {

    private final NamespacedKey id;
    private final List<NamespacedKey> itemData;
    private final ItemStack stack;

    public HistoriaItemData(
            NamespacedKey id,
            List<NamespacedKey> itemData,
            ItemStack stack) {
        this.id = id;
        this.itemData = itemData;
        this.stack = stack;
    }

    public static HistoriaItemData fromStack(ItemStack stack) {
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        String id = "";
        List<NamespacedKey> itemData = new ArrayList<>();

        for (NamespacedKey key : container.getKeys()) {
            String s_key = key.getKey().toLowerCase();

            if (s_key.equals("item-id")) {
                id = container.get(key, PersistentDataType.STRING);
            }

            else if (s_key.endsWith("-data")) {
                itemData.add(key);
            }

        }

        return new HistoriaItemData(HistoriaItems.getNamespacedKey(id), itemData, stack);
    }

    public boolean isHistoriaItem() {
        return this.id != null && !this.id.equals("");
    }

    public boolean hasData(NamespacedKey key) {
        return itemData.contains(key);
    }

    public HistoriaItem getHistoriaItem() {
        return HistoriaItems.ITEM_REGISTRY.get(id);
    }

    public <C, T extends ItemData> ItemData getData(
            NamespacedKey key,
            PersistentDataType<C, T> type) {

        if (!hasData(key)) {
            String regKey = key.getKey().replace("-data", "");
            return ItemComponentRegistry.get(regKey).getData();
        }

        return stack.getItemMeta().getPersistentDataContainer().get(key, type);
    }

}
