package dev.boooiil.historia.items.item.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.file.FileIO;
import dev.boooiil.historia.items.item.data.ArmorData;

public class ArmorComponentTest {

    private static ServerMock server;
    private static Main plugin;
    YamlConfiguration configuration = FileIO.findYamlConfiguration("bronze_boots.yml");
    ConfigurationSection item_root = configuration.getConfigurationSection("Light_Bronze_Boots");
    ConfigurationSection component_root = item_root.getConfigurationSection("armor");
    ArmorComponent component = ArmorComponent.fromConfig(component_root);

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up mock...");
        server = MockBukkit.mock();
        System.out.println("Loading plugin...");
        try {
            plugin = MockBukkit.load(Main.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished setup.");

    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    void testApply() {
        ArmorData data = component.data();

        assertTrue(data.getDefense() > component.defenseRange().get(0)
                && data.getDefense() < component.defenseRange().get(1));

    }

    @Test
    void testApply2() {
        ArmorData data = component.data(1f);

        assertTrue(data.getDefense() > component.defenseRange().get(0)
                && data.getDefense() < component.defenseRange().get(1));

    }

    @Test
    void testDefenseRange() {
        assertEquals(component_root.getFloatList("defense"), component.defenseRange());
    }

    @Test
    void testDurabilityRange() {
        assertEquals(component_root.getIntegerList("durability"), component.durabilityRange());
    }

    @Test
    void testGetKey() {
        assertEquals("armor", component.getKey());
    }

    @Test
    void testToJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("\"defenseRange\":");
        sb.append("[");
        sb.append(component.defenseRange().get(0) + ", ");
        sb.append(component.defenseRange().get(1) + "], ");
        sb.append("\"durabilityRange\":");
        sb.append("[");
        sb.append(component.durabilityRange().get(0) + ", ");
        sb.append(component.durabilityRange().get(1) + "]");
        sb.append("}");

        assertEquals(sb.toString(), component.toJSON());

    }

    @Test
    void testToString() {

        StringBuilder sb = new StringBuilder();

        sb.append("ArmorComponent{");
        sb.append("\"defenseRange\":");
        sb.append("[");
        sb.append(component.defenseRange().get(0) + ", ");
        sb.append(component.defenseRange().get(1) + "], ");
        sb.append("\"durabilityRange\":");
        sb.append("[");
        sb.append(component.durabilityRange().get(0) + ", ");
        sb.append(component.durabilityRange().get(1) + "]");
        sb.append("}");

        assertEquals(sb.toString(), component.toString());

    }
}
