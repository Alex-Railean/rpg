package com.endava.rpg.persistence.models;

import javax.persistence.*;

@Entity
@Table(name = "T_CREEP")
public class Creep implements TableMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CREEP_ID")
    private Integer creepId;

    @Column(name = "CREEP_NAME")
    private String creepName;

    @Column(name = "HP_FACTOR")
    private Integer hpFactor;

    @Column(name = "HP_REGENERATION")
    private Integer hpRegeneration;

    @Column(name = "MP_FACTOR")
    private Integer mpFactor;

    @Column(name = "MP_REGENERATION")
    private Integer mpRegeneration;

    @Column(name = "ENERGY")
    private Integer energy;

    @Column(name = "ENERGY_REGENERATION")
    private Integer energyRegeneration;

    @ManyToOne
    @JoinColumn(name = "SPELL_1")
    private Spell spell_1;

    @ManyToOne
    @JoinColumn(name = "SPELL_2")
    private Spell spell_2;

    @ManyToOne
    @JoinColumn(name = "SPELL_3")
    private Spell spell_3;

    @Column(name = "CREEP_TYPE")
    private String creepType;

    @Column(name = "CREEP_LOCATION")
    private String creepLocation;

    public Integer getCreepId() {
        return creepId;
    }

    public String getCreepName() {
        return creepName;
    }

    public Creep setCreepName(String creepName) {
        this.creepName = creepName;
        return this;
    }

    public Integer getHpFactor() {
        return hpFactor;
    }

    public Creep setHpFactor(Integer hpFactor) {
        this.hpFactor = hpFactor;
        return this;
    }

    public Integer getHpRegeneration() {
        return hpRegeneration;
    }

    public Creep setHpRegeneration(Integer hpRegeneration) {
        this.hpRegeneration = hpRegeneration;
        return this;
    }

    public Integer getMpFactor() {
        return mpFactor;
    }

    public Creep setMpFactor(Integer mpFactor) {
        this.mpFactor = mpFactor;
        return this;
    }

    public Integer getMpRegeneration() {
        return mpRegeneration;
    }

    public Creep setMpRegeneration(Integer mpRegeneration) {
        this.mpRegeneration = mpRegeneration;
        return this;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Creep setEnergy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public Integer getEnergyRegeneration() {
        return energyRegeneration;
    }

    public Creep setEnergyRegeneration(Integer energyRegeneration) {
        this.energyRegeneration = energyRegeneration;
        return this;
    }

    public Spell getSpell_1() {
        return spell_1;
    }

    public Creep setSpell_1(Spell spell_1) {
        this.spell_1 = spell_1;
        return this;
    }

    public Spell getSpell_2() {
        return spell_2;
    }

    public Creep setSpell_2(Spell spell_2) {
        this.spell_2 = spell_2;
        return this;
    }

    public Spell getSpell_3() {
        return spell_3;
    }

    public Creep setSpell_3(Spell spell_3) {
        this.spell_3 = spell_3;
        return this;
    }

    public String getCreepType() {
        return creepType;
    }

    public Creep setCreepType(String creepType) {
        this.creepType = creepType;
        return this;
    }

    public String getCreepLocation() {
        return creepLocation;
    }

    public Creep setCreepLocation(String creepLocation) {
        this.creepLocation = creepLocation;
        return this;
    }
}
