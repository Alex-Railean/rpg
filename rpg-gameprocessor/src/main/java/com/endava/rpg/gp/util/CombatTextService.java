package com.endava.rpg.gp.util;

import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class CombatTextService {

    private static List<String> combatText = new ArrayList<>();

    private CombatTextService() {
    }

    public static void createSpellRecord(Spell spell, State caster, State target, int dmg, int manaCost) {
        String combatRecord = "(Cost: " + manaCost + ")</br>" +
                caster.getName() + " used the " + spell.getSpellName() + " on " +
                target.getName() + "(" + target.getHp().getCurrentValue() + "/" + target.getHp().getValue() + ")" + "(" + target.getShieldPoints() + ")"
                + " -" + dmg;
        combatText.add(combatRecord);
    }

    public static void createSpellRecord(Spell spell, State caster, int benefit, int manaCost) {
        String combatRecord = "(Cost: " + manaCost + ")</br>" +
                caster.getName() + " used the " + spell.getSpellName() + ", " +
                caster.getName() + "(" + caster.getHp().getCurrentValue() + "/" + caster.getHp().getValue() + ")" + "(" + caster.getShieldPoints() + ")"
                + " +" + benefit + " of shield points";
        combatText.add(combatRecord);
    }

    public static String getCombatText() {
        List<String> reverse = new ArrayList<>(combatText);
        Collections.reverse(reverse);
        return reverse.stream().collect(Collectors.joining("</br></br>"));
    }

    public static void clearCombatText() {
        combatText = new ArrayList<>();
    }
}
