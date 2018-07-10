package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.ExpService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.constants.AttributeType;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Experienced;
import com.endava.rpg.gp.statemodels.State;

public class PlagueAura extends Passive implements Experienced {

    private Integer totalDmg = 0;

    public PlagueAura(State holder) {
        super(holder);
    }

    @Override
    public void affectTarget() {
        EnemyService.getCreepGroup()
                .forEach(c -> {
                    int dmg = getHpToSubtract(c);
                    c.getHp().subtractCurrentValue(dmg);
                    totalDmg += dmg;
                });

        expPolicy();
    }

    private int getHpToSubtract(State c) {
        Double dmg = c.getHp().getCurrentValue() / 20.0 * super.getLevel();
        return dmg.intValue();
    }

    @Override
    public void expPolicy() {
        ExpService.addAttributeExp(AttributeType.STRENGTH, totalDmg);
        totalDmg = 0;
    }
}
