package com.endava.rpg.gp.services.game;

import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellService;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {

    @Autowired
    private CharacterStateService CHAR_STATE_SERVICE;

    @Autowired
    private GameService GAME;

    @Autowired
    private SpellService SPELL_SERVICE;

    // TODO: To investigate the problem
//    @Autowired
//    public FormulaService(CharacterStateService characterStateService, GameService gameService, SpellService spellService) {
//        this.CHARACTER_STATE = characterStateService;
//        this.GAME = gameService;
//        this.SPELL_SERVICE = spellService;
//    }

    public Integer getCreepPoints(Integer factor){
        Integer hp = factor * 5;

        for(int i = 1; i <= CHAR_STATE_SERVICE.getCharacterState().getCharacterLevel(); i++){
            hp += factor + i * GAME.getGrowthFactor();
        }

        return hp;
    }

    public Integer getManaCost(Spell spell){
        return spell.getCost() +
                CHAR_STATE_SERVICE.getCharacterState().getCharacterLevel() +
                        GAME.getGrowthFactor() * 2;
    }

    public int getDamage(int damageCoefficient) {
        int characterLevel = CHAR_STATE_SERVICE.getCharacterState().getCharacterLevel();
        double levelReducer = 0.1;

        for (int i = 1; i <= characterLevel; i++) {
            damageCoefficient += damageCoefficient * levelReducer;
            levelReducer *= 0.993;
        }

        return damageCoefficient;
    }

    public int getShield(int damageCoefficient){
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

        for (int i = 1; i <= CHAR_STATE_SERVICE.getCharacterState().getCharacterLevel(); i++) {
            hp += 20 + i * GAME.getGrowthFactor();
        }

        for (int i = 1; i <= CHAR_STATE_SERVICE.getCharacterState().getStrengthProgressLevel(); i++) {
            hp += 35 + i * GAME.getGrowthFactor();
        }

        return hp;
    }

    public Integer getCharacterMp() {
        Integer mp = 50;

        for (int i = 1; i <= CHAR_STATE_SERVICE.getCharacterState().getCharacterLevel(); i++) {
            mp += 10 + i * GAME.getGrowthFactor();
        }

        for (int i = 1; i <= CHAR_STATE_SERVICE.getCharacterState().getIntelligenceProgressLevel(); i++) {
            mp += 45 + i * GAME.getGrowthFactor();
        }

        return mp;
    }

    public Integer getDeservedExp() {
        return SPELL_SERVICE.getLastMovePoints() / 5 * GAME.getGameRate();
    }
}
