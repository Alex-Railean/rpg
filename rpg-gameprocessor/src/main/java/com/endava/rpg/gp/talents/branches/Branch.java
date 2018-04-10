package com.endava.rpg.gp.talents.branches;

import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Branch {
    protected final PersistenceService PS;

    private List<Talent> talentsOfBranch = new ArrayList<>();

    private String name;

    private String linkName;

    private String URL;

    protected Branch(PersistenceService ps, String name, String linkName, String URL) {
        this.name = name;
        this.linkName = linkName;
        this.URL = URL;
        this.PS = ps;
    }

    public void define(Character character) {
        talentsOfBranch.forEach(t -> t.define(character));
    }

    public void addAvailableSpells(Character character) {
        BranchEntity branch = getBranchEntity(character);
        List<Spell> branchSpells = getBranchSpells();
        List<Spell> available = character.getAvailableSpells();

        branchSpells = branchSpells.stream()
                .filter(t -> t.getRequired() <= branch.getTotalPoints())
                .filter(t -> available.stream().noneMatch(a -> a.getSpellId().equals(t.getSpellId())))
                .collect(Collectors.toList());

        available.addAll(branchSpells);

        PS.updateCharacter(character);
    }

    protected abstract BranchEntity getBranchEntity(Character c);

    protected abstract List<Spell> getBranchSpells();

    public abstract void create(Character character);

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
