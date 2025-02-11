package dev.boooiil.historia.items.item.component;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ModifierData;
import dev.boooiil.historia.items.item.types.Qualities;
import dev.boooiil.historia.items.item.types.Weights;
import dev.boooiil.historia.core.util.JSONUtils;

@NullMarked
public class ModifierComponent implements ItemComponent {

    private final Weights weight;
    private final Qualities quality;

    public ModifierComponent(
            Weights weight,
            Qualities quality) {
        this.weight = weight;
        this.quality = quality;
    }

    public static ModifierComponent fromConfig(ConfigurationSection section) {

        Weights weight = Weights.fromString(section.getString("weight"));
        Qualities quality = Qualities.fromString(section.getString("quality"));

        return new ModifierComponent(
                weight,
                quality);
    }

    public Weights weight() {
        return weight;
    }

    public Qualities quality() {
        return quality;
    }

    @Override
    public ModifierData apply() {
        return new ModifierData(weight, quality);
    }

    @Override
    public ModifierData apply(float qualityModifier) {
        return apply();
    }

    @Override
    public String getKey() {
        return "modifier";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ModifierComponent");
        sb.append(toJSON());

        return sb.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("weight", weight.lowercase()));
        sb.append(JSONUtils.fromValue("quality", quality.lowercase()));
        sb.append("}");

        return sb.toString();
    }

}
