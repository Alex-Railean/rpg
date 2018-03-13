package com.endava.rpg.gp.services.game;

import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.battle.SpellService;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {

    private CharacterStateService characterStateService;

    private GameService game;

    private SpellService spellService;

    public Integer getCreepPoints(Integer factor) {
        Integer hp = factor * 5;

        for (int i = 1; i <= characterStateService.getCharacterState().getCharacterLevel(); i++) {
            hp += factor + i * game.getGrowthFactor();
        }

        return hp;
    }

    public Integer getManaCost(Spell spell) {
        return spell.getCost() +
                characterStateService.getCharacterState().getCharacterLevel() +
                game.getGrowthFactor() * 2;
    }

    public int getDamage(int damageCoefficient) {
        int characterLevel = characterStateService.getCharacterState().getCharacterLevel();
        double levelReducer = 0.1;

        for (int i = 1; i <= characterLevel; i++) {
            damageCoefficient += damageCoefficient * levelReducer;
            levelReducer *= 0.993;
        }

        return damageCoefficient;
    }

    public int getShield(int damageCoefficient) {
        return getDamage(damageCoefficient);
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

        for (int i = 1; i <= characterStateService.getCharacterState().getCharacterLevel(); i++) {
            hp += 20 + i * game.getGrowthFactor();
        }

        for (int i = 1; i <= characterStateService.getCharacterState().getStrengthProgressLevel(); i++) {
            hp += 35 + i * game.getGrowthFactor();
        }

        return hp;
    }

    public Integer getCharacterMp() {
        Integer mp = 50;

        for (int i = 1; i <= characterStateService.getCharacterState().getCharacterLevel(); i++) {
            mp += 10 + i * game.getGrowthFactor();
        }

        for (int i = 1; i <= characterStateService.getCharacterState().getIntelligenceProgressLevel(); i++) {
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
