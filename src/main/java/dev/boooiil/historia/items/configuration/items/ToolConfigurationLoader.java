package dev.boooiil.historia.items.configuration.items;

/**
 * It's a class that gets information from a configuration file.
 */
public class ToolConfigurationLoader extends BaseItemConfigurationLoader<ToolConfiguration> {

    /**
     * Used to create a new instance of ToolConfiguration.
     * 
     * @param toolName - Name of the tool to create.
     * @return Returns a ToolConfiguration object.
     */
    public ToolConfiguration createNew(String toolName) {

        return new ToolConfiguration(this.configuration.getConfigurationSection(toolName));

    }

}