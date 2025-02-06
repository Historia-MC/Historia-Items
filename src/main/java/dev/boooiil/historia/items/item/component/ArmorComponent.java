package dev.boooiil.historia.items.item.component;

import java.util.List;
import java.util.Objects;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.ArmorData;
import dev.boooiil.historia.items.util.NumberUtils;

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
    public void applyDefaultData(ItemStack item) {

        float defense = NumberUtils
                .roundFloat(NumberUtils.random(this.defenseRange().get(0), this.defenseRange().get(1)), 2);
        int durability = NumberUtils.randomInt(this.durabilityRange().get(0), this.durabilityRange().get(1));

        ArmorData armorData = new ArmorData(defense, durability);
        armorData.apply(item);
    }

    @Override
    public String getKey() {
        return "armor";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (ArmorComponent) obj;
        return Objects.equals(this.defenseRange, that.defenseRange) &&
                Objects.equals(this.durabilityRange, that.durabilityRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defenseRange, durabilityRange);
    }

    @Override
    public String toString() {
        return "ArmorComponent[" +
                "defenseRange=" + defenseRange + ", " +
                "durabilityRange=" + durabilityRange + ']';
    }
}
