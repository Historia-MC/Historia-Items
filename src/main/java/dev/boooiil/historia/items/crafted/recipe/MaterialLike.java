package dev.boooiil.historia.items.crafted.recipe;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import dev.boooiil.historia.items.Main;

public class MaterialLike {

    private Material baseMaterial;
    private String materialName;
    private boolean isWoodLike;

    public MaterialLike(Material baseMaterial) {
        this.baseMaterial = baseMaterial;
        this.materialName = baseMaterial.name();
    }

    public MaterialLike(Material baseMaterial, String materialName) {
        this.baseMaterial = baseMaterial;
        this.materialName = materialName;
    }

    public void setWoodLike(boolean isWoodLike) {
        this.isWoodLike = isWoodLike;
    }

    public boolean isWoodLike() {
        return isWoodLike;
    }

    public Material getBaseMaterial() {
        return baseMaterial;
    }

    public String getMaterialName() {
        return materialName;
    }

    public boolean match(ItemStack item) {

        // guard against null value
        if (item == null) {
            return false;
        }

        // guard against invalid material
        if (item.getType() != baseMaterial) {
            return false;
        }

        // match localized name
        if (item.getItemMeta().getLocalizedName() == materialName) {
            return true;
        }

        // match persistent data container
        if (item.hasItemMeta()) {
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            NamespacedKey key = Main.getNamespacedKey("item-name");

            if (container.has(key)) {
                if (container.get(key, PersistentDataType.STRING) == materialName) {
                    return true;
                }
            }
        }

        return false;

    }

}
