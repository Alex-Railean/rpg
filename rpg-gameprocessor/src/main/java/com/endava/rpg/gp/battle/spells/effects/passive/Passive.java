package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.spells.effects.Affecting;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.statemodels.State;

public abstract class Passive extends Affecting implements Leveled {

    private Effect effect;

    Passive(State holder, Effect effect) {
        super(holder);
        this.effect = effect;
    }

    Effect getInnerEffect() {
        return effect;
    }

    public Passive setInnerEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
