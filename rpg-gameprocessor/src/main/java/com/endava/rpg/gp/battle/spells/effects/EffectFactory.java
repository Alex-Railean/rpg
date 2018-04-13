package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.battle.spells.effects.passive.Cursed;
import com.endava.rpg.gp.battle.spells.effects.passive.CursedBlade;
import com.endava.rpg.gp.battle.spells.effects.shields.DefenceStance;
import com.endava.rpg.gp.battle.spells.effects.shields.UnstableShield;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;
import com.endava.rpg.persistence.models.Spell;

public class EffectFactory {
    public Effect createEffect(State target, Spell s) {
        switch (s.getEffectCore().getName()) {
            case EffectName.UNSTABLE_SHIELD:
                return new UnstableShield(target, s);
            case EffectName.DEFENCE_STANCE:
                return new DefenceStance(target, s);
            default:
                throw new IllegalArgumentException("This effect isn't added to the factory");
        }
    }

    public Effect createEffect(State target, String effectName, EffectCore ec) {
        switch (effectName) {
            case EffectName.CURSED_BLADE:
                return new CursedBlade(target, ec);
            case EffectName.CURSED:
                return new Cursed(target, ec);
            default:
                throw new IllegalArgumentException("This effect isn't added to the factory");
        }
    }
}
