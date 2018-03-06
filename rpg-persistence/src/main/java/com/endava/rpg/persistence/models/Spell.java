package com.endava.rpg.persistence.models;

import javax.persistence.*;

@Entity
@Table(name = "T_SPELL")
public class Spell implements TableMapping {

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

    @Column(name = "COEFFICIENT")
    private Integer coefficient;

    @Column(name = "SPELL_URL")
    private String spellURL;

    @Column(name = "COST")
    private Integer cost;

    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "ATTRIBUTE")
    private String attribute;

    @Column(name = "TYPE")
    private String spellType;

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

    public Integer getCoefficient() {
        return coefficient;
    }

    public Spell setCoefficient(Integer dmgCoefficient) {
        this.coefficient = dmgCoefficient;
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

    public String getSchool() {
        return school;
    }

    public Spell setSchool(String school) {
        this.school = school;
        return this;
    }

    public String getAttribute() {
        return attribute;
    }

    public Spell setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public String getSpellType() {
        return spellType;
    }

    public Spell setSpellType(String spellType) {
        this.spellType = spellType;
        return this;
    }
}
