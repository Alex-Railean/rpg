package com.endava.rpg.gp.services.game;

import com.endava.rpg.gp.services.battle.spells.SpellService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FormulaService {

    private CharacterStateService characterStateService;

    private GameService game;

    private SpellService spellService;

    private Map<Integer, Map<Integer, Integer>> calculatedDmg = new HashMap<>();

    public Integer getCreepPoints(Integer factor) {
        Integer points = factor * 5;

        for (int i = 1; i <= characterStateService.getCharacterLevel(); i++) {
            points += factor + i * game.getGrowthFactor();
        }

        return points;
    }

    public Integer getManaCost(Spell spell) {
        return spell.getCost() +
                characterStateService.getCharacterLevel() +
                game.getGrowthFactor() * 2;
    }

    public int getDamage(State caster, int damageCoefficient) {
        int lvl = caster.getLevel();

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

    public int getShield(State target, int damageCoefficient) {
        return getDamage(target, damageCoefficient);
    }

    public Integer getNextLevelExp(Integer attributeLevel) {
        Integer exp = 0;

        for (int i = 1; i <= attributeLevel; i++) {
            exp += 50 + i * 33;
        }

        return exp;
    }

    public Integer getCharacterHp() {
        Integer hp = 50;

        for (int i = 1; i <= characterStateService.getCharacterLevel(); i++) {
            hp += 20 + i * game.getGrowthFactor();
        }

        for (int i = 1; i <= characterStateService.getCharacterState().getStrength().getProgressLevel(); i++) {
            hp += 35 + i * game.getGrowthFactor();
        }

        return hp;
    }

    public Integer getCharacterMp() {
        Integer mp = 50;

        for (int i = 1; i <= characterStateService.getCharacterLevel(); i++) {
            mp += 10 + i * game.getGrowthFactor();
        }

        for (int i = 1; i <= characterStateService.getCharacterState().getIntelligence().getProgressLevel(); i++) {
            mp += 45 + i * game.getGrowthFactor();
        }

        return mp;
    }

    public Integer getDeservedExp() {
        return spellService.getLastMovePoints() / 5 * game.getGameRate();
    }

    @Autowired
    private void setCharacterStateService(CharacterStateService characterStateService) {
        this.characterStateService = characterStateService;
    }

    @Autowired
    private void setGame(GameService game) {
        this.game = game;
    }

    @Autowired
    private void setSpellService(SpellService spellService) {
        this.spellService = spellService;
    }
}
