package com.endava.rpg.persistence.models;


import javax.persistence.*;

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

    public Character(String characterName, Progress progress, ActionBar actionBar) {
        this.characterName = characterName;
        this.progress = progress;
        this.actionBar = actionBar;
    }

    public Character() {
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
}
