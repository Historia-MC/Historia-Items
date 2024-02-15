package dev.boooiil.historia.items.configuration.items;

import dev.boooiil.historia.items.util.Logging;

import java.util.ArrayList;
import java.util.List;

public class CustomItemConfigurationLoader extends BaseItemConfigurationLoader<CustomItemConfiguration> {

    @Override
    public CustomItemConfiguration createNew(String itemName) {
        return new CustomItemConfiguration(this.configuration.getConfigurationSection(itemName));
    }

    /**
     * It returns a list of all the items that have the same recipe shape as the one
     * passed in
     * 
     * @param shape A list of strings that represent the shape of the recipe.
     * @return A list of all the armor items that match the shape.
     */
    @Override
    public List<BaseItemConfiguration> getAllMatchingShape(List<String> shape) {

        List<BaseItemConfiguration> set = new ArrayList<>();

        for (CustomItemConfiguration customItem : map.values()) {

            if (!customItem.isShapeDependent())
                set.add(customItem);
            else if (customItem.getRecipeShape().equals(shape)) {

                Logging.debugToConsole(customItem.toString());

                set.add(customItem);

            }
        }

        return set;

    }

}
