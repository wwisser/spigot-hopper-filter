package de.wwiser.itemfilter.shared;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageFixtures {

    private static final String PREFIX = "§a§lFilter §8» §7";

    public String MODE_DEFAULT = "Erlaubte Items";
    public String MODE_INVERTED = "Verbotene Items";

    public String MESSAGE_CHANGED_MODE = PREFIX + "Du hast erfolgreich den Filtermodus §e%s §7gewählt.";
    public String MESSAGE_ALREADY_IN_VIEW = "§cDieser Item Filter wird aktuell von einem anderen Spieler bearbeitet.";

}
