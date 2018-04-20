package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;

public class WaitMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public WaitMessage(State caster) {
        headerMessage = "(Regenerated: " + caster.getHp().getRegeneration() + " HP; " +
                caster.getMp().getRegeneration() + " MP; " +
                caster.getEnergy().getRegeneration() + " Energy)";

        mainMessage = caster.getName() + " waited this turn";
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
