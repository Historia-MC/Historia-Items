package dev.boooiil.historia.items.configuration.crafted;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.util.Logging;

public class CraftedItemMaterialBasic {

    protected ItemStack itemStack;

    protected boolean hasQualities;

    protected List<MaterialMatchBy> matchBy = new ArrayList<>();

    public CraftedItemMaterialBasic(ItemStack itemStack) {

        Logging.debugToConsole("CraftedItemMaterialBasic constructor called.");

        this.itemStack = itemStack;
        hasQualities = false;
    }

    public void addMatchBy(MaterialMatchBy matchBy) {
        this.matchBy.add(matchBy);
    }

    public boolean hasQualities() {
        return hasQualities;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("ItemStack: ").append(itemStack.toString()).append("\n");
        sb.append("Has Qualities: ").append(hasQualities).append("\n");
        sb.append("Match By: ").append(matchBy.toString()).append("\n");

        return sb.toString();

    }

    public String toString(Character key) {

        StringBuilder sb = new StringBuilder();
        StringBuilder sbk = new StringBuilder();
        String _key;

        _key = sbk.append("(").append(key).append(")").toString();

        sb.append(_key).append(" ItemStack: ").append(itemStack.toString()).append("\n");
        sb.append(_key).append(" Has Qualities: ").append(hasQualities).append("\n");
        sb.append(_key).append(" Match By: ").append(matchBy.toString()).append("\n");

        return sb.toString();
    }

}
