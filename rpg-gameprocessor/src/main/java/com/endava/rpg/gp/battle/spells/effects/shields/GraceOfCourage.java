package com.endava.rpg.gp.battle.spells.effects.shields;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Shield;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public class GraceOfCourage extends Effect implements Shield, Leveled {

    private final int COEFICIENT = 2;

    private Integer points;

    private int level;

    public GraceOfCourage(State holder, EffectCore ec) {
        super(holder, ec);
        this.points = (getHolder().getHp().getValue() / 100 * COEFICIENT) * level;
    }

    @Override
    public void affectTarget() {
        if (points <= 0) {
            setToRemove(true);
        }
    }

    @Override
    public Integer getPoints() {
        return points;
    }

    @Override
    public GraceOfCourage setPoints(int p) {
        this.points = p;
        return this;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public GraceOfCourage setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public void refreshDuration() {
        this.points = (getHolder().getHp().getValue() / 100 * COEFICIENT) * level;
        super.refreshDuration();
    }
}
