package dev.boooiil.historia.items.handlers.inventory;

import dev.boooiil.historia.items.configuration.ItemRegistry;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.component.ExecutorComponent;
import dev.boooiil.historia.items.item.data.ArmorData;
import dev.boooiil.historia.items.item.data.ExecutorData;
import dev.boooiil.historia.items.item.data.ToolData;
import dev.boooiil.historia.items.item.data.WeaponData;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;

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
import org.mockbukkit.mockbukkit.entity.PlayerMock;

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

            Logging.debugToConsole(historiaItem.toString());

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

                        Logging.debugToConsole("Data:", td.toString());

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

                        Logging.debugToConsole("Data:", wd.toString());
                        assert (wd.sweeping() == sweeping);

                        break;

                    case "armor":
                        ArmorData ad = ArmorData.fromStack(item);

                        AttributeModifier defenseAttr = meta.getAttributeModifiers(Attribute.ARMOR).iterator()
                                .next();

                        Damageable armorDamageable = (Damageable) item.getItemMeta();
                        float defense = NumberUtils.roundFloat((float) defenseAttr.getAmount(), 2);

                        Logging.debugToConsole("Data:", ad.toString());

                        assert (ad.getDefense() == defense);
                        assert (ad.maxDurability() == armorDamageable.getMaxDamage());
                        break;

                    case "executor":
                        ExecutorComponent ec = (ExecutorComponent) historiaItem.getComponentHolder().get(key);
                        ExecutorData ed = ExecutorData.fromStack(item);
                        PlayerMock player = server.addPlayer();

                        for (Triggers trigger : ec.executables().keySet()) {

                            ItemExecutable executable = ec.executables().get(trigger);

                            Logging.debugToConsole("Executable:", executable.toString());

                            assert (trigger != Triggers.UNKNOWN);
                            assert (ec.executables().containsKey(trigger));

                            assert (ed.executables().get(trigger).uses() == executable.uses());

                            assert (ed.executables().get(trigger).cooldown() == executable.cooldown());

                            assert (ed.executables().get(trigger).commands().equals(executable.commands()));

                            ed.execute(player, item, trigger);

                            if (ec.executables().size() > ed.executables().size()) {
                                Logging.debugToConsole("Executables size changed: " + ec.executables().size() + " -> "
                                        + ed.executables().size(), "on trigger", trigger.getLowercase());
                            } else {
                                for (Triggers executedTrigger : ec.executables().keySet()) {
                                    assert (ed.executables().get(executedTrigger).uses() < ec.executables().get(trigger)
                                            .uses());
                                }
                            }

                        }

                        break;

                }
            }
        }
    }

}
