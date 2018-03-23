package com.endava.rpg.gp.combattext.messages;

import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class AttackMessage implements CombatMessage {
    private final Spell SPELL;

    private final State CASTER;

    private final State TARGET;

    private final int DMG;

    private final int COST;

    public AttackMessage(Spell spell, State caster, State target, int dmg, int cost) {
        this.SPELL = spell;
        this.CASTER = caster;
        this.TARGET = target;
        this.DMG = dmg;
        this.COST = cost;
    }

    @Override
    public String getCostMessage() {
        return "(Cost: " + COST + ")";
    }

    @Override
    public String getMainMessage() {
        return CASTER.getName() + " used the " + SPELL.getSpellName() + " on " +
                TARGET.getName() + "(" + TARGET.getHp().getCurrentValue() + "/" + TARGET.getHp().getValue() + ")" + "(" + TARGET.getShieldPoints() + ")"
                + " -" + DMG;
    }
}
