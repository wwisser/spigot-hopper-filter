package de.wwiser.itemfilter.presentation.factory;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class HopperFilterItemFactory {

    public ItemStack createEnableActionItem() {
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        setNameAndLore(
                itemStack,
                "§aAnschalten",
                List.of(System.lineSeparator(), "§7Dieser Filter ist aktuell §cdeaktiviert§7.")
        );

        return itemStack;
    }

    public ItemStack createDisableActionItem() {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        setNameAndLore(
                itemStack,
                "§cAbschalten",
                List.of(System.lineSeparator(), "§7Dieser Filter ist aktuell §aaktiviert§7.")
        );

        return itemStack;
    }

    public ItemStack createLockedItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_BARS);
        var lore = List.of(
                "§7Du kannst diesen Slot durch",
                "§7den Kauf eines Ranges freischalten."
        );

        setNameAndLore(itemStack, "§cNicht freigeschaltet", lore);
        return itemStack;
    }

    public ItemStack createFilterItem() {
        ItemStack itemStack = new ItemStack(Material.CHEST_MINECART);
        var lore = List.of(
                "§7Filtert nach Items, welche §aeingesammelt §7werden sollen.",
                "§7Alle anderen Items werden nicht eingesammelt.",
                System.lineSeparator(),
                "§8Klicke, um stattdessen nur Items zu verbieten"
        );

        setNameAndLore(itemStack, "§aErlaubte Items", lore);
        return itemStack;
    }

    public ItemStack createInvertedFilterItem() {
        ItemStack itemStack = new ItemStack(Material.TNT_MINECART);
        var lore = List.of(
                "§7Filtert nach Items, welche §cnicht §7eingesammelt",
                "§7werden sollen. Alle anderen Items werden eingesammelt.",
                System.lineSeparator(),
                "§8Klicke, um stattdessen nur Items zu erlauben"
        );

        setNameAndLore(itemStack, "§cVerbotene Items", lore);
        return itemStack;
    }

    public ItemStack createBackgroundItem() {
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        setNameAndLore(itemStack, "§b ", Collections.emptyList());

        return itemStack;
    }

    private void setNameAndLore(ItemStack itemStack, String displayName, List<String> lore) {
        ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
    }

}
