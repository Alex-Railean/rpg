package com.endava.rpg.gp.combattext;

import com.endava.rpg.gp.combattext.messages.AttackMessage;
import com.endava.rpg.gp.combattext.messages.CombatMessage;
import com.endava.rpg.gp.combattext.messages.ShieldMessage;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CombatTextService {

    private static List<CombatMessage> combatText = new ArrayList<>();

    private CombatTextService() {
    }

    public static void createSpellRecord(Spell spell, State caster, State target, int dmg, int cost) {
        CombatMessage combatRecord = new AttackMessage(spell, caster, target, dmg, cost);
        combatText.add(combatRecord);
    }

    public static void createSpellRecord(Spell spell, State caster, int benefit, int cost) {
        CombatMessage combatRecord = new ShieldMessage(spell, caster, benefit, cost);
        combatText.add(combatRecord);
    }

    public static List<CombatMessage> getCombatText() {
        List<CombatMessage> reverse = new ArrayList<>(combatText);
        Collections.reverse(reverse);
        return reverse;
    }

    public static void clearCombatText() {
        combatText = new ArrayList<>();
    }
}
