package com.endava.rpg.gp.statemodels.points;

import com.endava.rpg.gp.statemodels.State;

public class Point {

    private Integer value;

    private Integer currentValue;

    private Integer regeneration;

    public Point(State cs) {
        cs.addPoint(this);
    }

    public void useRegeneration() {
        if (currentValue < value) {
            currentValue = currentValue + regeneration >= value ?
                    value :
                    currentValue + regeneration;

        }
    }

    public Point refresh() {
        this.currentValue = this.value;
        return this;
    }

    public Point subtractCurrentValue(Integer dmg) {
        currentValue -= dmg;
        return this;
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
