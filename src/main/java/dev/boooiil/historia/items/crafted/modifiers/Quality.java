package dev.boooiil.historia.items.crafted.modifiers;

public enum Quality {

    POOR(0, "poor"),
    COMMON(1, "common"),
    UNCOMMON(2, "uncommon"),
    RARE(3, "rare"),
    PERFECT(4, "perfect");

    private int value;
    private String key;

    private Quality(int value, String key) {

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

    public static Quality getQuality(int value) {

        for (Quality tier : Quality.values()) {

            if (tier.getValue() == value) {

                return tier;

            }

        }

        return null;

    }

    public static Quality getQuality(String key) {

        for (Quality tier : Quality.values()) {

            if (tier.getKey().equalsIgnoreCase(key)) {

                return tier;

            }

        }

        return null;

    }

}