package com.endava.rpg.persistence.models;

import javax.persistence.*;

@Entity
@Table(name = "T_SPELL")
public class Spell implements Models{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPELLS_ID")
    private Integer spellId;

    @Column(name = "SPELL_NAME")
    private String spellName;

    @Column(name = "COOLDOWN")
    private Integer cooldown;

    @Column(name = "EFFECT_ID")
    private Integer effectId;

    @Column(name = "DMG_COEFFICIENT")
    private Integer dmgCoefficient;

    @Column(name = "SPELL_URL")
    private String spellURL;

    @Column(name = "COST")
    private Integer cost;

    @Column(name = "TYPE")
    private String spellType;

    @Column(name = "ATTRIBUTE")
    private String attribute;

    public Integer getSpellId() {
        return spellId;
    }

    public String getSpellName() {
        return spellName;
    }

    public Spell setSpellName(String spellName) {
        this.spellName = spellName;
        return this;
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public Spell setCooldown(Integer cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public Integer getEffectId() {
        return effectId;
    }

    public Spell setEffectId(Integer effectId) {
        this.effectId = effectId;
        return this;
    }

    public Integer getDmgCoefficient() {
        return dmgCoefficient;
    }

    public Spell setDmgCoefficient(Integer dmgCoefficient) {
        this.dmgCoefficient = dmgCoefficient;
        return this;
    }

    public String getSpellURL() {
        return spellURL;
    }

    public Spell setSpellURL(String spellURL) {
        this.spellURL = spellURL;
        return this;
    }

    public Integer getCost() {
        return cost;
    }

    public Spell setCost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public String getSpellType() {
        return spellType;
    }

    public Spell setSpellType(String spellType) {
        this.spellType = spellType;
        return this;
    }

    public String getAttribute() {
        return attribute;
    }

    public Spell setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }
}
