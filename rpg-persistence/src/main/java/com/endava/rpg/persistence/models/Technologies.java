package com.endava.rpg.persistence.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_TECHNOLOGIES")
public class Technologies extends BranchEntity implements TableMapping, Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    @Column(name = "MUSCLE_STIMULANTS")
    private Integer muscleStimulants = 0;

    @Column(name = "MUSCLE_STIMULANTS_LIMIT")
    private Integer muscleStimulantsLimit = 5;

    @Column(name = "EXOSPINE")
    private Integer exoSpine = 0;

    @Column(name = "EXOSPINE_LIMIT")
    private Integer exoSpineLimit = 5;

    @Column(name = "TOTAL_POINTS")
    private Integer totalPoints = 0;

    @Override
    public Technologies calculateTotalPoints() {
        totalPoints = muscleStimulants + exoSpine;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Technologies)) return false;

        Technologies that = (Technologies) o;

        return getCharacter().equals(that.getCharacter());
    }

    @Override
    public int hashCode() {
        return getCharacter().hashCode();
    }

    @Override
    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Technologies addExoSpine(int increase) {
        this.exoSpine += increase;
        return this;
    }

    public Technologies addMuscleStimulants(int increase) {
        this.muscleStimulants += increase;
        return this;
    }

    public Character getCharacter() {
        return character;
    }

    public Technologies setCharacter(Character character) {
        this.character = character;
        return this;
    }

    public Integer getMuscleStimulants() {
        return muscleStimulants;
    }

    public Technologies setMuscleStimulants(Integer muscleStimulants) {
        this.muscleStimulants = muscleStimulants;
        return this;
    }

    public Integer getExoSpine() {
        return exoSpine;
    }

    public Technologies setExoSpine(Integer exoSpine) {
        this.exoSpine = exoSpine;
        return this;
    }

    public Integer getMuscleStimulantsLimit() {
        return muscleStimulantsLimit;
    }

    public Technologies setMuscleStimulantsLimit(Integer muscleStimulantsLimit) {
        this.muscleStimulantsLimit = muscleStimulantsLimit;
        return this;
    }

    public Integer getExoSpineLimit() {
        return exoSpineLimit;
    }

    public Technologies setExoSpineLimit(Integer exoSpineLimit) {
        this.exoSpineLimit = exoSpineLimit;
        return this;
    }
}
