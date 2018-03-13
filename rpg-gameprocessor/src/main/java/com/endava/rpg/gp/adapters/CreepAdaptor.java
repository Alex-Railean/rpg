package com.endava.rpg.gp.adapters;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Creep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreepAdaptor {

    private final CharacterStateService CHAR_STATE;

    private final FormulaService FORMULA;

    @Autowired
    public CreepAdaptor(CharacterStateService charState, FormulaService formula) {
        this.CHAR_STATE = charState;
        this.FORMULA = formula;
    }

    public CreepState toCreepState(Creep creep){
        Integer lvl = CHAR_STATE.getCharacterState().getCharacterLevel();
        return (CreepState) new CreepState()
                .setCreepType(creep.getCreepType())
                .setCreepName(creep.getCreepName())
                .setCreepLevel(lvl)
                .setHp(FORMULA.getCreepPoints(creep.getHpFactor()))
                .setCurrentHp(FORMULA.getCreepPoints(creep.getHpFactor()))
                .setHpRegeneration(creep.getHpRegeneration() * lvl)
                .setMp(FORMULA.getCreepPoints(creep.getMpFactor()))
                .setCurrentMp(FORMULA.getCreepPoints(creep.getMpFactor()))
                .setMpRegeneration(creep.getMpRegeneration() * lvl)
                .setEnergy(creep.getEnergy())
                .setCurrentEnergy(creep.getEnergy())
                .setEnergyRegeneration(creep.getEnergyRegeneration())
                .setSpell_1(creep.getSpell_1())
                .setSpell_2(creep.getSpell_2())
                .setSpell_3(creep.getSpell_3());
    }
}
