package de.wwiser.itemfilter.presentation.listener;

import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.presentation.HopperFilterInventoryFixtures;
import de.wwiser.itemfilter.presentation.HopperFilterViewStateRegistry;
import lombok.AllArgsConstructor;
import de.wwiser.itemfilter.core.HopperFilter;
import de.wwiser.itemfilter.shared.exception.PlayerInFilterInventoryWithoutStateException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

@AllArgsConstructor
public class InventoryCloseListener implements Listener {

    private final HopperFilterService hopperFilterService;
    private final HopperFilterViewStateRegistry hopperFilterViewStateRegistry;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryView view = event.getView();

        if (!HopperFilterInventoryFixtures.INVENTORY_TITLE.equals(view.getTitle())) {
            return;
        }

        Inventory inventory = view.getTopInventory();

        ItemStack[] itemStacks = HopperFilterInventoryFixtures.SLOTS_ITEMS.stream().map(inventory::getItem)
                .filter(Predicate.not(Predicate.isEqual(HopperFilterInventoryFixtures.ITEM_LOCKED)))
                .toArray(ItemStack[]::new);

        UUID playerId = event.getPlayer().getUniqueId();
        HopperFilter hopperFilter = hopperFilterViewStateRegistry.getFilterInView(playerId)
                .orElseThrow(PlayerInFilterInventoryWithoutStateException::new);

        if (!Arrays.equals(itemStacks, hopperFilter.getItems())) {
            hopperFilter.setItems(itemStacks);
            hopperFilterService.saveFilter(hopperFilter);
        }

        hopperFilterViewStateRegistry.endView(playerId);
    }

}
