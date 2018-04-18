package com.endava.rpg.gp.combattext;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.combattext.messages.*;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CombatTextService {

    private static List<CombatMessage> combatText = new ArrayList<>();

    private CombatTextService() {
    }

    public static void createAttackRecord(Spell spell, State caster, State target, int dmg, int cost) {
        combatText.add(new AttackMessage(spell, caster, target, dmg, cost));
    }

    public static void createShieldRecord(Spell spell, State caster, int benefit, int cost) {
        combatText.add(new ShieldMessage(spell, caster, benefit, cost));
    }

    public static void createShieldEffectRecord(State target, int benefit) {
        combatText.add(new ShieldEffectMessage(target, benefit));
    }

    public static void createWaitMessage(State caster) {
        combatText.add(new WaitMessage(caster));
    }

    public static void createCustomMessage(String costMessage, String mainMessage) {
        combatText.add(new CustomMessage(costMessage, mainMessage));
    }

    public static void createEffectMessage(State target, Effect effect) {
        combatText.add(new EffectMessage(target, effect));
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
