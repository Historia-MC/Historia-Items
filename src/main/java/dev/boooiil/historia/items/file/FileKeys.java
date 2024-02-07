package dev.boooiil.historia.items.file;

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

    private final String key;

    FileKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}
