package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class AttackMessage implements CombatMessage {

    private String headerMessage;

    private String mainMessage;

    public AttackMessage(Spell spell, State caster, State target, int dmg, int cost) {
        headerMessage = "(Cost: " + cost + ")";
        mainMessage = caster.getName() + " used the " + spell.getSpellName() + " on " +
                target.getName() + "(" + target.getHp().getCurrentValue() + "/" + target.getHp().getValue() + ")" + "(" + target.getShieldPoints() + ")"
                + " -" + dmg;
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
