package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.statemodels.State;

public class ResistMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public ResistMessage(State resisted, Effect effect) {
        headerMessage = "(Resistance)";
        mainMessage = resisted.getName() + " has resisted effect " + effect.getName();
    }

    @Override
    public String getHeaderMessage() {
        return headerMessage;
    }

    @Override
    public String getMainMessage() {
        return mainMessage;
    }
}
