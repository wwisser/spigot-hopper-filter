package de.wwiser.itemfilter.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rank {

    DEFAULT("rank.default"),
    PRO("rank.pro");

    private final String permission;

}
