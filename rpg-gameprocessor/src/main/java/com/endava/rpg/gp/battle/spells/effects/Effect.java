package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.battle.spells.description.effects.EffectData;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

public abstract class Effect implements Serializable {

    private String name;

    private String description;

    private Integer duration;

    private Integer currentDuration;

    private String URL;

    private State holder;

    private boolean toRemove = false;

    protected Effect(State holder, EffectCore ec) {
        this.name = ec.getName();
        this.description = ec.getDescription();
        this.duration = ec.getDuration();
        this.currentDuration = ec.getDuration();
        this.URL = ec.getURL();
        this.holder = holder;
        this.description = EffectData.modify(this).getRawDescription();
    }

    public void remove() {
        setToRemove(true);
    }

    public abstract void affectTarget();

    public void decreaseDuration() {
        if (currentDuration != -1) {
            currentDuration -= 1;
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
        return EffectData.updateDuration(this);
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

    public Integer getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(Integer currentDuration) {
        this.currentDuration = currentDuration;
    }

    public String getURL() {
        return URL;
    }

    public Effect setURL(String URL) {
        this.URL = URL;
        return this;
    }

    @JsonBackReference
    public State getHolder() {
        return holder;
    }

    public void setHolder(State holder) {
        this.holder = holder;
    }

    public String getRawDescription() {
        return description;
    }

    public void refreshDuration() {
        this.currentDuration = this.duration;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public Effect setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
        return this;
    }
}
