package com.endava.rpg.gp.battle.spells.effects.targeted;

import com.endava.rpg.gp.battle.spells.effects.roots.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Displayed;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class SanctifiedWrath extends Effect implements Displayed {

    public SanctifiedWrath(State holder, Spell s) {
        super(holder, s.getEffectCore());
    }

    @Override
    public void affectTarget() {
        if (super.getDuration().equals(super.getRemainingDuration() + 1)) {
            super.getHolder().setStun(super.getDuration());
        }
    }
}
