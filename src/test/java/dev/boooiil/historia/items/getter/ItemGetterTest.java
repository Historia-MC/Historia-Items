package dev.boooiil.historia.items.getter;

import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.file.FileKeys;
import dev.boooiil.historia.items.items.getter.ItemGetter;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.items.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.items.CustomItemConfiguration;
import dev.boooiil.historia.items.configuration.items.ToolConfiguration;
import dev.boooiil.historia.items.configuration.items.WeaponConfiguration;

public class ItemGetterTest {

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
    public void testArmorGetterCompleteness() {
        System.out.println("Testing weapon getter completeness...");

        YamlConfiguration configuration = FileIO.get(FileKeys.ARMOR);

        for (String key : configuration.getKeys(false)) {
            if (key.equals("version"))
                continue;
            System.out.println("Testing key: " + key);
            ArmorConfiguration item = ItemGetter.getArmor(key);

            System.out.println("Item: " + item.getItemStack().getItemMeta().displayName());

            assert item != null;
        }

    }

    @Test
    public void testWeaponGetterCompleteness() {
        System.out.println("Testing weapon getter completeness...");

        YamlConfiguration configuration = FileIO.get(FileKeys.WEAPONS);

        for (String key : configuration.getKeys(false)) {
            if (key.equals("version"))
                continue;
            System.out.println("Testing key: " + key);
            WeaponConfiguration item = ItemGetter.getWeapon(key);

            System.out.println("Item: " + item.getItemStack().getItemMeta().displayName());

            assert item != null;
        }

    }

    @Test
    public void testCustomItemCompleteness() {
        System.out.println("Testing weapon getter completeness...");

        YamlConfiguration configuration = FileIO.get(FileKeys.CUSTOM_ITEMS);

        for (String key : configuration.getKeys(false)) {
            if (key.equals("version"))
                continue;
            System.out.println("Testing key: " + key);
            CustomItemConfiguration item = ItemGetter.getCustomItem(key);

            System.out.println("Item: " + item.getItemStack().getItemMeta().displayName());

            assert item != null;
        }

    }

    @Test
    public void testToolCompleteness() {
        System.out.println("Testing weapon getter completeness...");

        YamlConfiguration configuration = FileIO.get(FileKeys.TOOLS);

        for (String key : configuration.getKeys(false)) {
            if (key.equals("version"))
                continue;
            System.out.println("Testing key: " + key);
            ToolConfiguration item = ItemGetter.getTool(key);

            System.out.println("Item: " + item.getItemStack().getItemMeta().displayName());

            assert item != null;
        }

    }

}
