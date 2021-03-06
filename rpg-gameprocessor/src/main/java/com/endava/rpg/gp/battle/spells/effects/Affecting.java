package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.statemodels.State;
import com.fasterxml.jackson.annotation.JsonBackReference;

public abstract class Affecting {

    private State holder;

    protected Affecting(State holder) {
        this.holder = holder;
    }

    public abstract void affectTarget();

    @JsonBackReference
    public State getHolder() {
        return holder;
    }

    public Affecting setHolder(State holder) {
        this.holder = holder;
        return this;
    }
}
