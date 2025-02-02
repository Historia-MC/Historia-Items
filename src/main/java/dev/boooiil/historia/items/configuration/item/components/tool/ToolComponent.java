package dev.boooiil.historia.items.configuration.item.components.tool;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.core.Main;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.util.PDCUtils;

public class ToolComponent implements IItemComponent {

    private List<Float> damageRange;
    private List<Float> speedRange;
    private List<Float> knockbackRange;
    private List<Integer> durabilityRange;

    ToolComponent() {
    }

    @Override
    public void processConfiguration(ConfigurationSection section) {

        // this.priority = section.getInt("priority");
        this.damageRange = section.getFloatList("damage");
        this.speedRange = section.getFloatList("speed");
        this.knockbackRange = section.getFloatList("knockback");
        this.durabilityRange = section.getIntegerList("durability");

    }

    @Override
    public void processConfiguration(YamlConfiguration configuration) {

        processConfiguration(configuration.getConfigurationSection("tool"));
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
    public void setDefaultsToMeta(ItemStack item) {

        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT,
                getDamageRange().get(0));
        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT,
                getSpeedRange().get(0));
        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT,
                getKnockbackRange().get(0));
        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER,
                getDurabilityRange().get(0));

    }
}
