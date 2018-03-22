package com.endava.rpg.persistence.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_CHARACTER")
public class Character implements TableMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "CHARACTER_ID")
    private Integer characterId;

    @Column(name = "CHARACTER_NAME")
    private String characterName;

    @Column(name = "LOCATION")
    private String location = "hungry-forest";

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROGRESS_ID")
    private Progress progress;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ACTION_BAR_ID")
    private ActionBar actionBar;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "T_AVAILABLE_SPELLS",
            joinColumns = {@JoinColumn(name = "CHARACTER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPELL_ID")})
    private List<Spell> availableSpells;

    @Column(name = "FREE_POINTS")
    private Integer freePoints = 0;

    @OneToOne(mappedBy = "character")
    private Technologies technologies;

    public Character(String characterName, Progress progress, ActionBar actionBar) {
        this.characterName = characterName;
        this.progress = progress;
        this.actionBar = actionBar;
    }

    public Character() {
    }

    public Character addFreePoints(Integer freePoints) {
        this.freePoints += freePoints;
        return this;
    }

    public Character removeFreePoints(Integer freePoints) {
        this.freePoints -= freePoints;
        return this;
    }

    public List<Spell> getAvailableSpells() {
        return availableSpells;
    }

    public void setAvailableSpells(List<Spell> spells) {
        this.availableSpells = spells;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Character setCharacterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public Progress getProgress() {
        return progress;
    }

    public Character setProgress(Progress progressId) {
        this.progress = progressId;
        return this;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public Character setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Character setLocation(String location) {
        this.location = location;
        return this;
    }

    public Integer getFreePoints() {
        return freePoints;
    }

    public Character setFreePoints(Integer freePoints) {
        this.freePoints = freePoints;
        return this;
    }

    public Technologies getTechnologies() {
        return technologies;
    }

    public Character setTechnologies(Technologies technologies) {
        this.technologies = technologies;
        return this;
    }
}
