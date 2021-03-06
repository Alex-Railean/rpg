package com.endava.rpg.gp.talents.talents;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.Character;

import java.util.ArrayList;
import java.util.List;

public abstract class Talent {

    private List<Talent> dependency = new ArrayList<>();

    private String name;

    private String linkName;

    private String description;

    private int points;

    private int limit;

    private String URL;

    protected Talent(Branch branch, String name, String link, String URL) {
        this.name = name;
        this.linkName = link;
        this.URL = URL;
        branch.addTalent(this);
    }

    public abstract void affect();

    public abstract void define(Character character);

    public void addDependency(Talent t) {
        dependency.add(t);
    }

    public boolean isAvailable() {
        return dependency.size() == 0 || dependency.stream().allMatch(d -> d.getPoints() == d.getLimit());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
