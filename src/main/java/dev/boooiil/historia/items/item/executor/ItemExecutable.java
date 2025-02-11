package dev.boooiil.historia.items.item.executor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.Main;
import dev.boooiil.historia.items.util.JSONUtils;
import dev.boooiil.historia.items.util.KyoriUtils;

public class ItemExecutable {
    private List<String> commands;
    // ticks
    private int cooldown;
    private int uses;
    private boolean hasElevation;
    private boolean hasCooldown;

    public ItemExecutable(
            List<String> commands,
            int cooldown,
            int uses,
            boolean hasElevation,
            boolean hasCooldown) {
        this.commands = commands;
        this.cooldown = cooldown;
        this.uses = uses;
        this.hasCooldown = hasCooldown;

    }

    public ItemStack execute(Player player, ItemStack item) {

        for (String command : applyCommandPlaceholder(player)) {

            if (hasElevation)
                Main.server().dispatchCommand(Main.server().getConsoleSender(), command);
            else
                player.performCommand(command);
        }

        this.uses--;

        return item;
    }

    private List<String> applyCommandPlaceholder(Player player) {
        List<String> nCommands = new ArrayList<>();

        for (String command : commands) {

            String nCommand = KyoriUtils.replace(command, "player", player.getName());

            nCommands.add(nCommand);
        }

        return nCommands;
    }

    public List<String> commands() {
        return this.commands;
    }

    public int cooldown() {
        return this.cooldown;
    }

    public int uses() {
        return this.uses;
    }

    public boolean hasElevation() {
        return this.hasElevation;
    }

    public boolean hasCooldown() {
        return this.hasCooldown;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ItemExecutable{");
        sb.append(JSONUtils.fromStringList("commands", commands) + ", ");
        sb.append(JSONUtils.fromValue("uses", this.uses) + ", ");
        sb.append(JSONUtils.fromValue("cooldown", this.cooldown) + ", ");
        sb.append(JSONUtils.fromValue("elevated", this.hasElevation) + ", ");
        sb.append(JSONUtils.fromValue("hasCooldown", this.hasCooldown));
        sb.append("}");

        return sb.toString();
    }

}