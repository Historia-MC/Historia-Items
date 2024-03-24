package dev.boooiil.historia.items.configuration;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.items.Main;

public class ItemConfigurationRegistryTest {

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

}
