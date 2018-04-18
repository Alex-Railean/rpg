package com.endava.rpg.gp.battle.spells.effects.subtypes;

import com.endava.rpg.gp.battle.spells.effects.Effect;

public interface InnerEffected extends Leveled {
    Effect getEffect();

    Leveled setInnerEffect(Effect effect);
}
