package de.wwiser.itemfilter.shared;

import lombok.experimental.UtilityClass;
import de.wwiser.itemfilter.core.HopperFilter;
import de.wwiser.itemfilter.infra.HopperFilterEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Objects;

@UtilityClass
public class HopperFilterMapper {

    public HopperFilterEntity toEntity(HopperFilter domainModel) {
        HopperFilterEntity entity = new HopperFilterEntity();
        entity.setId(domainModel.getId());

        try {
            entity.setItems(ItemStackSerDe.serializeItemStacks(domainModel.getItems()));
        } catch (IOException e) {
            throw new RuntimeException("failed to serialize itemstack", e);
        }

        entity.setInverted(domainModel.isInverted());

        Location location = domainModel.getLocation();
        entity.setWorldId(Objects.requireNonNull(location.getWorld()).getUID());
        entity.setCoordinateX(location.getX());
        entity.setCoordinateY(location.getY());
        entity.setCoordinateZ(location.getZ());

        entity.setEnabled(domainModel.isEnabled());

        return entity;
    }

    public HopperFilter toDomain(HopperFilterEntity entity) {
        ItemStack[] items;
        try {
            items = ItemStackSerDe.deserializeItemStacks(entity.getItems());
        } catch (Exception e) {
            throw new RuntimeException("failed to deserialize itemstack", e);
        }

        Location location = new Location(
                Bukkit.getWorld(entity.getWorldId()),
                entity.getCoordinateX(),
                entity.getCoordinateY(),
                entity.getCoordinateZ()
        );

        return HopperFilter.builder()
                .id(entity.getId())
                .items(items)
                .inverted(entity.isInverted())
                .location(location)
                .enabled(entity.isEnabled())
                .build();
    }

}
