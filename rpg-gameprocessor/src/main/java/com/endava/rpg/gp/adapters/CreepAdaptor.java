package com.endava.rpg.gp.adapters;

import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.persistence.models.Creep;
import org.springframework.stereotype.Service;

@Service
public class CreepAdaptor {

    public CreepState toCreepState(Creep creep) {
        Integer lvl = CharacterStateService.getLvl();
        CreepState cs = new CreepState();

        cs.getHp().setValue(FormulaService.getCreepPoints(creep.getHpFactor()))
                .setCurrentValue(FormulaService.getCreepPoints(creep.getHpFactor()))
                .setRegeneration(creep.getHpRegeneration() * lvl);

        cs.getMp().setValue(FormulaService.getCreepPoints(creep.getMpFactor()))
                .setCurrentValue(FormulaService.getCreepPoints(creep.getMpFactor()))
                .setRegeneration(creep.getMpRegeneration() * lvl);

        cs.getEnergy().setValue(creep.getEnergy())
                .setCurrentValue(creep.getEnergy())
                .setRegeneration(creep.getEnergyRegeneration());

        return (CreepState) cs.setCreepType(creep.getCreepType())
                .setName(creep.getCreepName())
                .setLevel(lvl)
                .setSpell(0, creep.getSpell_1())
                .setSpell(1, creep.getSpell_2())
                .setSpell(2, creep.getSpell_3());
    }
}
