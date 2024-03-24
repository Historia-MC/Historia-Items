package dev.boooiil.historia.items.handlers.inventory;

import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.experience.CraftingSources;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.crafted.armor.Armor;
import dev.boooiil.historia.items.crafted.weapon.Weapon;
import dev.boooiil.historia.items.generic.Ingot;
import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftInventoryHelper;
import dev.boooiil.historia.items.util.Logging;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CraftingResult {

    private final Inventory inventory;
    private final BaseItemConfiguration craftedItem;
    private final HistoriaPlayer historiaPlayer;
    private ItemStack result;

    public CraftingResult(Inventory inventory, ItemStack result, BaseItemConfiguration craftedItem,
            HistoriaPlayer historiaPlayer) {
        this.inventory = inventory;
        this.result = result;
        this.historiaPlayer = historiaPlayer;
        this.craftedItem = craftedItem;
    }

    public void generateRandomModifiers() {

        switch (craftedItem.getItemType()) {
            case ARMOR:
                Logging.debugToConsole("[generateRandomModifiers] Armor");
                generateArmorModifiers();
                break;
            case WEAPON:
                Logging.debugToConsole("[generateRandomModifiers] Weapon");
                generateWeaponModifiers();
                break;
            default:
                Logging.debugToConsole("[generateRandomModifiers] Other");
                historiaPlayer.increaseExperience(CraftingSources.OTHER_CRAFT.getKey());
                break;
        }

    }

    private void generateArmorModifiers() {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        ArmorConfiguration armorConfiguration = (ArmorConfiguration) craftedItem;
        Armor armor = new Armor(armorConfiguration);

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());

        result = armor.getItemStack();

    }

    private void generateWeaponModifiers() {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        WeaponConfiguration weaponConfiguration = (WeaponConfiguration) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());

        Weapon weapon = new Weapon(weaponConfiguration);

        result = weapon.getItemStack();

    }

    public float getQualityBonus(List<String> materials) {

        int high = 0;
        int medium = 0;
        int low = 0;
        int complexity = materials.size();
        float mediumWeight = 50 / (float) complexity;
        float highWeight = 100 / (float) complexity;

        for (String material : materials) {

            Ingot ingot = Ingot.parseFromString(material);

            if (!ingot.isValid())
                continue;

            switch (ingot.getQuality()) {
                case PERFECT:
                    high++;
                    break;
                case UNCOMMON:
                    medium++;
                    break;
                case POOR:
                    low++;
                    break;
                default:
                    break;
            }

        }

        Logging.debugToConsole("[getQualityBonus] High: " + high);
        Logging.debugToConsole("[getQualityBonus] Medium: " + medium);
        Logging.debugToConsole("[getQualityBonus] Low: " + low);
        Logging.debugToConsole("[getQualityBonus] Medium Weight: " + mediumWeight);
        Logging.debugToConsole("[getQualityBonus] High Weight: " + highWeight);
        Logging.debugToConsole("[getQualityBonus] Quality Bonus: " + ((medium * mediumWeight) + (high * highWeight)));

        return (medium * mediumWeight) + (high * highWeight);

    }

    public float getLevelBonus(int level) {

        return (float) level / 100;

    }

    public BaseItemConfiguration getCraftedItem() {
        return craftedItem;
    }
}
