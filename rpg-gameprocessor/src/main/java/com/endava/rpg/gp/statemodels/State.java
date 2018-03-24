package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.statemodels.points.Point;
import com.endava.rpg.persistence.models.Spell;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    private List<Point> points = new ArrayList<>();

    private List<Spell> spells = new ArrayList<>();

    private String name;

    private Integer level;

    private Point hp = new Point(this);

    private Point mp = new Point(this);

    private Point energy = new Point(this);

    private Integer shieldPoints = 0;

    private Double criticalDmgCoefficient = 1.8;

    public List<Spell> getSpells() {
        return spells;
    }

    public Spell getSpell(int i) {
        return spells.get(i);
    }

    public State setSpell(int i, Spell s) {
        if (spells.size() <= i) {
            spells.add(i, s);
        } else {
            spells.set(i, s);
        }
        return this;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public State setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public State setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Point getHp() {
        return hp;
    }

    public void setHp(Point hp) {
        this.hp = hp;
    }

    public Point getMp() {
        return mp;
    }

    public void setMp(Point mp) {
        this.mp = mp;
    }

    public Point getEnergy() {
        return energy;
    }

    public void setEnergy(Point energy) {
        this.energy = energy;
    }

    public Integer getShieldPoints() {
        return shieldPoints;
    }

    public State setShieldPoints(Integer shieldPoints) {
        this.shieldPoints = shieldPoints;
        return this;
    }

    public Double getCriticalDmgCoefficient() {
        return criticalDmgCoefficient;
    }

    public State setCriticalDmgCoefficient(Double criticalDmgCoefficient) {
        this.criticalDmgCoefficient = criticalDmgCoefficient;
        return this;
    }
}
