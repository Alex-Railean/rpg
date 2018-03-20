package com.endava.rpg.gp.services.battle.location.enums;

public enum Location {
    HUNGRY_FOREST("hungry-forest", "locations/hungry-forest");

    public final String NAME;

    public final String VIEW;

    Location(String name, String view) {
        this.NAME = name;
        this.VIEW = view;
    }
}