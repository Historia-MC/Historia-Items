package dev.boooiil.historia.items.handlers.inventory;

import dev.boooiil.historia.items.configuration.ItemRegistry;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.data.ArmorData;
import dev.boooiil.historia.items.item.data.ToolData;
import dev.boooiil.historia.items.item.data.WeaponData;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;

public class PrepareItemCraftHandlerTest {

    private ServerMock server;
    private Main plugin;

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            plugin = MockBukkit.load(Main.class);
            MockBukkit.load(dev.boooiil.historia.core.HistoriaCore.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void validateItems() {
        for (String registeredItem : ItemRegistry.allKeys()) {

            HistoriaItem historiaItem = ItemRegistry.get(registeredItem);

            Logging.debugToConsole("Testing item:", historiaItem.getConfigurationId());

            ItemStack item = historiaItem.createItemStack();
            Logging.debugToConsole(item.getItemMeta().getPersistentDataContainer().getKeys() + "");

            assert (item.hasItemMeta());

            ItemMeta meta = item.getItemMeta();

            for (String key : historiaItem.getComponentHolder().keySet()) {

                switch (key) {
                    case "tool":
                        ToolData td = ToolData.fromStack(item);

                        AttributeModifier damageAttr = meta.getAttributeModifiers(Attribute.ATTACK_DAMAGE).iterator()
                                .next();

                        AttributeModifier speedAttr = meta.getAttributeModifiers(Attribute.ATTACK_SPEED).iterator()
                                .next();

                        AttributeModifier knockbackAttr = meta.getAttributeModifiers(Attribute.ATTACK_KNOCKBACK)
                                .iterator().next();

                        Damageable toolDamageable = (Damageable) item.getItemMeta();
                        float damage = NumberUtils.roundFloat((float) damageAttr.getAmount(), 2);
                        float speed = NumberUtils.roundFloat((float) speedAttr.getAmount(), 2);
                        float knockback = NumberUtils.roundFloat((float) knockbackAttr.getAmount(), 2);

                        Logging.debugToConsole("Damage", td.attackDamage() + " : " + damage);
                        Logging.debugToConsole("Speed", td.attackSpeed() + " : " + speed);
                        Logging.debugToConsole("Knockback", td.knockback() + " : " + knockback);
                        Logging.debugToConsole("Durability",
                                td.maxDurability() + " : " + toolDamageable.getMaxDamage());

                        assert (td.attackDamage() == damage);
                        assert (td.attackSpeed() == speed);
                        assert (td.knockback() == knockback);
                        assert (td.maxDurability() == toolDamageable.getMaxDamage());
                        break;

                    case "weapon":
                        WeaponData wd = WeaponData.fromStack(item);

                        AttributeModifier sweepingAttr = meta.getAttributeModifiers(Attribute.SWEEPING_DAMAGE_RATIO)
                                .iterator()
                                .next();

                        float sweeping = NumberUtils.roundFloat((float) sweepingAttr.getAmount(), 2);

                        Logging.debugToConsole("Damage", wd.sweeping() + " : " + sweeping);
                        assert (wd.sweeping() == sweeping);

                        break;

                    case "armor":
                        ArmorData ad = ArmorData.fromStack(item);

                        AttributeModifier defenseAttr = meta.getAttributeModifiers(Attribute.ARMOR).iterator()
                                .next();

                        Damageable armorDamageable = (Damageable) item.getItemMeta();
                        float defense = NumberUtils.roundFloat((float) defenseAttr.getAmount(), 2);

                        Logging.debugToConsole("Defense", ad.getDefense() + " : " + defense);
                        Logging.debugToConsole("Durability",
                                ad.maxDurability() + " : " + armorDamageable.getMaxDamage());

                        assert (ad.getDefense() == defense);
                        assert (ad.maxDurability() == armorDamageable.getMaxDamage());
                }
            }
        }
    }

}
