package dev.boooiil.historia.items.items.modifiers;

public enum Weight {

    LIGHT(0, "light"),
    MODERATE(1, "moderate"),
    HEAVY(2, "heavy");

    private int value;
    private String key;

    private Weight(int value, String key) {

        this.value = value;
        this.key = key;

    }

    public int getValue() {

        return this.value;

    }

    public String getKey() {

        return this.key;

    }

    public String getProperName() {

        String normalizedWeightString = this.key.substring(0, 1).toUpperCase() + this.key.substring(1);

        return normalizedWeightString;

    }

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

    public static Weight getWeight(int value) {

        for (Weight tier : Weight.values()) {

            if (tier.getValue() == value) {

                return tier;

            }

        }

        return null;

    }

    public static Weight getWeight(String key) {

        for (Weight tier : Weight.values()) {

            if (tier.getKey().equalsIgnoreCase(key)) {

                return tier;

            }

        }

        return null;

    }

}
