package dev.boooiil.historia.items.file;

/**
 * An enum representing the keys of the files in the plugin.
 */
public enum FileKeys {

    /** config.yml */
    CONFIG("config.yml"),
    /** armor.yml */
    ARMOR("armor.yml"),
    /** weapons.yml */
    WEAPONS("weapons.yml"),
    /** tools.yml */
    TOOLS("tools.yml"),
    /** customitems.yml */
    CUSTOM_ITEMS("items.yml"),
    /** ingots.yml */
    INGOTS("ingots.yml");

    private final String filename;

    /**
     * Constructs a FileKeys enum with a filename.
     *
     * @param filename The filename of the file.
     */
    FileKeys(String filename) {

        this.filename = filename;

    }

    /**
     * Get the filename.
     *
     * @return The filename of the file.
     */
    public String getKey() {

        return this.filename;

    }

}
