package dev.boooiil.historia.items.item.executor;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class ItemExecutable {
    private List<String> commands;
    // ticks
    private int cooldown;
    private int ueses;
    private boolean hasCooldown;

    public ItemExecutable(
            List<String> commands,
            int cooldown,
            int uses,
            boolean hasCooldown) {
        this.commands = commands;
        this.cooldown = cooldown;
        this.ueses = uses;
        this.hasCooldown = hasCooldown;

    }

    public ItemStack execute(ItemStack item) {
        return item;
    }

    public List<String> commands() {
        return this.commands;
    }

    public int cooldown() {
        return this.cooldown;
    }

    public int uses() {
        return this.ueses;
    }

    public boolean hasCooldown() {
        return this.hasCooldown;
    }

}