package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class ShieldMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public ShieldMessage(Spell spell, State caster, int benefit, int cost) {
        this.headerMessage = "(Cost: " + cost + ")";
        this.mainMessage = caster.getName() + " used the " + spell.getSpellName() + ", " +
                caster.getName() + "(" + caster.getHp().getCurrentValue() + "/" + caster.getHp().getValue() + ")" + "(" + caster.getShieldPoints() + ")"
                + " +" + benefit + " of shield points";
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
