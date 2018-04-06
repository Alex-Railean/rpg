package com.endava.rpg.persistence.services.utils.constants;

public enum BranchAttribute {
    TECHNOLOGIES("Technologies", "technologies", "/resources/img/technologies.jpg"),

    ASPECTS("Aspects", "aspects", "/resources/img/aspects.jpg");

    public final String LINK;

    public final String NAME;

    public final String URL;

    BranchAttribute(String name, String link, String URL) {
        this.NAME = name;
        this.LINK = link;
        this.URL = URL;
    }
}
