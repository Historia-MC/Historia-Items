package dev.boooiil.historia.items.configuration.item.data;

import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.items.configuration.item.components.IItemComponent;

public interface IItemData {

    IItemComponent getItemComponent();

    public ItemStack apply();

    public ItemStack applyToDataContainer();

    public ItemStack applyToLorePlaceholder();

    public String getConfigurationId();

    public IItemData fromItemStack(ItemStack item);

}
