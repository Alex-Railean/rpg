package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.statemodels.points.Point;
import com.endava.rpg.persistence.models.Spell;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Point> points = new ArrayList<>();

    private Point hp = new Point(this);

    private Point mp = new Point(this);

    private Point energy = new Point(this);

    private Integer shieldPoints = 0;

    private Double criticalDmgCoefficient = 1.8;

    private Spell spell_1;

    private Spell spell_2;

    private Spell spell_3;

    public void addPoint(Point point){
        this.points.add(point);
    }

    public List<Point> getPoints() {
        return points;
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

    public Spell getSpell_1() {
        return spell_1;
    }

    public State setSpell_1(Spell spell_1) {
        this.spell_1 = spell_1;
        return this;
    }

    public Spell getSpell_2() {
        return spell_2;
    }

    public State setSpell_2(Spell spell_2) {
        this.spell_2 = spell_2;
        return this;
    }

    public Spell getSpell_3() {
        return spell_3;
    }

    public State setSpell_3(Spell spell_3) {
        this.spell_3 = spell_3;
        return this;
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
