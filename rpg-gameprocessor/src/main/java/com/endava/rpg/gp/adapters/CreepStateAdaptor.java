package com.endava.rpg.gp.adapters;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Creep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreepStateAdaptor {

    @Autowired
    private CharacterStateService characterStateService;

    @Autowired
    private FormulaService formulaService;

    public CreepState toCreepState(Creep creepDBModel){
        return new CreepState()
                .setCreepName(creepDBModel.getCreepName())
                .setCreepLevel(characterStateService.getCharacterState().getCharacterLevel())
                .setHp(formulaService.getCreepPoints(creepDBModel.getHpFactor()))
                .setCurrentHp(formulaService.getCreepPoints(creepDBModel.getHpFactor()))
                .setHpRegeneration(creepDBModel.getHpRegeneration()
                        * characterStateService.getCharacterState().getCharacterLevel())
                .setMp(formulaService.getCreepPoints(creepDBModel.getMpFactor()))
                .setCurrentMp(formulaService.getCreepPoints(creepDBModel.getMpFactor()))
                .setMpRegeneration(creepDBModel.getMpRegeneration()
                        * characterStateService.getCharacterState().getCharacterLevel())
                .setEnergy(creepDBModel.getEnergy())
                .setCurrentEnergy(creepDBModel.getEnergy())
                .setEnergyRegeneration(creepDBModel.getEnergyRegeneration())
                .setSpell_1(creepDBModel.getSpell_1())
                .setSpell_2(creepDBModel.getSpell_2())
                .setSpell_3(creepDBModel.getSpell_3())
                .setCreepType(creepDBModel.getCreepType());
    }
}
