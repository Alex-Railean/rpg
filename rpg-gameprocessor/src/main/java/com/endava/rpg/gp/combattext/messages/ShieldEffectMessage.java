package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;

public class ShieldEffectMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public ShieldEffectMessage(State target, int benefit) {
        headerMessage = "(Effect)";
        mainMessage = target.getName() + " receives a shield effect. " +
                target.getName() + "(" + target.getHp().getCurrentValue() + "/" + target.getHp().getValue() + ")" + "(" + target.getShieldPoints() + ")"
                + " +" + benefit + " of shield points";
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
