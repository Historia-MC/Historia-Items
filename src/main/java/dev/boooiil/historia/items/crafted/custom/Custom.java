package dev.boooiil.historia.items.crafted.custom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import dev.boooiil.historia.items.util.Construct;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.crafted.BaseItem;

public class Custom extends BaseItem {

    public Custom(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    public Custom(Material material, int amount, String displayName, List<String> lore) {
        super(Construct.itemStack(material, amount, displayName, new ArrayList<>(lore)), true);
    }

    public Custom(@NotNull CustomItemConfiguration configuration) {
        super(Construct.itemStack(configuration.getMaterial(), configuration.getAmount(),
                configuration.getDisplayName(),
                new ArrayList<>(configuration.getLore())), true);
    }

}
