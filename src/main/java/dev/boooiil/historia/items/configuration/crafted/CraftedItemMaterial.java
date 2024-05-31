package dev.boooiil.historia.items.configuration.crafted;

import java.util.List;

import org.bukkit.Material;

public class CraftedItemMaterial<E extends Enum<E>> {

    private Material material;

    private boolean hasQualities;

    private List<MaterialMatchBy> matchBy;
    private List<E> acceptedModifiers;

    public CraftedItemMaterial(Material material) {
        this.material = material;
        hasQualities = false;
    }

    public CraftedItemMaterial(Class<E> enumClass, Material material) {
        this.material = material;
        hasQualities = true;
    }

    public void addMatchBy(MaterialMatchBy matchBy) {
        this.matchBy.add(matchBy);
    }

    public void addModifier(E modifier) {
        acceptedModifiers.add(modifier);
    }

    public boolean hasQualities() {
        return hasQualities;
    }

    public Material getMaterial() {
        return material;
    }
}
