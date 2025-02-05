package dev.boooiil.historia.items.configuration.item.components.tool;

import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.item.components.IItemComponent;
import dev.boooiil.historia.items.util.PDCUtils;

public class ToolComponent implements IItemComponent {

    private List<Float> damageRange;
    private List<Float> speedRange;
    private List<Float> knockbackRange;
    private List<Integer> durabilityRange;

    public ToolComponent() {
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

        ItemMeta meta = item.getItemMeta();

        float damage = getDamageRange().get(0);
        float speed = getSpeedRange().get(0);
        float knockback = getKnockbackRange().get(0);
        int durability = getDurabilityRange().get(0);

        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-damage"), PersistentDataType.FLOAT,
                damage);
        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-speed"), PersistentDataType.FLOAT,
                speed);
        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-knockback"), PersistentDataType.FLOAT,
                knockback);
        PDCUtils.setInContainer(item, Main.getNamespacedKey("tool-durability"), PersistentDataType.INTEGER,
                durability);

        AttributeModifier damageAttr = new AttributeModifier(Main.getNamespacedKey("tool-damage"), damage,
                Operation.ADD_NUMBER);
        AttributeModifier speedAttr = new AttributeModifier(Main.getNamespacedKey("tool-speed"), speed,
                Operation.ADD_NUMBER);
        AttributeModifier knockbackAttr = new AttributeModifier(Main.getNamespacedKey("tool-knockback"), knockback,
                Operation.ADD_NUMBER);

        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageAttr);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, speedAttr);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, knockbackAttr);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        Damageable damageable = (Damageable) item.getItemMeta();
        damageable.setMaxDamage(durability);

        item.setItemMeta(damageable);

    }
}
