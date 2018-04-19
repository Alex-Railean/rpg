package com.endava.rpg.persistence.services.utils.constants;

public enum TalentAttribute {
    // Tech
    EXO_SPINE("Exo-spine", "exospine"),
    MUSCLE_STIMULANTS("Muscle Stimulants", "musclestimulants"),

    // Aspects
    CURSED_BLADE("Cursed Blade", "cursedblade"),
    COURAGE("Courage", "courage");

    public final String NAME;

    public final String LINK;

    TalentAttribute(String name, String link) {
        this.NAME = name;
        this.LINK = link;
    }
}
