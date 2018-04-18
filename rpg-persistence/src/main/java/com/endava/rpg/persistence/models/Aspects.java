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

    @Column(name = "CURSED_BLADE")
    private Integer cursedBlade = 0;

    @Column(name = "CURSED_BLADE_LIMIT")
    private Integer cursedBladeLimit = 5;

    @Column(name = "COURAGE")
    private Integer courage = 0;

    @Column(name = "COURAGE_LIMIT")
    private Integer courageLimit = 5;

    @Column(name = "TOTAL_POINTS")
    private Integer totalPoints = 0;

    @Override
    public Aspects calculateTotalPoints() {
        totalPoints = cursedBlade;
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

    @Override
    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Aspects addCursedBlade(int increase) {
        this.cursedBlade += increase;
        return this;
    }

    public Aspects addCourage(int increase) {
        this.courage += increase;
        return this;
    }

    public Character getCharacter() {
        return character;
    }

    public Aspects setCharacter(Character character) {
        this.character = character;
        return this;
    }

    public Integer getCursedBlade() {
        return cursedBlade;
    }

    public Aspects setCursedBlade(Integer cursedBlade) {
        this.cursedBlade = cursedBlade;
        return this;
    }

    public Integer getCursedBladeLimit() {
        return cursedBladeLimit;
    }

    public Aspects setCursedBladeLimit(Integer cursedBladeLimit) {
        this.cursedBladeLimit = cursedBladeLimit;
        return this;
    }

    public Integer getCourage() {
        return courage;
    }

    public void setCourage(Integer courage) {
        this.courage = courage;
    }

    public Integer getCourageLimit() {
        return courageLimit;
    }

    public Aspects setCourageLimit(Integer courageLimit) {
        this.courageLimit = courageLimit;
        return this;
    }
}
