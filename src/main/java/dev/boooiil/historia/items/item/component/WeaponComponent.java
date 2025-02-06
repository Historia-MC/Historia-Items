package dev.boooiil.historia.items.item.component;

import java.util.List;
import java.util.Objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.WeaponData;
import dev.boooiil.historia.items.util.NumberUtils;

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
    public void applyDefaultData(ItemStack item) {
        float sweeping = NumberUtils
                .roundFloat(NumberUtils.random(this.sweepingRange().get(0), this.sweepingRange().get(1)), 2);

        WeaponData weaponData = new WeaponData(sweeping);
        weaponData.apply(item);
    }

    @Override
    public String getKey() {
        return "weapon";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (WeaponComponent) obj;
        return Objects.equals(this.sweepingRange, that.sweepingRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sweepingRange);
    }

    @Override
    public String toString() {
        return "WeaponComponent[" +
                "sweepingRange=" + sweepingRange + ']';
    }

}
