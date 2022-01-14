package de.wwiser.itemfilter.presentation.evaluator;

import de.wwiser.itemfilter.core.HopperFilter;
import org.bukkit.inventory.ItemStack;

public interface HopperFilterEvaluator {

    boolean isFiltered(HopperFilter filter, ItemStack itemToEvaluate);

}
