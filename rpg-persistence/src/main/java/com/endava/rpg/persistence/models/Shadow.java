package com.endava.rpg.persistence.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_SHADOW")
public class Shadow extends BranchEntity implements TableMapping, Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    @Column(name = "TOTAL_POINTS")
    private Integer totalPoints = 0;

    @Override
    public BranchEntity calculateTotalPoints() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shadow)) return false;

        Shadow shadow = (Shadow) o;

        if (getCharacter() != null ? !getCharacter().equals(shadow.getCharacter()) : shadow.getCharacter() != null)
            return false;
        return getTotalPoints() != null ? getTotalPoints().equals(shadow.getTotalPoints()) : shadow.getTotalPoints() == null;
    }

    @Override
    public int hashCode() {
        int result = getCharacter() != null ? getCharacter().hashCode() : 0;
        result = 31 * result + (getTotalPoints() != null ? getTotalPoints().hashCode() : 0);
        return result;
    }

    public Character getCharacter() {
        return character;
    }

    public Shadow setCharacter(Character character) {
        this.character = character;
        return this;
    }

    @Override
    public Integer getTotalPoints() {
        return null;
    }

    public Shadow setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }
}
