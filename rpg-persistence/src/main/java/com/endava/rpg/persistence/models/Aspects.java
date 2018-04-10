package com.endava.rpg.persistence.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_ASPECTS")
public class Aspects extends BranchEntity implements TableMapping, Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    @Column(name = "TOTAL_POINTS")
    private Integer totalPoints = 0;

    @Override
    public Aspects calculateTotalPoints() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aspects)) return false;

        Aspects that = (Aspects) o;

        return getCharacter().equals(that.getCharacter());
    }

    @Override
    public int hashCode() {
        return getCharacter().hashCode();
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Character getCharacter() {
        return character;
    }

    public Aspects setCharacter(Character character) {
        this.character = character;
        return this;
    }
}
