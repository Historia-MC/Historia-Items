package dev.boooiil.historia.items.item.component;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.WeaponData;
import dev.boooiil.historia.items.util.NumberUtils;

@NullMarked
public final class WeaponComponent implements ItemComponent {
    private final List<Float> sweepingRange;

    public WeaponComponent(List<Float> sweepingRange) {
        this.sweepingRange = sweepingRange;
    }

    public static WeaponComponent fromConfig(ConfigurationSection section) {
        return new WeaponComponent(section.getFloatList("sweeping"));
    }

    public List<Float> sweepingRange() {
        return sweepingRange;
    }

    @Override
    public WeaponData applyDefaultData() {
        float sweeping = NumberUtils
                .roundFloat(NumberUtils.random(this.sweepingRange().get(0), this.sweepingRange().get(1)), 2);

        return new WeaponData(sweeping);
    }

    @Override
    public String getKey() {
        return "weapon";
    }

    @Override
    public String toString() {
        return "WeaponComponent[" +
                "sweepingRange=" + sweepingRange + ']';
    }

}
