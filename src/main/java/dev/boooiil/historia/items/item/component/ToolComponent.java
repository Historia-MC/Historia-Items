package dev.boooiil.historia.items.item.component;

import java.util.List;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ToolData;
import dev.boooiil.historia.items.util.NumberUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ToolComponent implements ItemComponent {
    private final List<Float> damageRange;
    private final List<Float> speedRange;
    private final List<Float> knockbackRange;
    private final List<Integer> durabilityRange;

    public ToolComponent(
            List<Float> damageRange,
            List<Float> speedRange,
            List<Float> knockbackRange,
            List<Integer> durabilityRange) {
        this.damageRange = damageRange;
        this.speedRange = speedRange;
        this.knockbackRange = knockbackRange;
        this.durabilityRange = durabilityRange;
    }

    public static ToolComponent fromConfig(ConfigurationSection section) {
        return new ToolComponent(
                section.getFloatList("damage"),
                section.getFloatList("speed"),
                section.getFloatList("knockback"),
                section.getIntegerList("durability"));
    }

    @Override
    public ToolData applyWithDefault() {

        float damage = NumberUtils
                .roundFloat(NumberUtils.random(this.damageRange().get(0), this.damageRange().get(1)), 2);
        float speed = NumberUtils
                .roundFloat(NumberUtils.random(this.speedRange().get(0), this.speedRange().get(1)), 2);
        float knockback = NumberUtils
                .roundFloat(NumberUtils.random(this.knockbackRange().get(0), this.knockbackRange().get(1)), 2);
        int durability = NumberUtils.randomInt(this.durabilityRange().get(0), this.durabilityRange().get(1));

        return new ToolData(damage, speed, knockback, durability);
    }

    @Override
    public ToolData applyWithQuality(float qualityModifier) {
        return applyWithDefault();
    }

    @Override
    public String getKey() {
        return "tool";
    }

    public List<Float> damageRange() {
        return damageRange;
    }

    public List<Float> speedRange() {
        return speedRange;
    }

    public List<Float> knockbackRange() {
        return knockbackRange;
    }

    public List<Integer> durabilityRange() {
        return durabilityRange;
    }

    @Override
    public String toString() {
        return "ToolComponent[" +
                "damageRange=" + damageRange + ", " +
                "speedRange=" + speedRange + ", " +
                "knockbackRange=" + knockbackRange + ", " +
                "durabilityRange=" + durabilityRange + ']';
    }

}
