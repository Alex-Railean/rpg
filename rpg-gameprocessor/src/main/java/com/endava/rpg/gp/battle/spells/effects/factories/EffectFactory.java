package com.endava.rpg.gp.battle.spells.effects.factories;

import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.shields.DefenceStance;
import com.endava.rpg.gp.battle.spells.effects.shields.GraceOfCourage;
import com.endava.rpg.gp.battle.spells.effects.shields.UnstableShield;
import com.endava.rpg.gp.battle.spells.effects.stuns.SanctifiedWrath;
import com.endava.rpg.gp.battle.spells.effects.targeted.Cursed;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.utils.constants.EffectName;

public class EffectFactory {

    public static Effect createEffect(State target, Spell s) {
        switch (s.getEffectCore().getName()) {
            case EffectName.UNSTABLE_SHIELD:
                return new UnstableShield(target, s);
            case EffectName.DEFENCE_STANCE:
                return new DefenceStance(target, s);
            case EffectName.SANCTIFIED_WRATH:
                return new SanctifiedWrath(target, s);
            default:
                throw new IllegalArgumentException("This effect isn't added to the factory");
        }
    }

    public static Effect createEffect(State target, EffectCore ec) {
        switch (ec.getName()) {
            case EffectName.CURSED:
                return new Cursed(target, ec);
            case EffectName.GRACE_OF_COURAGE:
                return new GraceOfCourage(target, ec);
            default:
                throw new IllegalArgumentException("This effect isn't added to the factory");
        }
    }
}
