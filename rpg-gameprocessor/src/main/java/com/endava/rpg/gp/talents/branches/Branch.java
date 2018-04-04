package com.endava.rpg.gp.talents.branches;

import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.Character;

import java.util.ArrayList;
import java.util.List;

public abstract class Branch {
    protected List<Talent> talentsOfBranch = new ArrayList<>();

    private String name;

    private String linkName;

    private String URL;

    public abstract void define(Character character);

    public abstract void create(Character character);

    public abstract void addAvailableSpells(Character character);

    public void addTalent(Talent t) {
        talentsOfBranch.add(t);
    }

    public void affect() {
        talentsOfBranch.forEach(Talent::affect);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public List<Talent> getTalents() {
        return talentsOfBranch;
    }
}
