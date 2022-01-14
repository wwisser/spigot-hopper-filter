package de.wwiser.itemfilter.presentation.evaluator;

import de.wwiser.itemfilter.core.HopperFilter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class HopperFilterEvaluatorImpl implements HopperFilterEvaluator {

    @Override
    public boolean isFiltered(HopperFilter filter, ItemStack item) {
        List<Material> materials = Stream.of(filter.getItems())
                .filter(Objects::nonNull)
                .map(ItemStack::getType)
                .toList();

        if (!filter.isInverted() && !materials.contains(item.getType())) {
            return true;
        }

        return filter.isInverted() && materials.contains(item.getType());
    }

}
