package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.battle.spells.effects.roots.Effect;
import com.endava.rpg.gp.statemodels.State;

public class EffectMessage implements CombatMessage {
    private String costMessage;

    private String mainMessage;

    public EffectMessage(State target, Effect effect) {
        this.costMessage = "(Effect)";
        this.mainMessage = target.getName() + " is under effect " + effect.getName();
    }

    @Override
    public String getCostMessage() {
        return this.costMessage;
    }

    @Override
    public String getMainMessage() {
        return this.mainMessage;
    }
}
