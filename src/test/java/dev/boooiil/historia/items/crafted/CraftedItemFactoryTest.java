package dev.boooiil.historia.items.crafted;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
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
import dev.boooiil.historia.items.crafted.armor.Armor;
import dev.boooiil.historia.items.crafted.custom.Custom;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.crafted.tool.Tool;
import dev.boooiil.historia.items.crafted.weapon.Weapon;

public class CraftedItemFactoryTest {

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
    public void testWeaponCreationFromItemStack() {

        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.WEAPON.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, "Test Weapon");

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, 10.0);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, 1.0);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, 1.0);
        container.set(Main.getNamespacedKey("item-sweeping"), PersistentDataType.DOUBLE, 1.0);

        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, 100);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, 10);

        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, "light");

        item.setItemMeta(meta);

        Weapon weapon = CraftedItemFactory.createWeapon(item);

        assert (weapon.isValid());
        assert (weapon.getDisplayName().equals("Test Weapon"));
        assert (weapon.getDamage() == 10.0);
        assert (weapon.getSpeed() == 1.0);
        assert (weapon.getKnockback() == 1.0);
        assert (weapon.getSweeping() == 1.0);
        assert (weapon.getDurability() == 100);
        assert (weapon.getWeightValue() == 10);
        assert (weapon.getWeight().equals(Weight.LIGHT));

    }

    @Test
    public void testArmorCreationFromItemStack() {

        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.ARMOR.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, "Test Armor");

        container.set(Main.getNamespacedKey("item-defense"), PersistentDataType.DOUBLE, 10.0);
        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, 100);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, 10);

        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, "light");

        item.setItemMeta(meta);

        Armor armor = CraftedItemFactory.createArmor(item);

        assert (armor.isValid());
        assert (armor.getDisplayName().equals("Test Armor"));
        assert (armor.getDefense() == 10.0);
        assert (armor.getDurability() == 100);
        assert (armor.getWeightValue() == 10);
        assert (armor.getWeight().equals(Weight.LIGHT));

    }

    @Test
    public void testCustomCreationFromItemStack() {

        ItemStack item = new ItemStack(Material.DIAMOND, 1);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.CUSTOM.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, "Test Custom");

        item.setItemMeta(meta);

        Custom custom = CraftedItemFactory.createCustom(item);

        assert (custom.isValid());
        assert (custom.getDisplayName().equals("Test Custom"));

    }

    @Test
    public void testToolCreationFromItemStack() {

        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        container.set(Main.getNamespacedKey("item-type"), PersistentDataType.STRING, ItemType.TOOL.getType());
        container.set(Main.getNamespacedKey("item-name"), PersistentDataType.STRING, "Test Tool");

        container.set(Main.getNamespacedKey("item-damage"), PersistentDataType.DOUBLE, 10.0);
        container.set(Main.getNamespacedKey("item-speed"), PersistentDataType.DOUBLE, 1.0);
        container.set(Main.getNamespacedKey("item-knockback"), PersistentDataType.DOUBLE, 1.0);

        container.set(Main.getNamespacedKey("item-durability"), PersistentDataType.INTEGER, 100);
        container.set(Main.getNamespacedKey("item-weight-value"), PersistentDataType.INTEGER, 10);
        container.set(Main.getNamespacedKey("item-weight"), PersistentDataType.STRING, "light");

        item.setItemMeta(meta);

        Tool tool = CraftedItemFactory.createTool(item);

        assert (tool.isValid());
        assert (tool.getDisplayName().equals("Test Tool"));
        assert (tool.getDamage() == 10.0);
        assert (tool.getSpeed() == 1.0);
        assert (tool.getKnockback() == 1.0);
        assert (tool.getDurability() == 100);
        assert (tool.getWeightValue() == 10);
        assert (tool.getWeight().equals(Weight.LIGHT));

    }

    @Test
    public void testWeaponCreationFromConfiguration() {

        ItemConfigurationFactory<WeaponConfiguration> factory = ItemConfigurationFactory
                .create(WeaponConfiguration.class);
        WeaponConfiguration configuration = factory.getObject("Light_Iron_Sword");
        Weapon weapon = CraftedItemFactory.createWeapon(configuration);

        assert (weapon.isValid());
        assert (weapon.getDisplayName().equals("§7Iron Dagger"));
        System.out.println(weapon.getDamage());
        assert (weapon.getDamage() >= 3.5);
        assert (weapon.getDamage() <= 5.5);
        System.out.println(weapon.getSpeed());
        assert (weapon.getSpeed() >= 1.3);
        assert (weapon.getSpeed() <= 1.6);
        System.out.println(weapon.getKnockback());
        assert (weapon.getKnockback() >= 0.0);
        assert (weapon.getKnockback() <= 0.5);
        System.out.println(weapon.getSweeping());
        assert (weapon.getSweeping() >= 1.75);
        assert (weapon.getSweeping() <= 2.75);
        System.out.println(weapon.getDurability());
        assert (weapon.getDurability() >= 145);
        assert (weapon.getDurability() <= 175);
        assert (weapon.getItemType() == ItemType.WEAPON);

    }

    @Test
    public void testArmorCreationFromConfiguration() {

        ItemConfigurationFactory<ArmorConfiguration> factory = ItemConfigurationFactory
                .create(ArmorConfiguration.class);
        ArmorConfiguration configuration = factory.getObject("Light_Iron_Chestplate");
        Armor armor = CraftedItemFactory.createArmor(configuration);

        assert (armor.isValid());
        System.out.println(armor.getDisplayName());
        assert (armor.getDisplayName().equals("§7Chainmail Chestplate"));
        System.out.println(armor.getDefense());
        assert (armor.getDefense() >= 2.5);
        assert (armor.getDefense() <= 4.0);
        System.out.println(armor.getDurability());
        assert (armor.getDurability() >= 140);
        assert (armor.getDurability() <= 240);
        assert (armor.getItemType() == ItemType.ARMOR);

    }

    @Test
    public void testCustomCreationFromConfiguration() {
        ItemConfigurationFactory<CustomItemConfiguration> factory = ItemConfigurationFactory
                .create(CustomItemConfiguration.class);
        CustomItemConfiguration configuration = factory.getObject("GUNPOWDER");
        Custom custom = CraftedItemFactory.createCustom(configuration);

        assert (custom.isValid());
        assert (custom.getDisplayName().equals("§7Gunpowder"));
        assert (custom.getItemType() == ItemType.CUSTOM);

    }

    @Test
    public void testToolCreationFromConfiguration() {
        ItemConfigurationFactory<ToolConfiguration> factory = ItemConfigurationFactory
                .create(ToolConfiguration.class);
        ToolConfiguration configuration = factory.getObject("Light_Iron_Pickaxe");
        Tool tool = CraftedItemFactory.createTool(configuration);

        assert (tool.isValid());
        assert (tool.getDisplayName().equals("§7Iron Pickaxe"));
        assert (tool.getDamage() >= 3.5);
        assert (tool.getDamage() <= 5.5);
        assert (tool.getSpeed() >= 1.3);
        assert (tool.getSpeed() <= 1.6);
        assert (tool.getKnockback() >= 0.0);
        assert (tool.getKnockback() <= 0.5);
        assert (tool.getDurability() >= 145);
        assert (tool.getDurability() <= 175);
        assert (tool.getItemType() == ItemType.TOOL);

    }

    @Test
    public void testWeaponCreationFromGivenParameters() {

        Weapon weapon = CraftedItemFactory.createWeapon(Material.IRON_SWORD, "Test Weapon", 10.0, 1.0, 1.0, 1.0, 100,
                Weight.LIGHT, 10, List.of());

        assert (weapon.isValid());
        assert (weapon.getDisplayName().equals("Test Weapon"));
        assert (weapon.getDamage() == 10.0);
        assert (weapon.getSpeed() == 1.0);
        assert (weapon.getKnockback() == 1.0);
        assert (weapon.getSweeping() == 1.0);
        assert (weapon.getDurability() == 100);
        assert (weapon.getWeightValue() == 10);
        assert (weapon.getWeight().equals(Weight.LIGHT));

    }

    @Test
    public void testArmorCreationFromGivenParameters() {

        Armor armor = CraftedItemFactory.createArmor(Material.IRON_CHESTPLATE, "Test Armor", 10.0, 100, Weight.LIGHT,
                10,
                List.of());

        assert (armor.isValid());
        assert (armor.getDisplayName().equals("Test Armor"));
        assert (armor.getDefense() == 10.0);
        assert (armor.getDurability() == 100);
        assert (armor.getWeightValue() == 10);
        assert (armor.getWeight().equals(Weight.LIGHT));

    }

    @Test
    public void testCustomCreationFromGivenParameters() {

        Custom custom = CraftedItemFactory.createCustom(Material.DIAMOND, 1, "Test Custom", List.of());

        assert (custom.isValid());
        assert (custom.getDisplayName().equals("Test Custom"));

    }

    @Test
    public void testToolCreationFromGivenParameters() {

        Tool tool = CraftedItemFactory.createTool(Material.IRON_PICKAXE, "Test Tool", 10.0, 1.0, 1.0, 100, Weight.LIGHT,
                10, List.of());

        assert (tool.isValid());
        assert (tool.getDisplayName().equals("Test Tool"));
        assert (tool.getDamage() == 10.0);
        assert (tool.getSpeed() == 1.0);
        assert (tool.getKnockback() == 1.0);
        assert (tool.getDurability() == 100);
        assert (tool.getWeightValue() == 10);
        assert (tool.getWeight().equals(Weight.LIGHT));

    }

}
