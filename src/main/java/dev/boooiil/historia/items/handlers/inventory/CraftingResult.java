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

/**
 * <p>
 * The CraftingResult class is responsible for managing the result of a crafting
 * operation within the Historia plugin.
 * </p>
 * <p>
 * CraftingResult provides methods to generate random modifiers for crafted
 * items, such as armor and weapons, based on the materials used in the crafting
 * operation.
 * </p>
 */
public class CraftingResult {

    private final Inventory inventory;
    private final BaseItemConfiguration craftedItem;
    private final HistoriaPlayer historiaPlayer;
    private ItemStack result;

    /**
     * Constructs a CraftingResult object with the specified inventory, result
     * ItemStack, crafted item configuration, and Historia player.
     *
     * @param inventory      The inventory used in the crafting operation.
     * @param result         The result ItemStack of the crafting operation.
     * @param craftedItem    The configuration of the crafted item.
     * @param historiaPlayer The Historia player performing the crafting operation.
     */
    public CraftingResult(Inventory inventory, ItemStack result, BaseItemConfiguration craftedItem,
            HistoriaPlayer historiaPlayer) {
        this.inventory = inventory;
        this.result = result;
        this.historiaPlayer = historiaPlayer;
        this.craftedItem = craftedItem;
    }

    /**
     * Generates random modifiers for the crafted item based on the materials used
     * in
     * the crafting operation.
     */
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

    /**
     * Generates random modifiers for the crafted armor item based on the materials
     * used in the crafting operation.
     */
    private void generateArmorModifiers() {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        ArmorConfiguration armorConfiguration = (ArmorConfiguration) craftedItem;
        Armor armor = new Armor(armorConfiguration);

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());

        result = armor.getItemStack();

    }

    /**
     * Generates random modifiers for the crafted weapon item based on the materials
     * used in the crafting operation.
     */
    private void generateWeaponModifiers() {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        WeaponConfiguration weaponConfiguration = (WeaponConfiguration) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());

        Weapon weapon = new Weapon(weaponConfiguration);

        result = weapon.getItemStack();

    }

    /**
     * Gets the quality bonus for the crafted item based on the materials used in
     * the crafting operation.
     *
     * @param materials The materials used in the crafting operation.
     * @return The quality bonus for the crafted item.
     */
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

    /**
     * Gets the level bonus for the crafted item based on the level of the player
     * performing the crafting operation.
     *
     * @param level The level of the player performing the crafting operation.
     * @return The level bonus for the crafted item.
     */
    public float getLevelBonus(int level) {

        return (float) level / 100;

    }

    /**
     * Gets the result ItemStack of the crafting operation.
     *
     * @return The result ItemStack of the crafting operation.
     */
    public BaseItemConfiguration getCraftedItem() {
        return craftedItem;
    }
}
