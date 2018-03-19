package com.endava.rpg.gp.statemodels;

import com.endava.rpg.gp.statemodels.points.Attribute;
import com.endava.rpg.persistence.models.Spell;
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

    private Spell spell_4;

    private Spell spell_5;

    private Spell spell_6;

    private Spell spell_7;

    private Spell spell_8;

    private Spell spell_9;

    private Spell spell_10;

    private Spell spell_11;

    private Spell spell_12;

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

    public void addAttribute(Attribute a){
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

    public Spell getSpell_4() {
        return spell_4;
    }

    public CharacterState setSpell_4(Spell spell_4) {
        this.spell_4 = spell_4;
        return this;
    }

    public Spell getSpell_5() {
        return spell_5;
    }

    public CharacterState setSpell_5(Spell spell_5) {
        this.spell_5 = spell_5;
        return this;
    }

    public Spell getSpell_6() {
        return spell_6;
    }

    public CharacterState setSpell_6(Spell spell_6) {
        this.spell_6 = spell_6;
        return this;
    }

    public Spell getSpell_7() {
        return spell_7;
    }

    public CharacterState setSpell_7(Spell spell_7) {
        this.spell_7 = spell_7;
        return this;
    }

    public Spell getSpell_8() {
        return spell_8;
    }

    public CharacterState setSpell_8(Spell spell_8) {
        this.spell_8 = spell_8;
        return this;
    }

    public Spell getSpell_9() {
        return spell_9;
    }

    public CharacterState setSpell_9(Spell spell_9) {
        this.spell_9 = spell_9;
        return this;
    }

    public Spell getSpell_10() {
        return spell_10;
    }

    public CharacterState setSpell_10(Spell spell_10) {
        this.spell_10 = spell_10;
        return this;
    }

    public Spell getSpell_11() {
        return spell_11;
    }

    public CharacterState setSpell_11(Spell spell_11) {
        this.spell_11 = spell_11;
        return this;
    }

    public Spell getSpell_12() {
        return spell_12;
    }

    public CharacterState setSpell_12(Spell spell_12) {
        this.spell_12 = spell_12;
        return this;
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
