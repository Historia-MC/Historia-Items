package dev.boooiil.historia.items.configuration;

import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.items.generic.Ingot;
import dev.boooiil.historia.items.file.FileIO;

public class IngotConfigTest {

    private ServerMock server;
    private PlayerMock player;

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            MockBukkit.load(Main.class);
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
    public void testIngotCompleteness() {

        System.out.println("Testing ingot completeness...");

        YamlConfiguration internalIngotConfig = FileIO.yamlFromSource(
                new java.io.File(Main.plugin().getDataFolder(), "ingots.yml"));

        for (String key : internalIngotConfig.getKeys(false)) {

            System.out.println("Testing ingot: " + key);

            if (key.equals("version")) {
                continue;
            }

            Ingot ingot = Ingot.parseFromString(key);

            assert ingot != null;

            if (ingot.getItemStack() == null) {
                System.out.println("Item stack is null on ingot " + key + " in testIngotCompleteness.");
            }
            assert ingot.getItemStack() != null;

        }
    }
}
