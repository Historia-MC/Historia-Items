package dev.boooiil.historia.items.handlers.inventory;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.ItemConfigurationRegistry;
import dev.boooiil.historia.items.util.Logging;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

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
            MockBukkit.load(dev.boooiil.historia.core.Main.class);
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
    public void doMainTest() {
        PlayerMock player = server.addPlayer();
        Logging.debugToConsole("Creating player", player.getName());

        EntityMock entity = new ZombieMock(server, UUID.randomUUID());
        Logging.debugToConsole("Creating entity", entity.getType().name());

        // player.attack(entity);

        player.getInventory().addItem(ItemConfigurationRegistry.get("Light_Tin_Sword").createItemStack());

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                System.out.println("Item found: " + item.getType());
            }
        }

        System.out.println(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());

        // if (!player.getInventory().getItemInMainHand().hasItemMeta()
        // || player.getInventory().getItemInMainHand().getItemMeta().lore() == null
        // || player.getInventory().getItemInMainHand().getItemMeta().lore().isEmpty())
        // {

        // return;
        // }

        for (Component component : player.getInventory().getItemInMainHand().getItemMeta().lore()) {
            TextComponent textComponent = (TextComponent) component;

            System.out.println(textComponent.content());
        }
        // do main file tests
    }

}
