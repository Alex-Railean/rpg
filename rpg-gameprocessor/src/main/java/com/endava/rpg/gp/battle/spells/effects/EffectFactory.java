package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.battle.spells.effects.shields.DefenceStance;
import com.endava.rpg.gp.battle.spells.effects.shields.UnstableShield;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.Spell;

public class EffectFactory {
    public Effect createEffect(State target, Spell s) {
        switch (s.getEffectCore().getName()) {
            case "Unstable Shield":
                return new UnstableShield(target, s);
            case "Defence Stance":
                return new DefenceStance(target, s);
            default:
                throw new IllegalArgumentException("This effect isn't added to the factory");
        }
    }
}
