package dev.boooiil.historia.items.handlers.executor;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.sk89q.worldguard.bukkit.event.block.PlaceBlockEvent;

import dev.boooiil.historia.items.HistoriaItems;
import dev.boooiil.historia.items.item.HistoriaItemData;
import dev.boooiil.historia.items.item.data.ExecutorData;
import dev.boooiil.historia.items.item.types.Triggers;

public class ExecutorTriggerHandler {

    private static NamespacedKey EXECUTOR_KEY = HistoriaItems.getNamespacedKey("executor");

    public static void executeAction(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        ItemStack mainItemStack = player.getInventory().getItemInMainHand();
        ItemStack offItemStack = player.getInventory().getItemInOffHand();

        if (event.isSneaking()) {
            execute(player, mainItemStack, Triggers.CROUCH);
            execute(player, offItemStack, Triggers.CROUCH);
        } else {
            execute(player, mainItemStack, Triggers.UNCROUCH);
            execute(player, offItemStack, Triggers.UNCROUCH);
        }
    }

    public static void executeAction(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof HumanEntity)) {
            return;
        }

        HumanEntity humanEntity = (HumanEntity) event.getEntity().getShooter();

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.THROW);
        execute(humanEntity, offItemStack, Triggers.THROW);
    }

    public static void executeAction(EntityToggleSwimEvent event) {
        if (!(event.getEntity() instanceof HumanEntity)) {
            return;
        }

        HumanEntity humanEntity = (HumanEntity) event.getEntity();

        if (!humanEntity.isSwimming()) {
            return;
        }

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.SWIM);
        execute(humanEntity, offItemStack, Triggers.SWIM);
    }

    public static void executeAction(PlayerToggleSprintEvent event) {
        if (event.getPlayer().isSprinting()) {
            Player player = event.getPlayer();

            ItemStack mainItemStack = player.getInventory().getItemInMainHand();
            ItemStack offItemStack = player.getInventory().getItemInOffHand();

            execute(player, mainItemStack, Triggers.SPRINT);
            execute(player, offItemStack, Triggers.SPRINT);
        }
    }

    public static void executeAction(PlayerJumpEvent event) {

        Player player = event.getPlayer();

        ItemStack mainItemStack = player.getInventory().getItemInMainHand();
        ItemStack offItemStack = player.getInventory().getItemInOffHand();

        execute(player, mainItemStack, Triggers.JUMP);
        execute(player, offItemStack, Triggers.JUMP);
    }

    public static void executeAction(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        ItemStack mainItemStack = player.getInventory().getItemInMainHand();
        ItemStack offItemStack = player.getInventory().getItemInOffHand();

        // if bottle
        if (event.getItem().getType() == Material.GLASS_BOTTLE) {
            execute(player, mainItemStack, Triggers.DRINK);
            execute(player, offItemStack, Triggers.DRINK);
        }

        // else some other consumable
        else {
            execute(player, mainItemStack, Triggers.EAT);
            execute(player, offItemStack, Triggers.EAT);
        }

    }

    public static void executeAction(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof HumanEntity)) {
            return;
        }

        HumanEntity humanEntity = (HumanEntity) event.getEntity();

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.DROP);
        execute(humanEntity, offItemStack, Triggers.DROP);
    }

    public static void executeAction(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof HumanEntity)) {
            return;
        }

        HumanEntity humanEntity = (HumanEntity) event.getEntity();

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.PICKUP);
        execute(humanEntity, offItemStack, Triggers.PICKUP);

    }

    public static void executeAction(PlaceBlockEvent event) {

        Player player = event.getCause().getFirstPlayer();

        if (player == null) {
            return;
        }

        ItemStack mainItemStack = player.getInventory().getItemInMainHand();
        ItemStack offItemStack = player.getInventory().getItemInOffHand();

        execute(player, mainItemStack, Triggers.PLACE);
        execute(player, offItemStack, Triggers.PLACE);

    }

    public static void executeAction(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        ItemStack mainItemStack = player.getInventory().getItemInMainHand();
        ItemStack offItemStack = player.getInventory().getItemInOffHand();

        execute(player, mainItemStack, Triggers.SWAP_TO_MAINHAND);
        execute(player, offItemStack, Triggers.SWAP_TO_OFFHAND);
    }

    public static void executeAction(InventoryOpenEvent event) {

        HumanEntity humanEntity = (HumanEntity) event.getPlayer();

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.OPEN_INVENTORY);
        execute(humanEntity, offItemStack, Triggers.OPEN_INVENTORY);
    }

    public static void executeAction(InventoryCloseEvent event) {

        HumanEntity humanEntity = (HumanEntity) event.getPlayer();

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.CLOSE_INVENTORY);
        execute(humanEntity, offItemStack, Triggers.CLOSE_INVENTORY);
    }

    public static void executeAction(EntityDamageByEntityEvent event) {

        boolean isAttackerHuman = event.getDamager() instanceof HumanEntity;
        boolean isVictimLiving = event.getEntity() instanceof LivingEntity;
        boolean isBothLiving = isAttackerHuman && isVictimLiving;
        boolean isAttackerLivingVictimNot = isAttackerHuman && !isVictimLiving;

        if (!isBothLiving || isAttackerLivingVictimNot) {
            return;
        }

        HumanEntity damager = (HumanEntity) event.getDamager();

        ItemStack mainItemStack = damager.getInventory().getItemInMainHand();
        ItemStack offItemStack = damager.getInventory().getItemInOffHand();

        execute(damager, mainItemStack, Triggers.DAMAGE_ENTITY);
        execute(damager, offItemStack, Triggers.DAMAGE_ENTITY);

    }

    public static void executeAction(EntityInteractEvent event) {

        if (!(event.getEntity() instanceof HumanEntity)) {
            return;
        }

        HumanEntity humanEntity = (HumanEntity) event.getEntity();

        ItemStack mainItemStack = humanEntity.getInventory().getItemInMainHand();
        ItemStack offItemStack = humanEntity.getInventory().getItemInOffHand();

        execute(humanEntity, mainItemStack, Triggers.INTERACT_ENTITY);
        execute(humanEntity, offItemStack, Triggers.INTERACT_ENTITY);

    }

    public static void executeAction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack mainItemStack = player.getInventory().getItemInMainHand();
        ItemStack offItemStack = player.getInventory().getItemInOffHand();

        // if block is not air
        if (event.getClickedBlock() != null) {
            if (player.isSneaking()) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    execute(player, mainItemStack, Triggers.SNEAK_RIGHT_CLICK_BLOCK);
                    execute(player, offItemStack, Triggers.SNEAK_RIGHT_CLICK_BLOCK);
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    execute(player, mainItemStack, Triggers.SNEAK_LEFT_CLICK_BLOCK);
                    execute(player, offItemStack, Triggers.SNEAK_LEFT_CLICK_BLOCK);
                }
            } else {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    execute(player, mainItemStack, Triggers.RIGHT_CLICK_BLOCK);
                    execute(player, offItemStack, Triggers.RIGHT_CLICK_BLOCK);
                } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    execute(player, mainItemStack, Triggers.LEFT_CLICK_BLOCK);
                    execute(player, offItemStack, Triggers.LEFT_CLICK_BLOCK);
                }
            }

        }

        // if block is air
        else {
            if (player.isSneaking()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    execute(player, mainItemStack, Triggers.SNEAK_RIGHT_CLICK);
                    execute(player, offItemStack, Triggers.SNEAK_RIGHT_CLICK);
                } else if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    execute(player, mainItemStack, Triggers.SNEAK_LEFT_CLICK);
                    execute(player, offItemStack, Triggers.SNEAK_LEFT_CLICK);
                }
            } else {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    execute(player, mainItemStack, Triggers.RIGHT_CLICK);
                    execute(player, offItemStack, Triggers.RIGHT_CLICK);
                } else if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    execute(player, mainItemStack, Triggers.LEFT_CLICK);
                    execute(player, offItemStack, Triggers.LEFT_CLICK);
                }
            }
        }

    }

    public static void execute(HumanEntity humanEntity, ItemStack item, Triggers trigger) {

        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) {
            return;
        }

        HistoriaItemData historiaItemData = HistoriaItemData.fromStack(item);

        boolean hasExecutor = historiaItemData.hasData(EXECUTOR_KEY);

        if (!hasExecutor) {
            return;
        }

        else {
            ExecutorData executorData = historiaItemData.getData(EXECUTOR_KEY, ExecutorData.DATA_TYPE);

            executorData.execute(humanEntity, item, trigger);
        }
    }

}
