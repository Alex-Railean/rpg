package com.endava.rpg.gp.battle.spells.effects.shields;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Displayed;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Shield;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

import java.util.Set;

public class DefenceStance extends Effect implements Shield, Displayed {

    private Integer points;

    public DefenceStance(State target, Spell s) {
        super(target, s.getEffectCore());
        target.getEffects().remove(this);
        points = FormulaService.getShield(target, s.getCoefficient());
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
    public DefenceStance setPoints(int p) {
        points = p;
        return this;
    }

    @Override
    public boolean addTo(Set<Effect> effects) {
        CombatTextService.createShieldEffectRecord(super.getHolder(), points);
        return super.addTo(effects);
    }
}
