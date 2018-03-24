package com.endava.rpg.gp.game;

import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FormulaService {

    private CharacterStateService characterStateService;

    private SpellService spellService;

    private static Map<Integer, Map<Integer, Integer>> calculatedDmg = new HashMap<>();

    public static int getDamage(int lvl, int damageCoefficient) {

        if (calculatedDmg.get(lvl) == null) {
            calculatedDmg = new HashMap<>();
            calculatedDmg.put(lvl, new HashMap<>());
        }

        if (calculatedDmg.get(lvl).get(damageCoefficient) == null) {
            double dmgReducer = 0.1;
            int dmg = damageCoefficient;

            for (int i = 1; i <= lvl; i++) {
                dmg += dmg * dmgReducer;
                dmgReducer *= 0.993;
            }

            calculatedDmg.get(lvl).put(damageCoefficient, dmg);

            return dmg;

        } else {
            return calculatedDmg.get(lvl).get(damageCoefficient);
        }
    }

    public static Double getMinDmg(int dmg) {
        return dmg - dmg * 0.15;
    }

    public static Double getMaxDmg(int dmg) {
        return dmg + dmg * 0.15;
    }

    public static int getShield(State target, int damageCoefficient) {
        return getDamage(target.getLevel(), damageCoefficient);
    }

    public static Integer getNextLevelExp(Integer attributeLevel) {
        Integer exp = 0;

        for (int i = 1; i <= attributeLevel; i++) {
            exp += 50 + i * 33;
        }

        return exp;
    }

    public static Integer getCreepPoints(Integer factor) {
        Integer points = factor * 5;

        for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
            points += factor + i * GameService.GROWTH_FACTOR;
        }

        return points;
    }

    public static Integer getManaCost(Spell spell) {
        return spell.getCost() +
                CharacterStateService.getLvl() +
                GameService.GROWTH_FACTOR * 2;
    }

    public Integer getCharacterHp() {
        Integer hp = 50;

        for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
            hp += 20 + i * GameService.GROWTH_FACTOR;
        }

        for (int i = 1; i <= characterStateService.getCharacterState().getStrength().getProgressLevel(); i++) {
            hp += 35 + i * GameService.GROWTH_FACTOR;
        }

        return hp;
    }

    public Integer getCharacterMp() {
        Integer mp = 50;

        for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
            mp += 10 + i * GameService.GROWTH_FACTOR;
        }

        for (int i = 1; i <= characterStateService.getCharacterState().getIntelligence().getProgressLevel(); i++) {
            mp += 45 + i * GameService.GROWTH_FACTOR;
        }

        return mp;
    }

    // TODO: The only usage of spellService in class. Try to move
    public Integer getDeservedExp() {
        return spellService.getLastMovePoints() / 5 * GameService.GAME_RATE;
    }

    @Autowired
    private void setCharacterStateService(CharacterStateService characterStateService) {
        this.characterStateService = characterStateService;
    }

    @Autowired
    private void setSpellService(SpellService spellService) {
        this.spellService = spellService;
    }
}
