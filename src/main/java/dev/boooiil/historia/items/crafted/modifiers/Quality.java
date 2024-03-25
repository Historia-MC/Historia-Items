package dev.boooiil.historia.items.crafted.modifiers;

/**
 * An enum representing the quality of an item.
 */
public enum Quality {

    /** poor value */
    POOR(0, "poor"),
    /** common value */
    COMMON(1, "common"),
    /** uncommon value */
    UNCOMMON(2, "uncommon"),
    /** rare value */
    RARE(3, "rare"),
    /** perfect value */
    PERFECT(4, "perfect");

    /** int value of the enum */
    private int value;
    /** string key of the enum */
    private String key;

    /**
     * Constructs a Quality enum with a value and key.
     *
     * @param value The value of the quality.
     * @param key   The key of the quality.
     */
    private Quality(int value, String key) {

        this.value = value;
        this.key = key;

    }

    /**
     * Get the value of the quality.
     *
     * @return The value of the quality.
     */
    public int getValue() {

        return this.value;

    }

    /**
     * Get the key of the quality.
     *
     * @return The key of the quality.
     */
    public String getKey() {

        return this.key;

    }

    /**
     * Get the proper name of the quality.
     *
     * @return The proper name of the quality.
     */
    public String getProperName() {

        String normalizedWeightString = this.key.substring(0, 1).toUpperCase() + this.key.substring(1);

        return normalizedWeightString;

    }

    /**
     * Get the colored proper name of the quality.
     *
     * @return The colored proper name of the quality.
     */
    public String getProperNameColored() {

        switch (this) {

            case POOR:
                return "§c" + this.getProperName();

            case COMMON:
                return "§f" + this.getProperName();

            case UNCOMMON:
                return "§2" + this.getProperName();

            case RARE:
                return "§f" + this.getProperName();

            case PERFECT:
                return "§6" + this.getProperName();

            default:
                return "§f" + this.getProperName();

        }

    }

    /**
     * Get the color of the quality.
     *
     * @return The color of the quality.
     */
    public String getQualityColor() {

        switch (this) {

            case POOR:
                return "§c";

            case COMMON:
                return "§f";

            case UNCOMMON:
                return "§2";

            case RARE:
                return "§9";

            case PERFECT:
                return "§6";

            default:
                return "§f";

        }
    }

    /**
     * Get the quality of an item by its value.
     *
     * @param value The value of the quality.
     * @return The quality of the item.
     */
    public static Quality getQuality(int value) {

        for (Quality tier : Quality.values()) {

            if (tier.getValue() == value) {

                return tier;

            }

        }

        return null;

    }

    /**
     * Get the quality of an item by its key.
     *
     * @param key The key of the quality.
     * @return The quality of the item.
     */
    public static Quality getQuality(String key) {

        for (Quality tier : Quality.values()) {

            if (tier.getKey().equalsIgnoreCase(key)) {

                return tier;

            }

        }

        return null;

    }

}