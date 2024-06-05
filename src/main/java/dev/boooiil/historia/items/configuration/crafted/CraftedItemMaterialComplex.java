package dev.boooiil.historia.items.configuration.crafted;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.util.Logging;

public class CraftedItemMaterialComplex<E extends Enum<E>> extends CraftedItemMaterialBasic {

    private List<E> acceptedModifiers = new ArrayList<>();

    public CraftedItemMaterialComplex(Class<E> enumClass, ItemStack itemStack) {
        super(itemStack);

        Logging.debugToConsole("CraftedItemMaterialComplex constructor called.");

        hasQualities = true;
    }

    public void addModifier(E modifier) {
        acceptedModifiers.add(modifier);
    }

    public List<E> getAcceptedModifiers() {
        return acceptedModifiers;
    }

    public boolean hasModifier(E modifier) {
        return acceptedModifiers.contains(modifier);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Modifiers: ").append(acceptedModifiers.toString()).append("\n");

        return sb.toString();

    }

    public String toString(Character key) {

        StringBuilder sb = new StringBuilder();
        StringBuilder sbk = new StringBuilder();
        String _key;

        _key = sbk.append("(").append(key).append(")").toString();

        sb.append(super.toString(key));
        sb.append(_key).append(" Modifiers: ").append(acceptedModifiers.toString()).append("\n");

        return sb.toString();
    }

}
