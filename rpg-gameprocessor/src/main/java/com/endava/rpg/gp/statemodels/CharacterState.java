package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.statemodels.points.Attribute;
import com.endava.rpg.gp.util.Refreshable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterState extends State implements Refreshable {

    private final List<Attribute> ATTRIBUTES = new ArrayList<>();

    private Attribute strength = new Attribute(this);

    private Attribute agility = new Attribute(this);

    private Attribute intelligence = new Attribute(this);

    private Integer defaultEnergy = 100;

    private Long currentBattle = 0L;

    private String location;

    private Double stunResistancePercentage = 0D;

    private Integer freePoints;

    private Integer lastMovePoints = 0;

    private Integer biggestDmg = 0;

    public CharacterState() {
        getHp().setRegeneration(3);
        getMp().setRegeneration(5);
        getEnergy().setRegeneration(10);
        getEnergy().setCurrentValue(100);
        Refresher.addRefreshable(this);
    }

    @Override
    public void refresh() {
        this.biggestDmg = 0;
        this.lastMovePoints = 0;
    }

    public void addAttribute(Attribute a) {
        ATTRIBUTES.add(a);
    }

    public List<Attribute> getAttributes() {
        return ATTRIBUTES;
    }

    public Attribute getStrength() {
        return strength;
    }

    public void setStrength(Attribute strength) {
        this.strength = strength;
    }

    public Attribute getAgility() {
        return agility;
    }

    public void setAgility(Attribute agility) {
        this.agility = agility;
    }

    public Attribute getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Attribute intelligence) {
        this.intelligence = intelligence;
    }

    public Long getCurrentBattle() {
        return currentBattle;
    }

    public CharacterState setCurrentBattle(Long currentBattle) {
        this.currentBattle = currentBattle;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public CharacterState setLocation(String location) {
        this.location = location;
        return this;
    }

    public Integer getDefaultEnergy() {
        return defaultEnergy;
    }

    public void setDefaultEnergy(Integer defaultEnergy) {
        this.defaultEnergy = defaultEnergy;
    }

    public Double getStunResistancePercentage() {
        return stunResistancePercentage;
    }

    public void setStunResistancePercentage(Double stunResistancePercentage) {
        this.stunResistancePercentage = stunResistancePercentage;
    }

    public Integer getFreePoints() {
        return freePoints;
    }

    public CharacterState setFreePoints(Integer freePoints) {
        this.freePoints = freePoints;
        return this;
    }

    public Integer getLastMovePoints() {
        return lastMovePoints;
    }

    public void setLastMovePoints(Integer lastMovePoints) {
        this.lastMovePoints = lastMovePoints;
    }

    public Integer getBiggestDmg() {
        return biggestDmg;
    }

    public void setBiggestDmg(Integer biggestDmg) {
        this.biggestDmg = biggestDmg;
    }
}
