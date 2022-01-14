package de.wwiser.itemfilter.presentation;

import de.wwiser.itemfilter.core.HopperFilter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Registry for players currently viewing a hopper.
 */
@NotThreadSafe
public class HopperFilterViewStateRegistry {

    private final Map<UUID, HopperFilter> hopperFilterByPlayerId = new HashMap<>();

    public boolean isBeingViewed(HopperFilter hopperFilter) {
        return this.hopperFilterByPlayerId.containsValue(hopperFilter);
    }

    public void startView(UUID playerId, HopperFilter filter) {
        hopperFilterByPlayerId.put(playerId, filter);
    }

    public Optional<HopperFilter> getFilterInView(UUID playerId) {
        return Optional.ofNullable(hopperFilterByPlayerId.get(playerId));
    }

    public void endView(UUID playerId) {
        hopperFilterByPlayerId.remove(playerId);
    }

}
