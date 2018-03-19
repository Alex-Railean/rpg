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

    public CreepState toCreepState(Creep creep) {
        Integer lvl = CHAR_STATE.getCharacterState().getLevel();
        CreepState cs = new CreepState();

        cs.getHp().setValue(FORMULA.getCreepPoints(creep.getHpFactor()))
                .setCurrentValue(FORMULA.getCreepPoints(creep.getHpFactor()))
                .setRegeneration(creep.getHpRegeneration() * lvl);

        cs.getMp().setValue(FORMULA.getCreepPoints(creep.getMpFactor()))
                .setCurrentValue(FORMULA.getCreepPoints(creep.getMpFactor()))
                .setRegeneration(creep.getMpRegeneration() * lvl);

        cs.getEnergy().setValue(creep.getEnergy())
                .setCurrentValue(creep.getEnergy())
                .setRegeneration(creep.getEnergyRegeneration());

        return (CreepState) cs.setCreepType(creep.getCreepType())
                .setName(creep.getCreepName())
                .setLevel(lvl)
                .setSpell_1(creep.getSpell_1())
                .setSpell_2(creep.getSpell_2())
                .setSpell_3(creep.getSpell_3());
    }
}
