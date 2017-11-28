package com.endava.rpg.gp.statemodels;

import com.endava.rpg.persistence.models.Spell;

public class CreepState {
    private String creepName;

    private Integer creepLevel;

    private Integer hp;

    private Integer currentHp;

    private Integer hpRegeneration;

    private Integer mp;

    private Integer currentMp;

    private Integer mpRegeneration;

    private Integer energy;

    private Integer currentEnergy;

    private Integer energyRegeneration;

    private Spell spell_1;

    private Spell spell_2;

    private Spell spell_3;
    
    private String creepType;

    public String getCreepName() {
        return creepName;
    }

    public CreepState setCreepName(String creepName) {
        this.creepName = creepName;
        return this;
    }

    public Integer getCreepLevel() {
        return creepLevel;
    }

    public CreepState setCreepLevel(Integer creepLevel) {
        this.creepLevel = creepLevel;
        return this;
    }

    public Integer getHp() {
        return hp;
    }

    public CreepState setHp(Integer hp) {
        this.hp = hp;
        return this;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public CreepState setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
        return this;
    }

    public Integer getHpRegeneration() {
        return hpRegeneration;
    }

    public CreepState setHpRegeneration(Integer hpRegeneration) {
        this.hpRegeneration = hpRegeneration;
        return this;
    }

    public Integer getMp() {
        return mp;
    }

    public CreepState setMp(Integer mp) {
        this.mp = mp;
        return this;
    }

    public Integer getCurrentMp() {
        return currentMp;
    }

    public CreepState setCurrentMp(Integer currentMp) {
        this.currentMp = currentMp;
        return this;
    }

    public Integer getMpRegeneration() {
        return mpRegeneration;
    }

    public CreepState setMpRegeneration(Integer mpRegeneration) {
        this.mpRegeneration = mpRegeneration;
        return this;
    }

    public Integer getEnergy() {
        return energy;
    }

    public CreepState setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public Integer getCurrentEnergy() {
        return currentEnergy;
    }

    public CreepState setCurrentEnergy(Integer currentEnergy) {
        this.currentEnergy = currentEnergy;
        return this;
    }

    public Integer getEnergyRegeneration() {
        return energyRegeneration;
    }

    public CreepState setEnergyRegeneration(Integer energyRegeneration) {
        this.energyRegeneration = energyRegeneration;
        return this;
    }

    public Spell getSpell_1() {
        return spell_1;
    }

    public CreepState setSpell_1(Spell spell_1) {
        this.spell_1 = spell_1;
        return this;
    }

    public Spell getSpell_2() {
        return spell_2;
    }

    public CreepState setSpell_2(Spell spell_2) {
        this.spell_2 = spell_2;
        return this;
    }

    public Spell getSpell_3() {
        return spell_3;
    }

    public CreepState setSpell_3(Spell spell_3) {
        this.spell_3 = spell_3;
        return this;
    }

    public String getCreepType() {
        return creepType;
    }

    public CreepState setCreepType(String creepType) {
        this.creepType = creepType;
        return this;
    }
}
