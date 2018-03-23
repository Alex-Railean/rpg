package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class ShieldMessage implements CombatMessage {
    private Spell SPELL;

    private State CASTER;

    private int BENEFIT;

    private int COST;

    public ShieldMessage(Spell spell, State caster, int benefit, int cost) {
        this.SPELL = spell;
        this.CASTER = caster;
        this.BENEFIT = benefit;
        this.COST = cost;
    }

    @Override
    public String getCostMessage() {
        return "(Cost: " + COST + ")";
    }

    @Override
    public String getMainMessage() {
        return CASTER.getName() + " used the " + SPELL.getSpellName() + ", " +
                CASTER.getName() + "(" + CASTER.getHp().getCurrentValue() + "/" + CASTER.getHp().getValue() + ")" + "(" + CASTER.getShieldPoints() + ")"
                + " +" + BENEFIT + " of shield points";
    }
}
