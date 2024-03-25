package dev.boooiil.historia.items.crafted.modifiers;

/**
 * An enum representing the weight of an item.
 */
public enum Weight {

    /** light value */
    LIGHT(0, "light"),
    /** moderate value */
    MODERATE(1, "moderate"),
    /** heavy value */
    HEAVY(2, "heavy");

    /** int value of the enum */
    private int value;
    /** string key of the enum */
    private String key;

    /**
     * Constructs a Weight enum with a value and key.
     *
     * @param value The value of the weight.
     * @param key   The key of the weight.
     */
    private Weight(int value, String key) {

        this.value = value;
        this.key = key;

    }

    /**
     * Get the value of the weight.
     *
     * @return The value of the weight.
     */
    public int getValue() {

        return this.value;

    }

    /**
     * Get the key of the weight.
     *
     * @return The key of the weight.
     */
    public String getKey() {

        return this.key;

    }

    /**
     * Get the proper name of the weight.
     *
     * @return The proper name of the weight.
     */
    public String getProperName() {

        String normalizedWeightString = this.key.substring(0, 1).toUpperCase() + this.key.substring(1);

        return normalizedWeightString;

    }

    /**
     * Get the colored proper name of the weight.
     *
     * @return The colored proper name of the weight.
     */
    public String getWeightColor() {

        switch (this) {

            case LIGHT:
                return "§9" + this.getProperName();

            case MODERATE:
                return "§9" + this.getProperName();

            case HEAVY:
                return "§9" + this.getProperName();

            default:
                return "§f" + this.getProperName();

        }

    }

    /**
     * Get the weight of the item based on the value.
     *
     * @param value The value of the weight.
     * @return The weight of the item.
     */
    public static Weight getWeight(int value) {

        for (Weight tier : Weight.values()) {

            if (tier.getValue() == value) {

                return tier;

            }

        }

        return null;

    }

    /**
     * Get the weight of the item based on the key.
     *
     * @param key The key of the weight.
     * @return The weight of the item.
     */
    public static Weight getWeight(String key) {

        for (Weight tier : Weight.values()) {

            if (tier.getKey().equalsIgnoreCase(key)) {

                return tier;

            }

        }

        return null;

    }

}
