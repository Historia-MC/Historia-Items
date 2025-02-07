package dev.boooiil.historia.items.item.types;

import dev.boooiil.historia.items.util.Logging;

public enum Triggers {
    RIGHT_CLICK(1),
    LEFT_CLICK(2),
    RIGHT_CLICK_BLOCK(3),
    LEFT_CLICK_BLOCK(4),
    SNEAK_RIGHT_CLICK(5),
    SNEAK_LEFT_CLICK(6),
    SNEAK_RIGHT_CLICK_BLOCK(7),
    SNEAK_LEFT_CLICK_BLOCK(8),
    ATTACK_ENTITY(9),
    DAMAGE_ENTITY(10),
    INTERACT_ENTITY(11),
    OPEN_INVENTORY(12),
    CLOSE_INVENTORY(13),
    SWAP_ITEM(14),
    PLACE(15),
    PICKUP(16),
    DROP(17),
    EAT(18),
    DRINK(19),
    JUMP(20),
    SPRINT(21),
    SWIM(22),
    THROW(23),
    CROUCH(24),
    UNCROUCH(25),
    UNKNOWN(0); // Default ID for unknown triggers

    private final int id;
    private final String lowercase;

    Triggers(int id) {
        this.id = id;
        this.lowercase = this.name().toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getLowercase() {
        return lowercase;
    }

    @Override
    public String toString() {
        return lowercase;
    }

    public static Triggers fromId(int id) {
        for (Triggers trigger : values()) {
            if (trigger.id == id) {
                return trigger;
            }
        }

        Logging.errorToConsole("Tried to get trigger with id: " + id, "but it did not exist.");
        return UNKNOWN;
    }

    public static Triggers fromString(String strTrigger) {
        for (Triggers trigger : values()) {
            if (trigger.lowercase.equals(strTrigger)) {
                return trigger;
            }
        }

        Logging.errorToConsole("Tried to get trigger with string: " + strTrigger, "but it did not exist.");
        return UNKNOWN;
    }
}
