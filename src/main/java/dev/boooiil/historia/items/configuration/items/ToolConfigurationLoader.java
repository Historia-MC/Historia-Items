package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.configuration.BaseConfigurationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It's a class that gets information from a configuration file.
 */
public class ToolConfigurationLoader extends BaseConfigurationLoader<ToolConfiguration> {

    /**
     * Used to create a new instance of ToolConfiguration.
     * 
     * @param toolName - Name of the tool to create.
     * @return Returns a ToolConfiguration object.
     */
    public ToolConfiguration createNew(String toolName) {

        return new ToolConfiguration(this.configuration.getConfigurationSection(toolName));

    }

    /**
     * It takes a list of items and a list of shapes, and returns a tool if the
     * items and shapes
     * match a tool recipe
     * 
     * @param inputItems A list of strings that represent the items used to craft
     *                   the tool.
     * @param inputShape A list of strings that represent the shape of the tool.
     * @return A ToolConfiguration object.
     */
    public ToolConfiguration getObject(List<String> inputItems, List<String> inputShape) {

        ToolConfiguration toolConfiguration = null;

        for (Map.Entry<String, ToolConfiguration> entry : map.entrySet()) {

            boolean armorValid = entry.getValue().isValidRecipe(inputItems, inputShape);

            if (armorValid) {
                toolConfiguration = entry.getValue();
                break;
            }

        }

        return toolConfiguration;

    }

    /**
     * If the tool name is valid, return the tool object from the map. Otherwise,
     * return null
     * 
     * @param toolName The name of the tool you want to get.
     * @return A ToolConfiguration object.
     */
    public ToolConfiguration getObject(String toolName) {

        if (isValid(toolName))
            return map.get(toolName);
        else
            return null;

    }

    /**
     * It checks if the shape of the recipe is valid
     * 
     * @param shape The shape of the recipe.
     * @return A boolean value.
     */
    public boolean validShape(List<String> shape) {

        boolean found = false;

        for (ToolConfiguration toolConfiguration : map.values()) {

            if (toolConfiguration.getRecipeShape().equals(shape)) {

                found = true;
                break;

            }
        }

        return found;

    }

    /**
     * It returns a list of all the recipes for the tools
     * 
     * @return A list of lists of strings.
     */
    public List<List<String>> getAllShapes() {

        List<List<String>> set = new ArrayList<>();

        for (ToolConfiguration toolConfiguration : map.values()) {

            set.add(toolConfiguration.getRecipeShape());
        }

        return set;

    }

    /**
     * It returns a list of all the items that have the same recipe shape as the one
     * passed in
     * 
     * @param shape A list of strings that represent the shape of the recipe.
     * @return A list of all the tool items that match the shape.
     */
    public List<BaseConfiguration> getAllMatchingShape(List<String> shape) {

        List<BaseConfiguration> set = new ArrayList<>();

        for (ToolConfiguration toolConfiguration : map.values()) {

            if (toolConfiguration.getRecipeShape().equals(shape)) {

                set.add(toolConfiguration);

            }
        }

        return set;

    }
}