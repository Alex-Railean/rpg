package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.effects.roots.Effect;
import com.endava.rpg.gp.battle.spells.effects.roots.Passive;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.statemodels.State;

public class CursedBlade extends Passive implements Leveled {

    private int level;

    public CursedBlade(State holder, Effect effect) {
        super(holder, effect);
    }

    @Override
    public void affectTarget() {
        if (BattleService.isActiveTurn()) {
            Effect innerEffect = super.getInnerEffect();
            State creep = EnemyService.getCurrentEnemy();
            ((Leveled) innerEffect).setLevel(level);
            innerEffect.setHolder(creep);
            innerEffect.refreshDuration();
            creep.addEffect(innerEffect);
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public CursedBlade setLevel(int level) {
        this.level = level;
        return this;
    }
}
