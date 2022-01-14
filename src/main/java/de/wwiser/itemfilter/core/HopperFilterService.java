package de.wwiser.itemfilter.core;

import org.bukkit.Location;

import java.util.Optional;


public interface HopperFilterService {

    Optional<HopperFilter> findFilterByLocation(Location location);

    void saveFilter(HopperFilter hopperFilter);

    void deleteFilter(HopperFilter hopperFilter);

}
