package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.battle.spells.description.effects.EffectData;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public abstract class Effect {

    private String name;

    private String description;

    private Integer duration;

    private String URL;

    private State holder;

    protected Effect(State holder, EffectCore ec) {
        this.name = ec.getName();
        this.description = ec.getDescription();
        this.duration = ec.getDuration();
        this.URL = ec.getURL();
        this.holder = holder;
        this.description = EffectData.modify(this).getRawDescription();
    }

    public boolean remove(Effect e) {
        return holder.getEffects().remove(e);
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

    public String getURL() {
        return URL;
    }

    public Effect setURL(String URL) {
        this.URL = URL;
        return this;
    }

    public State getHolder() {
        return holder;
    }

    public String getRawDescription() {
        return description;
    }
}
