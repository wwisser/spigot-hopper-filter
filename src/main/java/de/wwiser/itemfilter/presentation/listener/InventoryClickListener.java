package de.wwiser.itemfilter.presentation.listener;

import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.presentation.HopperFilterInventoryFixtures;
import de.wwiser.itemfilter.presentation.HopperFilterViewStateRegistry;
import lombok.AllArgsConstructor;
import de.wwiser.itemfilter.core.HopperFilter;
import de.wwiser.itemfilter.shared.exception.PlayerInFilterInventoryWithoutStateException;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import static de.wwiser.itemfilter.shared.MessageFixtures.*;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private static final String SOUND_MODE_CHANGED = "minecraft:entity.player.levelup";

    private final HopperFilterService hopperFilterService;
    private final HopperFilterViewStateRegistry hopperFilterViewStateRegistry;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (!HopperFilterInventoryFixtures.INVENTORY_TITLE.equals(view.getTitle()) || isPlayerInventory(event)) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem != null && currentItem.equals(HopperFilterInventoryFixtures.ITEM_LOCKED)) {
            event.setCancelled(true);
            return;
        }

        int slot = event.getSlot();
        if (HopperFilterInventoryFixtures.SLOTS_ITEMS.contains(slot)) {
            return;
        }

        event.setCancelled(true);

        if (currentItem == null) {
            return;
        }

        Inventory inventory = event.getInventory();

        HumanEntity whoClicked = event.getWhoClicked();
        HopperFilter hopperFilter = hopperFilterViewStateRegistry.getFilterInView(whoClicked.getUniqueId())
                .orElseThrow(PlayerInFilterInventoryWithoutStateException::new);

        handleFilterToggle(currentItem, slot, inventory, hopperFilter);
        handleFilterInversion(currentItem, slot, inventory, hopperFilter, whoClicked);
    }

    private void handleFilterInversion(
            ItemStack currentItem,
            int slot,
            Inventory inventory,
            HopperFilter hopperFilter,
            HumanEntity whoClicked
    ) {
        if (!isFilter(currentItem)) {
            return;
        }

        boolean inverted = isFilterInverted(currentItem);
        inventory.setItem(slot, inverted ? HopperFilterInventoryFixtures.ITEM_FILTER : HopperFilterInventoryFixtures.ITEM_FILTER_INVERTED);

        boolean invertedNew = !inverted;
        hopperFilter.setInverted(invertedNew);
        hopperFilterService.saveFilter(hopperFilter);

        whoClicked.sendMessage(String.format(MESSAGE_CHANGED_MODE, invertedNew ? MODE_INVERTED : MODE_DEFAULT));
        whoClicked.closeInventory();
        ((Player) whoClicked).playSound(whoClicked.getLocation(), SOUND_MODE_CHANGED, 5, 1);
    }

    private boolean isFilter(ItemStack item) {
        return item.equals(HopperFilterInventoryFixtures.ITEM_FILTER) || item.equals(HopperFilterInventoryFixtures.ITEM_FILTER_INVERTED);
    }

    private boolean isFilterInverted(ItemStack item) {
        return item.equals(HopperFilterInventoryFixtures.ITEM_FILTER_INVERTED);
    }

    private void handleFilterToggle(ItemStack currentItem, int slot, Inventory inventory, HopperFilter hopperFilter) {
        if (!isToggleItem(currentItem)) {
            return;
        }

        boolean enabled = isDisabledItem(currentItem);
        inventory.setItem(slot, enabled ? HopperFilterInventoryFixtures.ITEM_ACTION_ENABLE : HopperFilterInventoryFixtures.ITEM_ACTION_DISABLE);
        hopperFilter.setEnabled(!enabled);
        hopperFilterService.saveFilter(hopperFilter);
    }

    private boolean isToggleItem(ItemStack item) {
        return item.equals(HopperFilterInventoryFixtures.ITEM_ACTION_DISABLE) || item.equals(HopperFilterInventoryFixtures.ITEM_ACTION_ENABLE);
    }

    private boolean isDisabledItem(ItemStack item) {
        return item.equals(HopperFilterInventoryFixtures.ITEM_ACTION_DISABLE);
    }

    private boolean isPlayerInventory(InventoryClickEvent event) {
        return event.getRawSlot() >= event.getView().getTopInventory().getSize();
    }

}
