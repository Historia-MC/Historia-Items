package dev.boooiil.historia.items.item.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.RegEx;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import dev.boooiil.historia.items.item.ItemComponent;
import dev.boooiil.historia.items.item.data.EnchantData;
import dev.boooiil.historia.items.util.HILogger;
import dev.boooiil.historia.core.util.JSONUtils;

public class EnchantComponent implements ItemComponent {

    private final HashMap<Enchantment, Integer> enchantments;

    public EnchantComponent(HashMap<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public static EnchantComponent fromConfig(ConfigurationSection section) {

        HashMap<Enchantment, Integer> map = new HashMap<>();

        for (String enchant : section.getKeys(false)) {
            Enchantment enchantment = Enchantment.getByName(enchant);

            if (enchantment == null) {
                HILogger.errorToConsole("Tried to get enchantment",
                        enchant, "from enchantment component but it does not exist.");
                continue;

            }

            map.put(enchantment, section.getInt(enchant));

        }

        return new EnchantComponent(map);

    }

    @Override
    public EnchantData data() {
        return new EnchantData(this.enchantments);
    }

    @Override
    public EnchantData data(float qualityModifier) {
        return data();
    }

    public HashMap<Enchantment, Integer> enchantments() {
        return this.enchantments;
    }

    @Override
    public String getKey() {
        return "enchant";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("EnchantComponent");
        sb.append("{");
        sb.append(JSONUtils.fromMap("enchantments", enchantments));
        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromMap("enchantments", enchantments));
        sb.append("}");

        return sb.toString();

    }

}
