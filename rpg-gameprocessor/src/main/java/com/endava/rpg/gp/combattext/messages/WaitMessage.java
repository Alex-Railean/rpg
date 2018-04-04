package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;

public class WaitMessage implements CombatMessage {

    private String costMessage;

    private String mainMessage;

    public WaitMessage(State caster) {
        this.costMessage = "(Regenerated: " + caster.getHp().getRegeneration() + " HP; " +
                caster.getMp().getRegeneration() + " MP; " +
                caster.getEnergy().getRegeneration() + " Energy)";

        this.mainMessage = caster.getName() + " waited this turn";
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
