package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.factories.EffectFactory;
import com.endava.rpg.gp.battle.spells.effects.passive.Passive;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Displayed;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Shield;
import com.endava.rpg.gp.statemodels.points.Point;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.persistence.models.Spell;

import java.util.*;
import java.util.stream.Collectors;

public abstract class State {

    private final List<Point> POINTS = new ArrayList<>();

    private final List<Spell> SPELLS = new ArrayList<>();

    private Set<Effect> effects = new HashSet<>();

    private Set<Passive> passives = new HashSet<>();

    private String name;

    private Integer level;

    private Point hp = new Point(this);

    private Point mp = new Point(this);

    private Point energy = new Point(this);

    private Double criticalDmgCoefficient = 1.8;

    private Double stunResistance = 0D;

    private int stun = 0;

    public void useEffects() {
        passives.forEach(Passive::affectTarget);
        effects.forEach(Effect::decreaseDuration);
        effects.forEach(Effect::affectTarget);
        removeEffects();
    }

    public Effect addEffect(State target, Spell s) {
        Effect e = EffectFactory.createEffect(target, s);
        Effect sameEffect = effects.stream()
                .filter(se -> se.equals(e))
                .findFirst()
                .orElse(null);

        if (sameEffect != null) return e.refreshDuration();

        e.addTo(effects);
        return e;
    }

    public void addEffect(Effect e) {
        Effect sameEffect = effects.stream()
                .filter(se -> se.equals(e))
                .findFirst()
                .orElse(null);

        if (sameEffect != null) {
            e.refreshDuration();
        } else {
            e.addTo(effects);
        }
    }

    public void addPassive(Passive p) {
        Passive samePassive = passives.stream()
                .filter(se -> se.equals(p))
                .findFirst()
                .orElse(null);

        if (samePassive != null) passives.remove(samePassive);

        passives.add(p);
    }

    private void removeEffects() {

        List<Effect> toRemove = effects.stream()
                .map(e -> e.getRemainingDuration() != -1 && e.getRemainingDuration() <= 0 ? e.remove() : e)
                .filter(Effect::isToRemove)
                .map(e -> e.setToRemove(false))
                .collect(Collectors.toList());

        effects.removeAll(toRemove);
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
        List<Shield> shields = this.effects.stream()
                .filter(e -> e instanceof Shield)
                .map(e -> (Shield) e)
                .collect(Collectors.toList());

        return shields.size() != 0 ?
                shields.get(ProcessorUtil.getRandomInt(0, shields.size())) : null;
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

    public Set<Effect> getDisplayEffects() {
        return effects.stream()
                .filter(e -> e instanceof Displayed)
                .collect(Collectors.toSet());
    }

    public Double getStunResistance() {
        return stunResistance;
    }

    public void setStunResistance(Double stunResistance) {
        this.stunResistance = stunResistance;
    }

    public int getStun() {
        return stun;
    }

    public void setStun(int stun) {
        if (stunResistance < new Random().nextInt(101)) this.stun = stun;
    }

    public boolean isStunned() {
        return stun > 0;
    }

    public void tickStun() {
        if (stun > 0) {
            stun -= 1;
        }
    }

    public boolean containsPassive(Passive p){
        return passives.contains(p);
    }

    public boolean removePassive(Passive p){
        return passives.remove(p);
    }
}
