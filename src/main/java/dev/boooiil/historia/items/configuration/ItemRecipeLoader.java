package dev.boooiil.historia.items.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import dev.boooiil.historia.items.util.Construct;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.crafted.modifiers.Quality;
import dev.boooiil.historia.items.crafted.modifiers.Weight;
import dev.boooiil.historia.items.generic.Ingot;

public class ItemRecipeLoader {

    public static void init() {

        deregister();
        register();

    }

    public static void reload() {

        Bukkit.resetRecipes();

        init();

    }

    public static void deregister() {

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("golden_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("wooden_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wooden_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wooden_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wooden_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wooden_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("chainmail_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("chainmail_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("chainmail_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("chainmail_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("leather_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("leather_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("leather_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("leather_boots"));

    }

    public static void register() {

        HashMap<String, BaseItemConfiguration> registry = ItemConfigurationRegistry.registry;

        // #region base recipe registration
        // iterate over registered configurations
        for (Map.Entry<String, BaseItemConfiguration> entry : registry.entrySet()) {

            // get base configuration from k,v pair
            BaseItemConfiguration configuration = entry.getValue();
            boolean hasIngot = false;

            System.out.println(configuration.getID());

            for (String mat : configuration.getRecipeItems()) {
                if (mat.contains("INGOT") || mat.contains("LEATHER")) {
                    hasIngot = true;
                    break;
                }
            }

            if (hasIngot) {
                registerIngotItem(configuration);
                Logging.debugToConsole(
                        "Registered recipe for " + configuration.getDisplayName() + " with ID: "
                                + configuration.getID());
                continue;
            }

            // construct base item for crafting result
            ItemStack item = Construct.itemStack(configuration.getMaterial(), 1, configuration.getDisplayName(),
                    new ArrayList<>(configuration.getLore()));

            // create shaped recipe
            ShapedRecipe recipe = new ShapedRecipe(Main.getNamespacedKey(configuration.getID()), item);

            String[] pattern = {
                    configuration.getRecipeShape().get(0),
                    configuration.getRecipeShape().get(1),
                    configuration.getRecipeShape().get(2)
            };

            recipe.shape(pattern[0], pattern[1], pattern[2]);

            // iterate over recipe items (we know that the items will be in proper order)
            // we as in me
            for (int i = 0; i < configuration.getRecipeItems().size(); i++) {

                // letters for the pattern will match index of the material in the recipe
                final String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

                // get itemstack of the material
                // if the item is in the registry, build the itemstack
                // if the item is an ingot, build the ingot and variations

                recipe.setIngredient(letter[i].charAt(0), configuration.getMaterial());
            }

            Logging.debugToConsole(
                    "Registered recipe for " + configuration.getDisplayName() + " with ID: " + configuration.getID());
            Bukkit.addRecipe(recipe);
        }

        // #endregion

    }

    // need to get the ingot amount (generally count how many "A"'s in the recipe')
    // then we need to get the weight of the ingot
    //

    public static void registerIngotItem(BaseItemConfiguration configuration) {

        Quality[] qualities = { Quality.POOR, Quality.UNCOMMON, Quality.PERFECT };
        List<String> modifiedShape = new ArrayList<>();
        int ingotCount = 0;
        int charIndex = 0;

        // iterate over the material strings in the recipe shape
        for (String mat : configuration.getRecipeShape()) {
            String row = "";
            // some recipes have spaces or multiple characters for a single material
            // so we need to iterate over the string instead of simply searching
            for (int i = 0; i < mat.length(); i++) {
                if (mat.charAt(i) == 'A') {
                    ingotCount++;
                    row += String.valueOf((char) ('A' + charIndex));
                    charIndex++;
                } else if (mat.charAt(i) == 'B') {
                    row += 'Z';
                } else
                    row += mat.charAt(i);
            }

            modifiedShape.add(row);
        }

        assert ingotCount > 0;

        String[] split = configuration.getRecipeItems().get(0).split("_");

        Weight weight = Weight.getWeight(split[0]);
        Material material = Material.AIR;

        switch (split[1]) {
            case "IRON":
            case "TIN":
                material = Material.IRON_INGOT;
            case "COPPER":
            case "BRONZE":
            case "GOLD":
                material = Material.GOLD_INGOT;
                break;
            case "STEEL":
                material = Material.NETHERITE_INGOT;
            case "LEATHER":
                material = Material.LEATHER;
                break;
        }

        assert material != Material.AIR;
        // get the weight of the ingot
        // this will MORE THAN LIKELY BREAK

        // Logging.debugToConsole("Item", configuration.getID(), "has " + ingotCount,
        // "ingots with weight " + weight);
        // Logging.debugToConsole("Modified shape", modifiedShape.toString());

        List<List<Quality>> qualityCombinations = generateQualityCombinations(qualities, ingotCount);

        for (int i = 0; i < qualityCombinations.size(); i++) {

            List<Quality> combination = qualityCombinations.get(i);
            registerRecipe(configuration, modifiedShape, combination, weight, material, i);

            // String build = configuration.getID();

            // for (Quality quality : combination) {
            // build += " " + quality.getKey();
            // }

            // System.out.println(build);
        }
    }

    private static List<List<Quality>> generateQualityCombinations(Quality[] qualities, int itemCount) {
        List<List<Quality>> combinations = new ArrayList<>();
        generateQualityCombinationsHelper(qualities, itemCount, new ArrayList<>(), combinations);
        return combinations;
    }

    private static void generateQualityCombinationsHelper(Quality[] qualities, int itemCount, List<Quality> current,
            List<List<Quality>> combinations) {
        if (current.size() == itemCount) {
            combinations.add(new ArrayList<>(current));
            return;
        }

        for (Quality quality : qualities) {
            current.add(quality);
            generateQualityCombinationsHelper(qualities, itemCount, current,
                    combinations);
            current.remove(current.size() - 1);
        }
    }

    private static void registerRecipe(BaseItemConfiguration configuration, List<String> modifiedShape,
            List<Quality> qualityCombination, Weight weight, Material material, int variation) {

        ItemStack item = Construct.itemStack(configuration.getMaterial(), 1, configuration.getDisplayName(),
                new ArrayList<>(configuration.getLore()));

        ShapedRecipe recipe = new ShapedRecipe(Main.getNamespacedKey(configuration.getID() + "-" + variation), item);

        String s1 = modifiedShape.get(0);
        String s2 = modifiedShape.get(1);
        String s3 = modifiedShape.get(2);

        recipe.shape(s1, s2, s3);
        recipe.setGroup(configuration.getID());

        int ingotIndex = 0;

        for (String row : modifiedShape) {
            for (int i = 0; i < row.length(); i++) {

                if (row.charAt(i) < 'A' || row.charAt(i) > 'I')
                    continue;

                char ingredient = row.charAt(i);
                Quality quality = qualityCombination.get(ingotIndex++);
                String id = "";
                String displayName = "";

                if (material != Material.LEATHER) {
                    String[] split = material.name().split("_");
                    String mat1 = split[0].substring(0, 1).toUpperCase() + split[0].substring(1).toLowerCase();
                    String mat2 = split[1].substring(0, 1).toUpperCase() + split[1].substring(1).toLowerCase();
                    id = mat1 + "_" + mat2;
                    displayName = weight.getWeightColor() + " " + mat1 + " " + mat2;
                } else {
                    id = "LEATHER";
                    displayName = weight.getWeightColor() + " Leather";
                }

                Ingot ingot = new Ingot(material, id, displayName, weight, quality);

                recipe.setIngredient(ingredient, ingot.getItemStack());

            }
        }

        Bukkit.addRecipe(recipe);
    }

}
