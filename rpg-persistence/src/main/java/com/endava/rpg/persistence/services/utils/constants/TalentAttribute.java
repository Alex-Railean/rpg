package com.endava.rpg.persistence.services.utils.constants;

public enum TalentAttribute {
    // Tech
    EXO_SPINE("Exo-spine", "exospine", "/resources/img/exo-spine.jpg"),
    MUSCLE_STIMULANTS("Muscle Stimulants", "musclestimulants", "/resources/img/muscle-stimulants.jpg"),

    // Aspects
    CURSED_BLADE("Cursed Blade", "cursedblade", "/resources/img/cursed-blade.jpg"),
    COURAGE("Courage", "courage", "/resources/img/courage.jpg"),
    PLAGUE_AURA("Plague Aura", "plagueaura", "/resources/img/plague-aura.jpg");

    public final String NAME;

    public final String LINK;

    public final String URL;

    TalentAttribute(String name, String link, String URL) {
        this.NAME = name;
        this.LINK = link;
        this.URL = URL;
    }
}
