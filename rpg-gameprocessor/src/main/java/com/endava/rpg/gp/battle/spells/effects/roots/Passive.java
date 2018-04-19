package com.endava.rpg.gp.battle.spells.effects.roots;

import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.statemodels.State;

public abstract class Passive extends Affecting implements Leveled {

    private Effect effect;

    public Passive(State holder, Effect effect) {
        super(holder);
        this.effect = effect;
    }

    public Effect getInnerEffect() {
        return effect;
    }

    public Passive setInnerEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
