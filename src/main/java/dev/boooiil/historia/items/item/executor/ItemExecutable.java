package dev.boooiil.historia.items.item.executor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class ItemExecutable {
    private List<String> commands;
    // ticks
    private int cooldown;
    private int uses;
    private boolean hasCooldown;

    public ItemExecutable(
            List<String> commands,
            int cooldown,
            int uses,
            boolean hasCooldown) {
        this.commands = commands;
        this.cooldown = cooldown;
        this.uses = uses;
        this.hasCooldown = hasCooldown;

    }

    public ItemStack execute(Player player, ItemStack item) {

        for (String command : applyCommandPlaceholder(player)) {

            player.performCommand(command);

        }

        this.uses--;

        return item;
    }

    private List<String> applyCommandPlaceholder(Player player) {
        List<String> nCommands = new ArrayList<>();

        for (String command : commands) {
            String nCommand = ((TextComponent) MiniMessage.miniMessage().deserialize(command,
                    Placeholder.component("player",
                            Component.text(player.getName()))))
                    .content();

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

    public boolean hasCooldown() {
        return this.hasCooldown;
    }

}