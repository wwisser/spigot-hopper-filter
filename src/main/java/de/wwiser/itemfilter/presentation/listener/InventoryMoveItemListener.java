package de.wwiser.itemfilter.presentation.listener;

import lombok.AllArgsConstructor;
import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.presentation.evaluator.HopperFilterEvaluator;
import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class InventoryMoveItemListener implements Listener {

    private final HopperFilterService hopperFilterService;
    private final HopperFilterEvaluator hopperFilterEvaluator;

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        for (Inventory inventory : Set.of(event.getSource(), event.getDestination())) {
            if (inventory.getType() != InventoryType.HOPPER) {
                continue;
            }

            Hopper hopper = (Hopper) inventory.getHolder();
            Location location = Objects.requireNonNull(hopper).getLocation();

            hopperFilterService.findFilterByLocation(location).ifPresent(hopperFilter -> {
                if (!hopperFilter.isEnabled()) {
                    return;
                }

                ItemStack item = event.getItem();
                event.setCancelled(hopperFilterEvaluator.isFiltered(hopperFilter, item));
            });
        }
    }

}
