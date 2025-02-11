package dev.boooiil.historia.items.item.component;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ArmorData;
import dev.boooiil.historia.items.util.JSONUtils;
import dev.boooiil.historia.items.util.NumberUtils;

@NullMarked
public class ArmorComponent implements ItemComponent {
    private final List<Float> defenseRange;
    private final List<Integer> durabilityRange;

    public ArmorComponent(
            List<Float> defenseRange,
            List<Integer> durabilityRange) {
        this.defenseRange = defenseRange;
        this.durabilityRange = durabilityRange;
    }

    public static ArmorComponent fromConfig(ConfigurationSection section) {
        return new ArmorComponent(
                section.getFloatList("defense"),
                section.getIntegerList("durability"));
    }

    public List<Float> defenseRange() {
        return defenseRange;
    }

    public List<Integer> durabilityRange() {
        return durabilityRange;
    }

    @Override
    public ArmorData apply() {

        float defense = NumberUtils
                .roundFloat(NumberUtils.random(this.defenseRange().get(0), this.defenseRange().get(1)), 2);
        int durability = NumberUtils.randomInt(this.durabilityRange().get(0), this.durabilityRange().get(1));

        return new ArmorData(defense, durability);
    }

    @Override
    public ArmorData apply(float qualityModifier) {
        return apply();
    }

    @Override
    public String getKey() {
        return "armor";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("ArmorComponent{");
        sb.append(JSONUtils.fromFloatList("defenseRange", defenseRange) + ", ");
        sb.append(JSONUtils.fromIntegerList("durabilityRange", durabilityRange));
        sb.append("}");

        return sb.toString();
    }
}
