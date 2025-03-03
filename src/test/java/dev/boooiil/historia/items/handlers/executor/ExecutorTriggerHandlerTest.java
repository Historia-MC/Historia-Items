package dev.boooiil.historia.items.handlers.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.HistoriaItem;
import dev.boooiil.historia.items.item.HistoriaItemData;
import dev.boooiil.historia.items.item.component.ExecutorComponent;
import dev.boooiil.historia.items.item.data.ExecutorData;
import dev.boooiil.historia.items.item.executor.ItemExecutable;
import dev.boooiil.historia.items.item.types.Triggers;
import dev.boooiil.historia.items.util.HILogger;

public class ExecutorTriggerHandlerTest {

    static ServerMock server;
    static HistoriaItems plugin;
    static Player player;
    final NamespacedKey EXECUTOR_KEY = HistoriaItems.getNamespacedKey("executor");

    HistoriaItem historiaItem = HistoriaItems.ITEM_REGISTRY.get(HistoriaItems.getNamespacedKey("Light_Bronze_Boots"));
    ExecutorComponent component = new ExecutorComponent(new HashMap<>());

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            plugin = MockBukkit.load(HistoriaItems.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player = server.addPlayer();
        System.out.println("Finished setup.");

    }

    @BeforeEach
    public void clearExecutables() {
        component.executables().clear();
        historiaItem.getComponentHolder().put(EXECUTOR_KEY, component);

        assertTrue(historiaItem.getComponentHolder().containsKey(EXECUTOR_KEY));
        assertTrue(((ExecutorComponent) historiaItem.getComponentHolder().get(EXECUTOR_KEY)).executables().isEmpty());
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test // EntityDamageByEntityEvent
    void testExecuteAction() {

        component.executables().put(Triggers.DAMAGE_ENTITY, new ItemExecutable(List.of(""), 200, 2, false, false));

        ItemStack mhi = historiaItem.createItemStack();
        ItemStack ohi = historiaItem.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(player, player, DamageCause.ENTITY_ATTACK, 1.0);

        ExecutorTriggerHandler.executeAction(event);

        System.out
                .println("raw: " + player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                        .get(HistoriaItems.getNamespacedKey("executor"), ExecutorData.DATA_TYPE)
                        .toString());

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(Triggers.DAMAGE_ENTITY).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(Triggers.DAMAGE_ENTITY).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(Triggers.DAMAGE_ENTITY).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(Triggers.DAMAGE_ENTITY).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(Triggers.DAMAGE_ENTITY).uses());
        assertEquals(1, ohd.executables().get(Triggers.DAMAGE_ENTITY).uses());

    }

    @Test // EntityDropItemEvent
    void testExecuteAction2() {

        component.executables().put(Triggers.DROP, new ItemExecutable(List.of(""), 200, 2, false, false));

        ItemStack mhi = historiaItem.createItemStack();
        ItemStack ohi = historiaItem.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityDropItemEvent event = new EntityDropItemEvent(player,
                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ACACIA_BOAT)));

        ExecutorTriggerHandler.executeAction(event);

        System.out
                .println("raw: " + player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                        .get(HistoriaItems.getNamespacedKey("executor"), ExecutorData.DATA_TYPE)
                        .toString());

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(Triggers.DROP).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(Triggers.DROP).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(Triggers.DROP).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(Triggers.DROP).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(Triggers.DROP).uses());
        assertEquals(1, ohd.executables().get(Triggers.DROP).uses());
    }

    @Test // EntityInteractEvent
    void testExecuteAction3() {

        component.executables().put(Triggers.INTERACT_ENTITY, new ItemExecutable(List.of(""), 200, 2, false, false));

        ItemStack mhi = historiaItem.createItemStack();
        ItemStack ohi = historiaItem.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityInteractEvent event = new EntityInteractEvent(player, player.getWorld().getBlockAt(0, 0, 0));

        ExecutorTriggerHandler.executeAction(event);

        System.out
                .println("raw: " + player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                        .get(HistoriaItems.getNamespacedKey("executor"), ExecutorData.DATA_TYPE)
                        .toString());

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(Triggers.INTERACT_ENTITY).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(Triggers.INTERACT_ENTITY).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(Triggers.INTERACT_ENTITY).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(Triggers.INTERACT_ENTITY).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(Triggers.INTERACT_ENTITY).uses());
        assertEquals(1, ohd.executables().get(Triggers.INTERACT_ENTITY).uses());
    }

    @Test // EntityPickupItemEvent
    void testExecuteAction4() {
        component.executables().put(Triggers.PICKUP, new ItemExecutable(List.of(""), 200, 2, false, false));

        ItemStack mhi = historiaItem.createItemStack();
        ItemStack ohi = historiaItem.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityPickupItemEvent event = new EntityPickupItemEvent(player,
                (Item) player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.ACACIA_BOAT)), 1);

        ExecutorTriggerHandler.executeAction(event);

        System.out
                .println("raw: " + player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                        .get(HistoriaItems.getNamespacedKey("executor"), ExecutorData.DATA_TYPE)
                        .toString());

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(Triggers.INTERACT_ENTITY).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(Triggers.PICKUP).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(Triggers.PICKUP).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(Triggers.PICKUP).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(Triggers.PICKUP).uses());
        assertEquals(1, ohd.executables().get(Triggers.PICKUP).uses());
    }

    @Test // EntityToggleSwimEvent
    void testExecuteAction5() {
        component.executables().put(Triggers.SWIM, new ItemExecutable(List.of(""), 200, 2, false, false));

        ItemStack mhi = historiaItem.createItemStack();
        ItemStack ohi = historiaItem.createItemStack();

        player.getInventory().setItemInMainHand(mhi);
        player.getInventory().setItemInOffHand(ohi);

        EntityToggleSwimEvent event = new EntityToggleSwimEvent(player, true);

        ExecutorTriggerHandler.executeAction(event);

        System.out
                .println("raw: " + player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                        .get(HistoriaItems.getNamespacedKey("executor"), ExecutorData.DATA_TYPE)
                        .toString());

        HistoriaItemData mainHand = HistoriaItemData.fromStack(player.getInventory().getItemInMainHand());
        HistoriaItemData offHand = HistoriaItemData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhdr = ExecutorData.fromStack(player.getInventory().getItemInMainHand());
        ExecutorData ohdr = ExecutorData.fromStack(player.getInventory().getItemInOffHand());

        ExecutorData mhd = mainHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);
        ExecutorData ohd = offHand.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

        System.out.println("MHDR Uses: " + mhdr.executables().get(Triggers.SWIM).uses());
        System.out.println("OHDR Uses: " + ohdr.executables().get(Triggers.SWIM).uses());

        System.out.println("MHD Uses: " + mhd.executables().get(Triggers.SWIM).uses());
        System.out.println("OHD Uses: " + ohd.executables().get(Triggers.SWIM).uses());

        // System.out.println(mhd.toString());

        assertEquals(mhdr.toJSON(), mhd.toJSON());
        assertEquals(ohdr.toJSON(), ohd.toJSON());

        assertEquals(1, mhd.executables().get(Triggers.SWIM).uses());
        assertEquals(1, ohd.executables().get(Triggers.SWIM).uses());
    }

    @Test // ProjectileLaunchEvent
    void testExecuteAction6() {

    }

    @Test // InventoryCloseEvent
    void testExecuteAction7() {

    }

    @Test // InventoryOpenEvent
    void testExecuteAction8() {

    }

    @Test // PlayerInteractEvent
    void testExecuteAction9() {

    }

    @Test // PlayerItemConsumeEvent
    void testExecuteAction10() {

    }

    @Test // PlayerSwapHandItemsEvent
    void testExecuteAction11() {

    }

    @Test // PlayerToggleSneakEvent
    void testExecuteAction12() {

    }

    @Test // PlayerToggleSprintEvent
    void testExecuteAction13() {

    }

    @Test // PlayerJumpEvent
    void testExecuteAction14() {

    }

    @Test // PlaceBlockEvent
    void testExecuteAction15() {

    }
}
