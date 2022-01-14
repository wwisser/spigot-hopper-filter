package de.wwiser.itemfilter.presentation.factory;

import de.wwiser.itemfilter.core.Rank;
import de.wwiser.itemfilter.core.HopperFilter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static de.wwiser.itemfilter.presentation.HopperFilterInventoryFixtures.*;

public class HopperFilterInventoryFactory {

    public Inventory createFilterInventory(HopperFilter hopperFilter, Player player) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_TITLE);

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            inventory.setItem(i, ITEM_BACKGROUND);
        }

        inventory.setItem(SLOT_MODE, hopperFilter.isInverted() ? ITEM_FILTER_INVERTED : ITEM_FILTER);
        inventory.setItem(SLOT_ACTIVATION, hopperFilter.isEnabled() ? ITEM_ACTION_DISABLE : ITEM_ACTION_ENABLE);

        ItemStack[] items = hopperFilter.getItems();

        int index = 0;
        for (int slot : SLOTS_ITEMS) {
            ItemStack itemToPlace = null;
            if (items != null && (items.length - 1) >= index) {
                itemToPlace = items[index];
            }

            inventory.setItem(slot, itemToPlace);
            index++;
        }

        if (!player.hasPermission(Rank.PRO.getPermission())) {
            inventory.setItem(SLOT_ITEM_PRO, ITEM_LOCKED);
        }

        return inventory;
    }

}
