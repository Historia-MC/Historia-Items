package dev.boooiil.historia.items.util;

import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
public class PDCUtils {

    public static PersistentDataContainer getContainer(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer();
    }

    public static PersistentDataContainer getContainer(ItemMeta itemMeta) {
        return itemMeta.getPersistentDataContainer();
    }

    @Nullable
    public static <T> T getFromContainer(PersistentDataContainer container, NamespacedKey key,
            PersistentDataType<T, T> type) {
        if (container.has(key)) {
            return container.get(key, type);
        } else
            return null;
    }

    public static <T> Optional<T> getFromContainer(ItemStack item, NamespacedKey key,
                                                   PersistentDataType<T, T> type) {
        if (getContainer(item).has(key)) {
            return Optional.ofNullable(getContainer(item).get(key, type));
        } else
            return Optional.empty();
    }

    @Nullable
    public static <T> T getFromContainer(ItemMeta itemMeta, NamespacedKey key,
            PersistentDataType<T, T> type) {
        if (getContainer(itemMeta).has(key)) {
            return getContainer(itemMeta).get(key, type);
        } else
            return null;
    }

    public static <T> void setInContainer(PersistentDataContainer container, NamespacedKey key,
            PersistentDataType<T, T> type, T value) {
        container.set(key, type, value);
    }

    public static <T> void setInContainer(ItemMeta itemMeta, NamespacedKey key,
            PersistentDataType<T, T> type, T value) {
        getContainer(itemMeta).set(key, type, value);
    }

    public static <T> void setInContainer(ItemStack item, NamespacedKey key,
            PersistentDataType<T, T> type, T value) {

        ItemMeta meta = item.getItemMeta();

        getContainer(meta).set(key, type, value);

        item.setItemMeta(meta);
    }

}
