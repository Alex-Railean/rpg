package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;

public class ShieldEffectMessage implements CombatMessage {

    private String costMessage;

    private String mainMessage;

    public ShieldEffectMessage(State target, int benefit) {
        this.costMessage = "(Effect)";
        this.mainMessage = target.getName() + " receives a shield effect. " +
                target.getName() + "(" + target.getHp().getCurrentValue() + "/" + target.getHp().getValue() + ")" + "(" + target.getShieldPoints() + ")"
                + " +" + benefit + " of shield points";
    }

    @Override
    public String getCostMessage() {
        return costMessage;
    }

    @Override
    public String getMainMessage() {
        return mainMessage;
    }
}
