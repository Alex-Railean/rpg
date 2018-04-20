package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.battle.spells.description.effects.EffectData;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

import java.io.Serializable;
import java.util.Set;

public abstract class Effect extends Affecting implements Serializable {

    private String name;

    private String description;

    private Integer duration;

    private Integer remainingDuration;

    private String URL;

    private boolean toRemove = false;

    protected Effect(State holder, EffectCore ec) {
        super(holder);
        this.name = ec.getName();
        this.description = ec.getDescription();
        this.duration = ec.getDuration();
        this.remainingDuration = ec.getDuration();
        this.URL = ec.getURL();
        this.description = EffectData.modify(this).getRawDescription();
    }

    protected void instantEffect() {
        affectTarget();
        decreaseDuration();
    }

    public Effect remove() {
        setToRemove(true);
        return this;
    }

    public boolean addTo(Set<Effect> effects) {
        return effects.add(this);
    }

    public void decreaseDuration() {
        if (remainingDuration != -1) {
            remainingDuration -= 1;
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

    public Integer getRemainingDuration() {
        return remainingDuration;
    }

    public void setRemainingDuration(Integer remainingDuration) {
        this.remainingDuration = remainingDuration;
    }

    public String getURL() {
        return URL;
    }

    public Effect setURL(String URL) {
        this.URL = URL;
        return this;
    }

    public String getRawDescription() {
        return description;
    }

    public Effect refreshDuration() {
        this.remainingDuration = this.duration;
        return this;
    }

    public boolean isToRemove() {
        return toRemove;
    }

    public Effect setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
        return this;
    }
}
