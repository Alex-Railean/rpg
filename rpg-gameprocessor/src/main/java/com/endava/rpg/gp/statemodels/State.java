package com.endava.rpg.gp.statemodels;

import com.endava.rpg.persistence.models.Spell;

public class State {

    private Integer hpRegeneration;

    private Integer mpRegeneration;

    private Integer energy;

    private Integer energyRegeneration;

    private Integer shieldPoints = 0;

    private Double criticalDmgCoefficient = 1.8;

    private Integer hp;

    private Integer currentHp;

    private Integer mp;

    private Integer currentMp;

    private Integer currentEnergy;

    private Spell spell_1;

    private Spell spell_2;

    private Spell spell_3;

    public Integer getHp() {
        return hp;
    }

    public State setHp(Integer hp) {
        this.hp = hp;
        return this;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public State setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
        return this;
    }

    public Integer getHpRegeneration() {
        return hpRegeneration;
    }

    public State setHpRegeneration(Integer hpRegeneration) {
        this.hpRegeneration = hpRegeneration;
        return this;
    }

    public Integer getMp() {
        return mp;
    }

    public State setMp(Integer mp) {
        this.mp = mp;
        return this;
    }

    public Integer getCurrentMp() {
        return currentMp;
    }

    public State setCurrentMp(Integer currentMp) {
        this.currentMp = currentMp;
        return this;
    }

    public Integer getMpRegeneration() {
        return mpRegeneration;
    }

    public State setMpRegeneration(Integer mpRegeneration) {
        this.mpRegeneration = mpRegeneration;
        return this;
    }

    public Integer getEnergy() {
        return energy;
    }

    public State setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public Integer getCurrentEnergy() {
        return currentEnergy;
    }

    public State setCurrentEnergy(Integer currentEnergy) {
        this.currentEnergy = currentEnergy;
        return this;
    }

    public Integer getEnergyRegeneration() {
        return energyRegeneration;
    }

    public State setEnergyRegeneration(Integer energyRegeneration) {
        this.energyRegeneration = energyRegeneration;
        return this;
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
