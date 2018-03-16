package com.endava.rpg.gp.statemodels.points;

import com.endava.rpg.gp.statemodels.State;

public class Point {

    private Integer value;

    private Integer currentValue;

    private Integer regeneration;

    public Point(State cs) {
        cs.addPoint(this);
    }

    public Integer getValue() {
        return value;
    }

    public Point setValue(Integer value) {
        this.value = value;
        return this;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public Point setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
        return this;
    }

    public Integer getRegeneration() {
        return regeneration;
    }

    public Point setRegeneration(Integer regeneration) {
        this.regeneration = regeneration;
        return this;
    }
}
