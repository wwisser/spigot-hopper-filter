package de.wwiser.itemfilter.presentation.listener;

import lombok.AllArgsConstructor;
import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.presentation.HopperFilterViewStateRegistry;
import de.wwiser.itemfilter.shared.MessageFixtures;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
public class BlockBreakListener implements Listener {

    private final HopperFilterService hopperFilterService;
    private final HopperFilterViewStateRegistry hopperFilterViewStateRegistry;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        World world = Objects.requireNonNull(location.getWorld());

        hopperFilterService.findFilterByLocation(location).ifPresent(hopperFilter -> {
            if (hopperFilterViewStateRegistry.isBeingViewed(hopperFilter)) {
                event.getPlayer().sendMessage(MessageFixtures.MESSAGE_ALREADY_IN_VIEW);
                event.setCancelled(true);
                return;
            }

            ItemStack[] items = hopperFilter.getItems();
            hopperFilterService.deleteFilter(hopperFilter);

            if (items != null)
                Stream.of(items)
                        .filter(Objects::nonNull)
                        .forEach(i -> world.dropItemNaturally(location, i));
        });
    }

}
