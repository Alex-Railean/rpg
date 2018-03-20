package com.endava.rpg.gp.talents.constants;

public enum TalentAttribute {
    EXO_SPINE("Exo-spine", "exospine"),
    MUSCLE_STIMULANTS("Muscle Stimulants", "musclestimulants");

    public final String LINK;

    public final String NAME;

    TalentAttribute(String name, String link) {
        this.NAME = name;
        this.LINK = link;
    }
}
