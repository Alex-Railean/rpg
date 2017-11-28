package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.statemodels.CurrentCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

    @Autowired
    private SpellService spellService;

    @Autowired
    private LocationCreepService locationCreepService;

    @Autowired
    private CharacterStateService characterStateService;

    public boolean isEndOfBattle() {
        return characterStateService.characterIsDead() || locationCreepService.getCreepGroup().size() == 0;
    }

    public void makeATurn(Integer actionBarNumber, CreepState currentEnemy){
        spellService.useSpellToEnemy(actionBarNumber, currentEnemy);
        seekDeath();
        useRegeneration();
    }

    public void waitATurn(){
        seekDeath();
        useRegeneration();
    }

    private void useRegeneration(){
        CurrentCharacter character = characterStateService.getCharacterState();
        for(CreepState creep : locationCreepService.getCreepGroup()){
            if(creep.getCurrentHp() < creep.getHp()) {
                creep.setCurrentHp(creep.getCurrentHp() + creep.getHpRegeneration() >= creep.getHp() ?
                        creep.getHp() :
                        creep.getCurrentHp() + creep.getHpRegeneration());
            }

            if(creep.getCurrentMp() < creep.getMp()) {
                creep.setCurrentMp(creep.getCurrentMp() + creep.getMpRegeneration() >= creep.getMp() ?
                        creep.getMp() :
                        creep.getCurrentMp() + creep.getMpRegeneration());
            }

            if(creep.getCurrentEnergy() < creep.getEnergy()) {
                creep.setCurrentEnergy(creep.getCurrentEnergy() + creep.getEnergyRegeneration() >= creep.getEnergy() ?
                        creep.getEnergy() :
                        creep.getCurrentEnergy() + creep.getEnergyRegeneration());
            }
        }

        if(character.getCurrentHp() < character.getHp()){
            character.setCurrentHp(character.getCurrentHp() + character.getHpRegeneration() >= character.getHp() ?
                    character.getHp() :
                    character.getCurrentHp() + character.getHpRegeneration());
        }

        if(character.getCurrentMp() < character.getMp()){
            character.setCurrentMp(character.getCurrentMp() + character.getMpRegeneration() >= character.getMp() ?
                    character.getMp() :
                    character.getCurrentMp() + character.getMpRegeneration());
        }

        if(character.getCurrentEnergy() < character.getEnergy()){
            character.setCurrentEnergy(character.getCurrentEnergy() + character.getEnergyRegeneration() >= character.getEnergy() ?
                    character.getEnergy() :
                    character.getCurrentEnergy() + character.getEnergyRegeneration());
        }
    }

    private void seekDeath(){
        if(locationCreepService.currentEnemyIsDead()){
            locationCreepService.getCreepGroup().remove(locationCreepService.getCurrentEnemy());
        }
    }
}
