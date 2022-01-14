package de.wwiser.itemfilter.shared.exception;

public class PlayerInFilterInventoryWithoutStateException extends IllegalStateException {
    public PlayerInFilterInventoryWithoutStateException() {
        super("player in filter inventory without state");
    }
}