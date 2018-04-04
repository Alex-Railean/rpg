package com.endava.rpg.gp.game;

import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FormulaService {

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

    public static Integer getDeservedExp() {
        return CharacterStateService.getCharacter().getLastMovePoints() / 5 * GameService.GAME_RATE;
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

    public static Integer getCharacterHp() {
        Integer hp = 50;

        for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
            hp += 20 + i * GameService.GROWTH_FACTOR;
        }

        for (int i = 1; i <= CharacterStateService.getCharacter().getStrength().getProgressLevel(); i++) {
            hp += 35 + i * GameService.GROWTH_FACTOR;
        }

        return hp;
    }

    public static Integer getCharacterMp() {
        Integer mp = 50;

        for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
            mp += 10 + i * GameService.GROWTH_FACTOR;
        }

        for (int i = 1; i <= CharacterStateService.getCharacter().getIntelligence().getProgressLevel(); i++) {
            mp += 45 + i * GameService.GROWTH_FACTOR;
        }

        return mp;
    }
}
