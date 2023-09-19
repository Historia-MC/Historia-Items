package dev.boooiil.historia.items.classes.enums.file;

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
    CUSTOM_ITEMS("customitems.yml");

    private final String key;

    FileKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}
