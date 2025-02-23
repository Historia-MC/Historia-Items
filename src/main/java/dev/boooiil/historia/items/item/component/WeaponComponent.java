package dev.boooiil.historia.items.item.component;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.WeaponData;
import dev.boooiil.historia.core.util.JSONUtils;
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
    public WeaponData data() {
        float sweeping = NumberUtils
                .roundFloat(NumberUtils.random(this.sweepingRange().get(0), this.sweepingRange().get(1)), 2);

        return new WeaponData(sweeping);
    }

    @Override
    public WeaponData data(float qualityModifier) {
        return data();
    }

    @Override
    public String getKey() {
        return "weapon";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("WeaponComponent");
        sb.append(toJSON());

        return sb.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromFloatList("sweepRange", sweepingRange));
        sb.append("}");

        return sb.toString();
    }

}
