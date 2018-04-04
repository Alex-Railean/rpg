package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public abstract class Effect {

    private String name;

    private String description;

    private Integer duration;

    private String URL;

    private State target;

    protected Effect(State target, EffectCore ec) {
        this.name = ec.getName();
        this.description = ec.getDescription();
        this.duration = ec.getDuration();
        this.URL = ec.getURL();
        this.target = target;
    }

    public abstract void affectTarget();

    public void decreaseDuration() {
        if (duration != -1) {
            duration -= 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Effect)) return false;

        Effect effect = (Effect) o;

        return getName().equals(effect.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public String getName() {
        return name;
    }

    public Effect setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Effect setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public Effect setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getURL() {
        return URL;
    }

    public Effect setURL(String URL) {
        this.URL = URL;
        return this;
    }

    public State getTarget() {
        return target;
    }
}
