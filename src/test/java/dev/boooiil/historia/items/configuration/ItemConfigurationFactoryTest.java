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
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.ItemConfigurationFactory;
import dev.boooiil.historia.items.configuration.items.ToolConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;
import dev.boooiil.historia.items.file.FileIO;

public class ItemConfigurationFactoryTest {

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
    public void testArmorConfigurationFactory() {

        System.out.println("Testing armor configuration factory...");

        YamlConfiguration internalArmorConfig = FileIO.yamlFromSource(
                new java.io.File(Main.plugin().getDataFolder(), "armor.yml"));

        ItemConfigurationFactory<ArmorConfiguration> armorConfigurationFactory = ItemConfigurationFactory
                .create(ArmorConfiguration.class);

        for (String key : internalArmorConfig.getKeys(false)) {

            System.out.println("Testing armor: " + key);

            if (key.equals("version")) {
                continue;
            }

            assert armorConfigurationFactory.isValid(key);
            assert armorConfigurationFactory.getObject(key) != null;

        }

    }

    @Test
    public void testWeaponConfigurationFactory() {

        System.out.println("Testing weapon configuration factory...");

        YamlConfiguration internalWeaponConfig = FileIO.yamlFromSource(
                new java.io.File(Main.plugin().getDataFolder(), "weapons.yml"));

        ItemConfigurationFactory<WeaponConfiguration> weaponConfigurationFactory = ItemConfigurationFactory
                .create(WeaponConfiguration.class);

        for (String key : internalWeaponConfig.getKeys(false)) {

            System.out.println("Testing weapon: " + key);

            if (key.equals("version")) {
                continue;
            }

            assert weaponConfigurationFactory.isValid(key);
            assert weaponConfigurationFactory.getObject(key) != null;

        }

    }

    @Test
    public void testToolConfigurationFactory() {

        System.out.println("Testing tool configuration factory...");

        YamlConfiguration internalToolConfig = FileIO.yamlFromSource(
                new java.io.File(Main.plugin().getDataFolder(), "tools.yml"));

        ItemConfigurationFactory<ToolConfiguration> toolConfigurationFactory = ItemConfigurationFactory
                .create(ToolConfiguration.class);

        for (String key : internalToolConfig.getKeys(false)) {

            System.out.println("Testing tool: " + key);

            if (key.equals("version")) {
                continue;
            }

            assert toolConfigurationFactory.isValid(key);
            assert toolConfigurationFactory.getObject(key) != null;

        }

    }

    @Test
    public void testCustomItemConfigurationFactory() {

        System.out.println("Testing custom item configuration factory...");

        YamlConfiguration internalCustomItemConfig = FileIO.yamlFromSource(
                new java.io.File(Main.plugin().getDataFolder(), "items.yml"));

        ItemConfigurationFactory<CustomItemConfiguration> customItemConfigurationFactory = ItemConfigurationFactory
                .create(CustomItemConfiguration.class);

        for (String key : internalCustomItemConfig.getKeys(false)) {

            System.out.println("Testing custom item: " + key);

            if (key.equals("version")) {
                continue;
            }

            assert customItemConfigurationFactory.isValid(key);
            assert customItemConfigurationFactory.getObject(key) != null;

        }

    }
}
