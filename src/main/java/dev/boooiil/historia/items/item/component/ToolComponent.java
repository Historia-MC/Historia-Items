package dev.boooiil.historia.items.item.component;

import java.util.List;
import java.util.Objects;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ToolData;
import dev.boooiil.historia.items.util.NumberUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
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
            List<Integer> durabilityRange
    ) {
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
                section.getIntegerList("durability")
        );
    }

    public List<Float> getDamageRange() {
        return damageRange;
    }

    public List<Float> getSpeedRange() {
        return speedRange;
    }

    public List<Float> getKnockbackRange() {
        return knockbackRange;
    }

    public List<Integer> getDurabilityRange() {
        return durabilityRange;
    }

    @Override
    public void applyDefaultData(ItemStack item) {

        float damage = NumberUtils
                .roundFloat(NumberUtils.random(this.getDamageRange().get(0), this.getDamageRange().get(1)), 2);
        float speed = NumberUtils
                .roundFloat(NumberUtils.random(this.getSpeedRange().get(0), this.getSpeedRange().get(1)), 2);
        float knockback = NumberUtils
                .roundFloat(NumberUtils.random(this.getKnockbackRange().get(0), this.getKnockbackRange().get(1)), 2);
        int durability = NumberUtils.randomInt(this.getDurabilityRange().get(0), this.getDurabilityRange().get(1));

        ToolData toolData = new ToolData(damage, speed, knockback, durability);
        toolData.apply(item);
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ToolComponent) obj;
        return Objects.equals(this.damageRange, that.damageRange) &&
                Objects.equals(this.speedRange, that.speedRange) &&
                Objects.equals(this.knockbackRange, that.knockbackRange) &&
                Objects.equals(this.durabilityRange, that.durabilityRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(damageRange, speedRange, knockbackRange, durabilityRange);
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
