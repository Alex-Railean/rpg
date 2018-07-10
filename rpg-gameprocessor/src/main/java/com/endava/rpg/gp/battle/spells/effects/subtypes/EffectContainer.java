package com.endava.rpg.gp.battle.spells.effects.subtypes;

import com.endava.rpg.gp.battle.spells.effects.Effect;

public interface EffectContainer {

    Effect getInnerEffect();

    EffectContainer setInnerEffect(Effect effect);
}
