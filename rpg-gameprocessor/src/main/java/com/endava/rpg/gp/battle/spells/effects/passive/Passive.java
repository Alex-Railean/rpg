package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.spells.effects.Affecting;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.statemodels.State;

public abstract class Passive extends Affecting implements Leveled {

    private int level;

    Passive(State holder) {
        super(holder);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Passive setLevel(int level) {
        this.level = level;
        return this;
    }
}
