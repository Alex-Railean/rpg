package com.endava.rpg.persistence.models;

import com.endava.rpg.persistence.services.utils.DescribedSpell;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_SPELL")
public class Spell implements TableMapping, DescribedSpell, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPELL_ID")
    private Integer spellId;

    @Column(name = "SPELL_NAME")
    private String spellName;

    @Column(name = "COOLDOWN")
    private Integer cooldown;

    @OneToOne
    @JoinColumn(name = "EFFECT")
    private EffectCore effectCore;

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

    @Column(name = "REQUIRED")
    private Integer required;

    @Column(name = "BRANCH")
    private String branch;

    @Transient
    private Integer currentCooldown = 0;

    public void onCooldown() {
        this.currentCooldown = this.cooldown;
    }

    public void tickCooldown() {
        if (this.currentCooldown > 0) {
            this.currentCooldown -= 1;
        }
    }

    @Override
    @JsonBackReference
    public Spell getSpell() {
        return this;
    }

    @Override
    public String getDescription() {
        return spellName.toUpperCase();
    }

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

    public EffectCore getEffectCore() {
        return effectCore;
    }

    public Spell setEffectCore(EffectCore effectId) {
        this.effectCore = effectId;
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

    public Integer getRequired() {
        return required;
    }

    public Spell setRequired(Integer required) {
        this.required = required;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public Spell setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public Integer getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(Integer currentCooldown) {
        this.currentCooldown = currentCooldown;
    }
}
