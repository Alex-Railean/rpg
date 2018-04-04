package com.endava.rpg.persistence.services.utils.constants;

public enum BranchAttribute {
    TECHNOLOGIES("Technologies", "technologies");

    public final String LINK;

    public final String NAME;

    BranchAttribute(String name, String link) {
        this.NAME = name;
        this.LINK = link;
    }
}