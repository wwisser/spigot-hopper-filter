package de.wwiser.itemfilter.presentation;

import lombok.experimental.UtilityClass;
import de.wwiser.itemfilter.presentation.factory.HopperFilterItemFactory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class HopperFilterInventoryFixtures {

    public String INVENTORY_TITLE = "§8» §e§lTrichter§8-§6Filter";
    public int INVENTORY_SIZE = 9 * 5;

    public Set<Integer> SLOTS_ITEMS = IntStream.range(29, 34).boxed().collect(Collectors.toSet());

    public int SLOT_ITEM_PRO = 32;
    public int SLOT_ITEM_ALPHA = 33;

    public int SLOT_MODE = 12;
    public int SLOT_ACTIVATION = 14;

    public ItemStack ITEM_LOCKED = HopperFilterItemFactory.createLockedItem();

    public ItemStack ITEM_ACTION_DISABLE = HopperFilterItemFactory.createDisableActionItem();
    public ItemStack ITEM_ACTION_ENABLE = HopperFilterItemFactory.createEnableActionItem();

    public ItemStack ITEM_FILTER = HopperFilterItemFactory.createFilterItem();
    public ItemStack ITEM_FILTER_INVERTED = HopperFilterItemFactory.createInvertedFilterItem();

    public ItemStack ITEM_BACKGROUND = HopperFilterItemFactory.createBackgroundItem();

}
