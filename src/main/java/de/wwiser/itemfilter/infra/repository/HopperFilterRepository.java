package de.wwiser.itemfilter.infra.repository;

import de.wwiser.itemfilter.infra.HopperFilterEntity;

import java.util.List;

public interface HopperFilterRepository {

    List<HopperFilterEntity> findAll();

    void saveFilter(HopperFilterEntity hopperFilter);

    void deleteFilter(int hopperFilterId);

}
