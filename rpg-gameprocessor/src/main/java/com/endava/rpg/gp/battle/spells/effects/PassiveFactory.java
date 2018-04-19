package com.endava.rpg.gp.battle.spells.effects;

import com.endava.rpg.gp.battle.spells.effects.passive.Courage;
import com.endava.rpg.gp.battle.spells.effects.passive.CursedBlade;
import com.endava.rpg.gp.battle.spells.effects.roots.Effect;
import com.endava.rpg.gp.battle.spells.effects.roots.Passive;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.services.utils.constants.EffectName;

public class PassiveFactory {
    public static Passive createPassive(State holder, Effect effect) {
        switch (effect.getName()) {
            case EffectName.GRACE_OF_COURAGE:
                return new Courage(holder, effect);
            case EffectName.CURSED:
                return new CursedBlade(holder, effect);
            default:
                throw new IllegalArgumentException("This effect isn't added to the factory");
        }
    }
}
