package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.services.responsiveness.SpellChoiceService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.util.Refreshable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService implements Refreshable {

    private final SpellService SPELL_SERVICE;

    private final LocationService CREEP_LOCATION;

    private final CharacterStateService CHARACTER_STATE_SERVICE;

    private final SpellChoiceService SPELL_CHOICE;

    private Long battleId;

    @Autowired
    private BattleService(SpellService spellService, LocationService creepLocation, CharacterStateService characterState, SpellChoiceService spellChoice) {
        this.SPELL_SERVICE = spellService;
        this.CREEP_LOCATION = creepLocation;
        this.CHARACTER_STATE_SERVICE = characterState;
        this.SPELL_CHOICE = spellChoice;
    }

    public boolean isEndOfBattle() {
        return CHARACTER_STATE_SERVICE.isCharacterDead() || CREEP_LOCATION.getCreepGroup().size() == 0;
    }

    public void makeATurn(Integer actionBarNumber, CreepState currentEnemy) {
        SPELL_SERVICE.useSpellTo(actionBarNumber, currentEnemy);
        SPELL_CHOICE.creepResponse();
        seekDeath();
        useRegeneration();
    }

    public void waitATurn() {
        SPELL_CHOICE.creepResponse();
        seekDeath();
        useRegeneration();
    }

    private void useRegeneration() {
        CharacterState character = CHARACTER_STATE_SERVICE.getCharacterState();
        CREEP_LOCATION.getCreepGroup()
                .forEach(creep -> {
                    if (creep.getCurrentHp() < creep.getHp()) {
                        creep.setCurrentHp(creep.getCurrentHp() + creep.getHpRegeneration() >= creep.getHp() ?
                                creep.getHp() :
                                creep.getCurrentHp() + creep.getHpRegeneration());
                    }

                    if (creep.getCurrentMp() < creep.getMp()) {
                        creep.setCurrentMp(creep.getCurrentMp() + creep.getMpRegeneration() >= creep.getMp() ?
                                creep.getMp() :
                                creep.getCurrentMp() + creep.getMpRegeneration());
                    }

                    if (creep.getCurrentEnergy() < creep.getEnergy()) {
                        creep.setCurrentEnergy(creep.getCurrentEnergy() + creep.getEnergyRegeneration() >= creep.getEnergy() ?
                                creep.getEnergy() :
                                creep.getCurrentEnergy() + creep.getEnergyRegeneration());
                    }
                });

        if (character.getCurrentHp() < character.getHp()) {
            character.setCurrentHp(character.getCurrentHp() + character.getHpRegeneration() >= character.getHp() ?
                    character.getHp() :
                    character.getCurrentHp() + character.getHpRegeneration());
        }

        if (character.getCurrentMp() < character.getMp()) {
            character.setCurrentMp(character.getCurrentMp() + character.getMpRegeneration() >= character.getMp() ?
                    character.getMp() :
                    character.getCurrentMp() + character.getMpRegeneration());
        }

        if (character.getCurrentEnergy() < character.getEnergy()) {
            character.setCurrentEnergy(character.getCurrentEnergy() + character.getEnergyRegeneration() >= character.getEnergy() ?
                    character.getEnergy() :
                    character.getCurrentEnergy() + character.getEnergyRegeneration());
        }
    }

    private void seekDeath() {
        if (CREEP_LOCATION.isCurrentEnemyDead()) {
            CREEP_LOCATION.getCreepGroup().remove(CREEP_LOCATION.getCurrentEnemy());
        }
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }

    @Override
    public void refresh() {
        battleId = 0L;
    }
}
