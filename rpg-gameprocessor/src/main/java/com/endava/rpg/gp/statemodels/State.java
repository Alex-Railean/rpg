package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.EffectFactory;
import com.endava.rpg.gp.battle.spells.effects.shields.Shield;
import com.endava.rpg.gp.statemodels.points.Point;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.persistence.models.Spell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class State {

    private final List<Point> POINTS = new ArrayList<>();

    private final List<Spell> SPELLS = new ArrayList<>();

    private Set<Effect> effects = new HashSet<>();

    private String name;

    private Integer level;

    private Point hp = new Point(this);

    private Point mp = new Point(this);

    private Point energy = new Point(this);

    private Double criticalDmgCoefficient = 1.8;

    public void useEffects() {
        effects.forEach(Effect::decreaseDuration);
        effects.forEach(Effect::affectTarget);
        removeEffects();
    }

    public Effect addEffect(State target, Spell s) {
        Effect e = new EffectFactory().createEffect(target, s);
        effects.remove(e);
        effects.add(e);
        return e;
    }

    private void removeEffects() {
        Set<Effect> infiniteEffects = effects.stream().filter(e -> e.getDuration() == -1).collect(Collectors.toSet());
        effects = effects.stream().filter(e -> e.getDuration() > 0).collect(Collectors.toSet());
        effects.addAll(infiniteEffects);
    }

    public List<Spell> getSpells() {
        return SPELLS;
    }

    public Spell getSpell(int i) {
        return SPELLS.get(i);
    }

    public State setSpell(int i, Spell s) {
        if (SPELLS.size() <= i) {
            SPELLS.add(i, s);
        } else {
            SPELLS.set(i, s);
        }
        return this;
    }

    public void addPoint(Point point) {
        this.POINTS.add(point);
    }

    public List<Point> getPoints() {
        return POINTS;
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
        return effects.stream()
                .filter(e -> e instanceof Shield)
                .map(e -> (Shield) e)
                .mapToInt(Shield::getPoints)
                .sum();
    }

    public Shield getRandomShield() {
        Set<Shield> shields = effects.stream()
                .filter(e -> e instanceof Shield)
                .map(e -> (Shield) e)
                .collect(Collectors.toSet());

        return shields.size() != 0 ? shields.stream()
                .skip(ProcessorUtil.getRandomInt(0, shields.size() - 1))
                .findFirst()
                .orElse(null) : null;
    }

    public Double getCriticalDmgCoefficient() {
        return criticalDmgCoefficient;
    }

    public State setCriticalDmgCoefficient(Double criticalDmgCoefficient) {
        this.criticalDmgCoefficient = criticalDmgCoefficient;
        return this;
    }

    public Set<Effect> getEffects() {
        return effects;
    }
}
