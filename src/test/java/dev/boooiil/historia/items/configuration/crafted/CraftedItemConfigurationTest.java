package dev.boooiil.historia.items.configuration.crafted;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.crafted.modifiers.Quality;

public class CraftedItemConfigurationTest {

    private ServerMock server;

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

        System.out.println("Finished setup.");

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down mock...");
        MockBukkit.unmock();
    }

    @Test
    public void testCraftedItemConfigurationInitialization() {

        System.out.println("Testing CraftedItemConfiguration initialization...");

        CraftedItemConfiguration cic = new CraftedItemConfiguration();
        CraftedItemMaterialComplex<Quality> cimc_cobble = new CraftedItemMaterialComplex<>(Quality.class,
                new ItemStack(Material.COBBLESTONE));
        CraftedItemMaterialBasic cimb_stick = new CraftedItemMaterialBasic(new ItemStack(Material.STICK));

        cimc_cobble.addModifier(Quality.COMMON);
        cimc_cobble.addModifier(Quality.UNCOMMON);
        cimc_cobble.addModifier(Quality.RARE);

        cimc_cobble.addMatchBy(MaterialMatchBy.MODIFIER);

        cic.setProficiencyRequirement("testProficiencyRequirement");
        cic.setRecipeShape(List.of("AAA", " B ", " B "));
        cic.setIngredient('A', cimc_cobble);
        cic.setIngredient('B', cimb_stick);

        System.out.println(cic.toString());

    }

}
