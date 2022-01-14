package de.wwiser.itemfilter.presentation.listener;

import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.presentation.evaluator.HopperFilterEvaluator;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor
public class InventoryPickupItemListener implements Listener {

    private final HopperFilterService hopperFilterService;
    private final HopperFilterEvaluator hopperFilterEvaluator;

    @EventHandler
    public void onInventoryPickup(InventoryPickupItemEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getType() != InventoryType.HOPPER) {
            return;
        }

        Hopper hopper = (Hopper) inventory.getHolder();
        Location location = Objects.requireNonNull(hopper).getLocation();

        hopperFilterService.findFilterByLocation(location).ifPresent(hopperFilter -> {
            if (!hopperFilter.isEnabled()) {
                return;
            }

            ItemStack item = event.getItem().getItemStack();
            event.setCancelled(hopperFilterEvaluator.isFiltered(hopperFilter, item));
        });
    }

}
