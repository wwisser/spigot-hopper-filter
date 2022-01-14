package de.wwiser.itemfilter.infra;

import de.wwiser.itemfilter.core.HopperFilter;
import de.wwiser.itemfilter.core.HopperFilterService;
import de.wwiser.itemfilter.infra.repository.HopperFilterRepository;
import de.wwiser.itemfilter.shared.HopperFilterMapper;
import org.bukkit.Location;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A cached and blocking implementation of {@link HopperFilterService}.
 */
public class CachedBlockingHopperFilterService implements HopperFilterService {

    private final HopperFilterRepository hopperFilterRepository;
    private final Map<Location, HopperFilter> filtersByLocation;

    public CachedBlockingHopperFilterService(HopperFilterRepository hopperFilterRepository) {
        this.hopperFilterRepository = hopperFilterRepository;

        this.filtersByLocation = this.hopperFilterRepository.findAll()
                .stream()
                .map(HopperFilterMapper::toDomain)
                .collect(Collectors.toMap(HopperFilter::getLocation, Function.identity()));
    }

    @Override
    public Optional<HopperFilter> findFilterByLocation(Location location) {
        return Optional.ofNullable(this.filtersByLocation.get(location));
    }

    @Override
    public void saveFilter(HopperFilter hopperFilter) {
        this.filtersByLocation.put(hopperFilter.getLocation(), hopperFilter);

        HopperFilterEntity entity = HopperFilterMapper.toEntity(hopperFilter);
        this.hopperFilterRepository.saveFilter(entity);

        hopperFilter.setId(entity.getId());
    }

    @Override
    public void deleteFilter(HopperFilter hopperFilter) {
        this.filtersByLocation.values().remove(hopperFilter);
        this.hopperFilterRepository.deleteFilter(hopperFilter.getId());
    }

}
