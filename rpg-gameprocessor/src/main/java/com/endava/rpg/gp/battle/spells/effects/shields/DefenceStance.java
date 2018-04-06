package com.endava.rpg.gp.battle.spells.effects.shields;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class DefenceStance extends Effect implements Shield {

    private int points;

    public DefenceStance(State target, Spell s) {
        super(target, s.getEffectCore());
        target.getEffects().remove(this);
        points = FormulaService.getShield(target, s.getCoefficient());
    }

    @Override
    public void affectTarget() {
        if (points <= 0) {
            getHolder().getEffects().remove(this);
        }
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public Shield setPoints(int p) {
        points = p;
        return this;
    }
}
