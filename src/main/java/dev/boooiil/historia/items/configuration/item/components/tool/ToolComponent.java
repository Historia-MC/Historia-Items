package dev.boooiil.historia.items.configuration.item.components.tool;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.util.NumberUtils;

public class ToolComponent implements IItemComponent {

    private int priority;
    private List<Float> damage;
    private List<Float> speed;
    private List<Float> knockback;
    private List<Integer> durability;

    ToolComponent() {
    }

    @Override
    public void processConfiguration(ConfigurationSection section) {

        this.priority = section.getInt("priority");
        this.damage = section.getFloatList("damage");
        this.speed = section.getFloatList("speed");
        this.knockback = section.getFloatList("knockback");
        this.durability = section.getIntegerList("durability");

    }

    @Override
    public void processConfiguration(YamlConfiguration configuration) {

        processConfiguration(configuration.getConfigurationSection("tool"));
    }

    @Override
    public ItemStack applyToItemStack(ItemStack item, float qualityModifier) {

        // TODO: apply modified quality and other information to stack

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT,
                NumberUtils.random(damage.get(0), damage.get(1)));
        container.set(Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT,
                NumberUtils.random(speed.get(0), speed.get(1)));
        container.set(Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT,
                NumberUtils.random(knockback.get(0), knockback.get(1)));
        container.set(Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER,
                NumberUtils.randomInt(durability.get(0), durability.get(1)));

        item.setItemMeta(meta);

        return item;
    }

}
