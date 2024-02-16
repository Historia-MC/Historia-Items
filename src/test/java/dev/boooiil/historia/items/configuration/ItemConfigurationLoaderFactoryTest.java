package dev.boooiil.historia.items.configuration;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.ArmorConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.CustomItemConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.ItemConfigurationLoaderFactory;
import dev.boooiil.historia.items.configuration.items.ToolConfiguration;
import dev.boooiil.historia.items.configuration.items.ToolConfigurationLoader;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfigurationLoader;
import dev.boooiil.historia.items.file.FileKeys;

public class ItemConfigurationLoaderFactoryTest {
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
    public void testArmorConfigurationLoaderFactory() {
        System.out.println("Testing armor configuration loader...");

        ArmorConfigurationLoader configurationLoader = ItemConfigurationLoaderFactory
                .getArmorConfigurationLoader();

        assert configurationLoader != null;

        configurationLoader.loadConfiguration(FileKeys.ARMOR);

        assert configurationLoader.getConfiguration() != null;

        assert configurationLoader.map.size() == ConfigurationProvider.getArmorConfigurationLoader().map.size();

        ArmorConfiguration configuration = configurationLoader.getObject("Light_Iron_Helmet");

        assert configuration != null;
        assert configuration.getLore() != null;
        assert configuration.getDisplayName() != null;

        System.out.println("Armor configuration loader test passed.");
    }

    @Test
    public void testWeaponConfigurationLoaderFactory() {
        System.out.println("Testing item configuration loader factory...");

        WeaponConfigurationLoader configurationLoader = ItemConfigurationLoaderFactory
                .getWeaponConfigurationLoader();

        assert configurationLoader != null;

        configurationLoader.loadConfiguration(FileKeys.WEAPONS);

        assert configurationLoader.getConfiguration() != null;

        assert configurationLoader.map.size() == ConfigurationProvider.getWeaponConfigurationLoader().map.size();

        WeaponConfiguration configuration = configurationLoader.getObject("Light_Iron_Sword");

        assert configuration != null;
        assert configuration.getLore() != null;
        assert configuration.getDisplayName() != null;

        System.out.println("Item configuration loader factory test passed.");
    }

    @Test
    public void testToolConfigurationLoaderFactory() {
        System.out.println("Testing item configuration loader factory...");

        ToolConfigurationLoader configurationLoader = ItemConfigurationLoaderFactory
                .getToolConfigurationLoader();

        assert configurationLoader != null;

        configurationLoader.loadConfiguration(FileKeys.TOOLS);

        assert configurationLoader.getConfiguration() != null;

        assert configurationLoader.map.size() == ConfigurationProvider.getToolConfigurationLoader().map.size();

        ToolConfiguration configuration = configurationLoader.getObject("Light_Iron_Pickaxe");

        assert configuration != null;
        assert configuration.getLore() != null;
        assert configuration.getDisplayName() != null;

        System.out.println("Item configuration loader factory test passed.");
    }

    @Test
    public void testCustomItemConfigurationLoaderFactory() {
        System.out.println("Testing custom item configuration loader factory...");

        CustomItemConfigurationLoader configurationLoader = ItemConfigurationLoaderFactory
                .getCustomItemConfigurationLoader();

        assert configurationLoader != null;

        configurationLoader.loadConfiguration(FileKeys.CUSTOM_ITEMS);

        assert configurationLoader.getConfiguration() != null;

        assert configurationLoader.map.size() == ConfigurationProvider.getCustomItemConfigurationLoader().map.size();

        CustomItemConfiguration configuration = configurationLoader.getObject("COAL_COKE");

        assert configuration != null;
        assert configuration.getLore() != null;
        assert configuration.getDisplayName() != null;

        System.out.println("Custom item configuration loader factory test passed.");
    }

    @Test
    public void testGenericItemConfigurationLoaderFactory() {

        WeaponConfigurationLoader configurationLoader = ItemConfigurationLoaderFactory
                .getConfigurationLoader(FileKeys.WEAPONS, WeaponConfigurationLoader::new);

        WeaponConfiguration configuration = configurationLoader.getObject("Light_Iron_Sword");

        assert configuration != null;
        assert configuration.getLore() != null;
        assert configuration.getDisplayName() != null;

    }
}
