package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.EffectContainer;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.statemodels.State;

public class CursedBlade extends Passive implements EffectContainer {

    private Effect effect;

    public CursedBlade(State holder, Effect effect) {
        super(holder);
        this.effect = effect;
    }

    @Override
    public void affectTarget() {
        if (BattleService.isActiveTurn()) {
            State creep = EnemyService.getCurrentEnemy();
            ((Leveled) effect).setLevel(getLevel());
            effect.setHolder(creep);
            effect.refreshDuration();
            creep.addEffect(effect);
        }
    }

    @Override
    public Effect getInnerEffect() {
        return effect;
    }

    @Override
    public EffectContainer setInnerEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
