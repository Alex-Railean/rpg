package com.endava.rpg.gp.battle.spells.effects.stuns;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

abstract class Stun extends Effect {

    Stun(State holder, EffectCore ec) {
        super(holder, ec);
    }

    void stun() {
        State holder = super.getHolder();
        int currentStun = holder.getStun();
        holder.setStun(super.getDuration());
        if (holder.getStun() == currentStun) {
            CombatTextService.createResistMessage(holder, this);
            super.remove();
        } else {
            CombatTextService.createEffectMessage(holder, this);
        }
    }
}
