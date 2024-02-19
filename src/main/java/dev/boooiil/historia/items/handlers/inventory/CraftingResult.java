package dev.boooiil.historia.items.handlers.inventory;

import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.experience.CraftingSources;
import dev.boooiil.historia.items.configuration.crafted.BaseItemConfiguration;
import dev.boooiil.historia.items.configuration.crafted.armor.ArmorConfiguration;
import dev.boooiil.historia.items.configuration.crafted.weapon.WeaponConfiguration;
import dev.boooiil.historia.items.handlers.inventory.prepareCraftItem.PrepareItemCraftInventoryHelper;
import dev.boooiil.historia.items.util.Logging;
import dev.boooiil.historia.items.util.NumberUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class CraftingResult {

    private final Inventory inventory;
    private final ItemStack result;
    private final BaseItemConfiguration craftedItem;
    private final HistoriaPlayer historiaPlayer;

    public CraftingResult(Inventory inventory, ItemStack result, BaseItemConfiguration craftedItem,
            HistoriaPlayer historiaPlayer) {
        this.inventory = inventory;
        this.result = result;
        this.historiaPlayer = historiaPlayer;
        this.craftedItem = craftedItem;
    }

    public void generateRandomModifiers() {

        if (craftedItem instanceof ArmorConfiguration) {

            Logging.debugToConsole("[generateRandomModifiers] Generating Armor Modifiers");

            generateArmorModifiers();

            historiaPlayer.increaseExperience(CraftingSources.ARMOR_CRAFT.getKey());

        } else if (craftedItem instanceof WeaponConfiguration) {

            Logging.debugToConsole("[generateRandomModifiers] Generating Weapon Modifiers");

            generateWeaponModifiers();

            historiaPlayer.increaseExperience(CraftingSources.WEAPON_CRAFT.getKey());

        }

        else {

            Logging.debugToConsole("[generateRandomModifiers] Was other item.");

            historiaPlayer.increaseExperience(CraftingSources.OTHER_CRAFT.getKey());

        }

    }

    private void generateArmorModifiers() {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        ArmorConfiguration armor = (ArmorConfiguration) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());
        int rolledDurability = armor.getMaxDurabilityValue();
        double rolledArmor = armor.getRandomDefenseValue();

        ItemMeta meta = result.getItemMeta();
        Damageable damageable = (Damageable) meta;
        AttributeModifier armorModifier = new AttributeModifier(
                UUID.randomUUID(), "Attack Speed", rolledArmor, AttributeModifier.Operation.ADD_NUMBER,
                result.getType().getEquipmentSlot());

        int adjustedDurability = result.getType().getMaxDurability() - rolledDurability;

        List<String> lore = List.of(
                "",
                "§7Class - " + armor.getWeight(),
                "",
                "§7Armor - " + NumberUtils.roundDouble(rolledArmor, 2),
                "§7Weight - " + armor.getWeightValue());

        damageable.setDamage(adjustedDurability);
        meta.setLore(lore);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);

        Logging.debugToConsole("[generateArmorModifiers] Durability: " + rolledDurability);
        Logging.debugToConsole("[generateArmorModifiers] Durability: " + adjustedDurability);

        result.setItemMeta(meta);

    }

    private void generateWeaponModifiers() {

        PrepareItemCraftInventoryHelper inspector = new PrepareItemCraftInventoryHelper(inventory.getContents());
        List<String> allMaterials = inspector.getFullMaterials();
        WeaponConfiguration weapon = (WeaponConfiguration) craftedItem;

        float qualityBonus = getQualityBonus(allMaterials);
        float levelBonus = getLevelBonus(historiaPlayer.getLevel());
        int rolledDurability = weapon.getRandomDurabilityValue();
        double rolledDamage = weapon.getDamageRandomValue();
        double rolledAttackSpeed = weapon.getSpeedRandomValue();
        double rolledKnockback = weapon.getKnockbackRandomValue();
        double rolledSweeping = weapon.getSweepRandomValue();

        ItemMeta meta = result.getItemMeta();
        Damageable damageable = (Damageable) meta;
        AttributeModifier damageModifier = new AttributeModifier(UUID.randomUUID(), "Damage", rolledDamage,
                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "Attack Speed",
                rolledAttackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        int adjustedDurability = result.getType().getMaxDurability() - rolledDurability;

        List<String> lore = List.of(
                "",
                "§7Class - " + weapon.getWeight(),
                "",
                "§7Damage - " + NumberUtils.roundDouble(rolledDamage, 2),
                "§7Attack Speed - " + NumberUtils.roundDouble(rolledAttackSpeed, 2),
                "§7Knockback - " + NumberUtils.roundDouble(rolledKnockback, 2),
                "§7Sweeping - " + NumberUtils.roundDouble(rolledSweeping, 2),
                "§7Weight - " + weapon.getWeightValue());

        damageable.setDamage(adjustedDurability);
        meta.setLore(lore);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);

        Logging.debugToConsole("[generateArmorModifiers] Durability: " + rolledDurability);
        Logging.debugToConsole("[generateArmorModifiers] Adjusted Durability: " + adjustedDurability);

        result.setItemMeta(meta);

    }

    public float getQualityBonus(List<String> materials) {

        int high = 0;
        int medium = 0;
        int low = 0;
        int complexity = materials.size();
        float mediumWeight = 50 / (float) complexity;
        float highWeight = 100 / (float) complexity;

        for (String material : materials) {

            if (material.contains("HIGH")) {

                high++;

            } else if (material.contains("MEDIUM")) {

                medium++;

            } else if (material.contains("LOW")) {

                low++;

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
