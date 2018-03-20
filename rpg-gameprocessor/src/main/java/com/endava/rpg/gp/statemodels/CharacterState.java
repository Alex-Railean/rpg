package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.statemodels.points.Attribute;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterState extends State {

    private List<Attribute> attributes = new ArrayList<>();

    private Attribute strength = new Attribute(this);

    private Attribute agility = new Attribute(this);

    private Attribute intelligence = new Attribute(this);

    private Integer defaultEnergy = 100;

    private Long currentBattle;

    private String location;

    private Double stunResistancePercentage = 0D;

    private Integer freePoints;

    public CharacterState() {
        getHp().setRegeneration(3);
        getMp().setRegeneration(5);
        getEnergy().setRegeneration(10);
        getEnergy().setCurrentValue(100);
    }

    public void addAttribute(Attribute a) {
        attributes.add(a);
    }

    public List<Attribute> getAttributes() {
        return attributes;
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
}
