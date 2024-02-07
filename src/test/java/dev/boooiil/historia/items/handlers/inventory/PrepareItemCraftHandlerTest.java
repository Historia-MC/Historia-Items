package dev.boooiil.historia.items.handlers.inventory;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.items.Main;

public class PrepareItemCraftHandlerTest {

    private ServerMock server;
    private Main plugin;
    private PlayerMock player;

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
        System.out.println("Creating player...");
        player = new PlayerMock(server, "MockUser", UUID.fromString("00000000-0000-0000-0000-000000000001"));
        System.out.println("Player created: " + player.getName());

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

        ItemStack[] items = {
                new ItemStack(Material.AIR),
                new ItemStack(Material.IRON_INGOT),
                new ItemStack(Material.AIR),

                new ItemStack(Material.AIR),
                new ItemStack(Material.IRON_INGOT),
                new ItemStack(Material.AIR),

                new ItemStack(Material.AIR),
                new ItemStack(Material.STICK),
                new ItemStack(Material.AIR)
        };

        System.out.println("Crafting item...");
        ItemStack item = server.craftItem(items, player.getWorld(), player);

        System.out.println("Item: " + item.getType().toString());
        // do main file tests
    }

}
