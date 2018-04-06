package com.endava.rpg.persistence.models;

public abstract class BranchEntity implements TableMapping {
    public abstract BranchEntity calculateTotalPoints();

    public abstract Integer getTotalPoints();
}
