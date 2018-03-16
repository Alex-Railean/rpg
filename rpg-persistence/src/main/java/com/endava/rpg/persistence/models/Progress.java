package com.endava.rpg.persistence.models;

import javax.persistence.*;

@Entity
@Table(name = "T_PROGRESS")
public class Progress implements TableMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "PROGRESS_ID")
    private Integer progressId;

    @Column(name = "STRENGTH_PROGRESS_LEVEL")
    private Integer strengthProgressLevel = 1;

    @Column(name = "STRENGTH_PROGRESS")
    private Integer strengthProgress = 0;

    @Column(name = "AGILITY_PROGRESS_LEVEL")
    private Integer agilityProgressLevel = 1;

    @Column(name = "AGILITY_PROGRESS")
    private Integer agilityProgress = 0;

    @Column(name = "INTELLIGENCE_PROGRESS_LEVEL")
    private Integer intelligenceProgressLevel = 1;

    @Column(name = "INTELLIGENCE_PROGRESS")
    private Integer intelligenceProgress = 0;

    public Progress addStrengthProgress(Integer strengthProgress) {
        this.strengthProgress += strengthProgress;
        return this;
    }

    public Progress addStrengthProgressLevel(Integer strengthProgressLevel) {
        this.strengthProgressLevel += strengthProgressLevel;
        return this;
    }

    public Progress addAgilityProgress(Integer agilityProgress) {
        this.agilityProgress += agilityProgress;
        return this;
    }

    public Progress addAgilityProgressLevel(Integer agilityProgressLevel) {
        this.agilityProgressLevel += agilityProgressLevel;
        return this;
    }

    public Progress addIntelligenceProgressLevel(Integer intelligenceProgressLevel) {
        this.intelligenceProgressLevel += intelligenceProgressLevel;
        return this;
    }

    public Progress addIntelligenceProgress(Integer intelligenceProgress) {
        this.intelligenceProgress += intelligenceProgress;
        return this;
    }

    public Integer getProgressId() {
        return progressId;
    }

    public Integer getStrengthProgressLevel() {
        return strengthProgressLevel;
    }

    public Progress setStrengthProgressLevel(Integer strengthProgressLevel) {
        this.strengthProgressLevel = strengthProgressLevel;
        return this;
    }

    public Integer getStrengthProgress() {
        return strengthProgress;
    }

    public Progress setStrengthProgress(Integer strengthProgress) {
        this.strengthProgress = strengthProgress;
        return this;
    }

    public Integer getAgilityProgressLevel() {
        return agilityProgressLevel;
    }

    public Progress setAgilityProgressLevel(Integer agilityProgressLevel) {
        this.agilityProgressLevel = agilityProgressLevel;
        return this;
    }

    public Integer getAgilityProgress() {
        return agilityProgress;
    }

    public Progress setAgilityProgress(Integer agilityProgress) {
        this.agilityProgress = agilityProgress;
        return this;
    }

    public Integer getIntelligenceProgressLevel() {
        return intelligenceProgressLevel;
    }

    public Progress setIntelligenceProgressLevel(Integer intelligenceProgressLevel) {
        this.intelligenceProgressLevel = intelligenceProgressLevel;
        return this;
    }

    public Integer getIntelligenceProgress() {
        return intelligenceProgress;
    }

    public Progress setIntelligenceProgress(Integer intelligenceProgress) {
        this.intelligenceProgress = intelligenceProgress;
        return this;
    }
}
