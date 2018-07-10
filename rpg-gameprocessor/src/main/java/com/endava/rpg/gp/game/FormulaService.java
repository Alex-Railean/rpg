package com.endava.rpg.gp.game;

import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FormulaService {

    private final static Map<Integer, Map<Integer, Integer>> CALCULATED_DMG = new HashMap<>();

    private final static Map<Integer, Map<Integer, Integer>> CALCULATED_CREEP_POINTS = new HashMap<>();

    private final static Map<Integer, Integer> CALCULATED_HP = new HashMap<>();

    private final static Map<Integer, Integer> CALCULATED_MP = new HashMap<>();

    public static int getDamage(int lvl, int dc) {

        if (dc < 0) return 0;

        CALCULATED_DMG.computeIfAbsent(lvl, k -> new HashMap<>());

        if (CALCULATED_DMG.get(lvl).get(dc) == null) {
            double dmgReducer = 0.1;
            int dmg = dc;

            for (int i = 1; i <= lvl; i++) {
                dmg += dmg * dmgReducer;
                dmgReducer *= 0.993;
            }

            CALCULATED_DMG.get(lvl).put(dc, dmg);

            return dmg;

        } else {
            return CALCULATED_DMG.get(lvl).get(dc);
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

    public static Integer getDeservedExp(Integer movePoints) {
        return movePoints / 5 * GameService.GAME_RATE;
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

    public static Integer getCreepPoints(Integer factor, int lvl) {
        CALCULATED_CREEP_POINTS.computeIfAbsent(lvl, k -> new HashMap<>());

        if (CALCULATED_CREEP_POINTS.get(lvl).get(factor) == null) {

            Integer points = factor * 5;

            for (int i = 1; i <= lvl; i++) {
                points += factor + i * GameService.GROWTH_FACTOR;
            }

            CALCULATED_CREEP_POINTS.get(lvl).put(factor, points);

            return points;
        } else {
            return CALCULATED_CREEP_POINTS.get(lvl).get(factor);
        }
    }

    public static Integer getManaCost(Spell spell) {
        return spell.getCost() +
                CharacterStateService.getLvl() +
                GameService.GROWTH_FACTOR * 2;
    }

    public static Integer getCharacterHp() {
        int lvl = CharacterStateService.getLvl();

        if (CALCULATED_HP.get(lvl) == null) {
            Integer hp = 50;

            for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
                hp += 20 + i * GameService.GROWTH_FACTOR;
            }

            for (int i = 1; i <= CharacterStateService.getCharacter().getStrength().getProgressLevel(); i++) {
                hp += 35 + i * GameService.GROWTH_FACTOR;
            }

            CALCULATED_HP.put(lvl, hp);

            return hp;
        }

        return CALCULATED_HP.get(lvl);
    }

    public static Integer getCharacterMp() {
        int lvl = CharacterStateService.getLvl();

        if (CALCULATED_MP.get(lvl) == null) {

            Integer mp = 50;

            for (int i = 1; i <= CharacterStateService.getLvl(); i++) {
                mp += 10 + i * GameService.GROWTH_FACTOR;
            }

            for (int i = 1; i <= CharacterStateService.getCharacter().getIntellect().getProgressLevel(); i++) {
                mp += 45 + i * GameService.GROWTH_FACTOR;
            }

            CALCULATED_MP.put(lvl, mp);

            return mp;
        }

        return CALCULATED_MP.get(lvl);
    }
}
