package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.statemodels.State;

public class EffectMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public EffectMessage(State target, Effect effect) {
        headerMessage = "(Effect)";
        mainMessage = target.getName() + " is under effect " + effect.getName();
    }

    @Override
    public String getHeaderMessage() {
        return this.headerMessage;
    }

    @Override
    public String getMainMessage() {
        return this.mainMessage;
    }
}
