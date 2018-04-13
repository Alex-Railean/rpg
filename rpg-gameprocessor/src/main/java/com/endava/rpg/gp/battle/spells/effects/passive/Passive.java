package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.spells.effects.Effect;

public interface Passive {
    Effect getEffect();

    void setEffect(Effect effect);

    int getLevel();

    void setLevel(int level);
}
