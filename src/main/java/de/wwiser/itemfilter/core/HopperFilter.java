package de.wwiser.itemfilter.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HopperFilter {
    Integer id;
    ItemStack[] items;
    boolean inverted;
    Location location;
    boolean enabled;
}
