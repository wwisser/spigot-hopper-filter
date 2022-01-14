package de.wwiser.itemfilter.presentation.listener;

import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.presentation.HopperFilterViewStateRegistry;
import de.wwiser.itemfilter.presentation.factory.HopperFilterInventoryFactory;
import lombok.AllArgsConstructor;
import de.wwiser.itemfilter.core.HopperFilter;
import de.wwiser.itemfilter.shared.MessageFixtures;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@AllArgsConstructor
public class PlayerInteractListener implements Listener {

    private final HopperFilterService hopperFilterService;
    private final HopperFilterViewStateRegistry hopperFilterViewStateRegistry;
    private final HopperFilterInventoryFactory hopperFilterInventoryFactory;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking() || !isInteractingWithItemHopper(event)) {
            return;
        }

        event.setCancelled(true);

        Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();

        HopperFilter hopperFilter = hopperFilterService.findFilterByLocation(location)
                .orElseGet(() -> newHopperFilter(location));

        if (hopperFilterViewStateRegistry.isBeingViewed(hopperFilter)) {
            player.sendMessage(MessageFixtures.MESSAGE_ALREADY_IN_VIEW);
            return;
        }

        hopperFilterViewStateRegistry.startView(player.getUniqueId(), hopperFilter);

        Inventory filterInventory = hopperFilterInventoryFactory.createFilterInventory(hopperFilter, player);
        player.openInventory(filterInventory);
    }

    private static HopperFilter newHopperFilter(Location location) {
        return HopperFilter.builder().enabled(true).inverted(false).items(new ItemStack[]{}).location(location).build();
    }

    private static boolean isInteractingWithItemHopper(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return false;
        }

        Block clickedBlock = Objects.requireNonNull(event.getClickedBlock());
        return clickedBlock.getBlockData().getMaterial() == Material.HOPPER;
    }

}
