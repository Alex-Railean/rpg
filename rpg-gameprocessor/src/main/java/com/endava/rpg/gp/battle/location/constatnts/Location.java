package com.endava.rpg.gp.battle.location.constatnts;

public enum Location {
    HUNGRY_FOREST("hungry-forest", "locations/hungry-forest");

    public final String NAME;

    public final String VIEW;

    Location(String name, String view) {
        this.NAME = name;
        this.VIEW = view;
    }
}
