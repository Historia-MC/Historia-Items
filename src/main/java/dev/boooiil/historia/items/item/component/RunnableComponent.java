package dev.boooiil.historia.items.item.component;

import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.RunnableData;
import dev.boooiil.historia.core.util.JSONUtils;

public class RunnableComponent implements ItemComponent {

    private final int ticks;
    private final String command;
    private final String permission;

    public RunnableComponent(int ticks, String command, String permission) {
        this.ticks = ticks;
        this.command = command;
        this.permission = permission;
    }

    public static RunnableComponent fromConfig(ConfigurationSection section) {

        int ticks = section.getInt("ticks");
        String command = section.getString("command");
        String permission = section.getString("permission");

        return new RunnableComponent(ticks, command, permission);

    }

    @Override
    public RunnableData apply() {
        return new RunnableData(this.ticks, this.command, this.permission);
    }

    @Override
    public RunnableData apply(float qualityModifier) {
        return apply();
    }

    @Override
    public String getKey() {
        return "runnable";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("RunnableComponent");
        sb.append("{");
        sb.append(JSONUtils.fromValue("ticks", ticks) + ", ");
        sb.append(JSONUtils.fromValue("command", command) + ", ");
        sb.append(JSONUtils.fromValue("permission", permission));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("ticks", ticks) + ", ");
        sb.append(JSONUtils.fromValue("command", command) + ", ");
        sb.append(JSONUtils.fromValue("permission", permission));
        sb.append("}");

        return sb.toString();

    }

}
