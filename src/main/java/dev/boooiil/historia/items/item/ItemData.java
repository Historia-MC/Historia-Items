package dev.boooiil.historia.items.item;

import org.bukkit.inventory.ItemStack;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface ItemData {

    static void set(ItemStack stack, @Nullable ItemData data) {
        if (data != null) {
            data.apply(stack);
        }
    }

    static @Nullable <T extends ItemData> T get(ItemStack stack, Class<T> dataType) {
        try {
            T data = dataType.getDeclaredConstructor().newInstance();
            data.read(stack);

            return data;

        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    void read(ItemStack stack);

    void apply(ItemStack stack);
}
