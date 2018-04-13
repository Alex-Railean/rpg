package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public class CursedBlade extends Effect implements Passive {

    private int level;

    private Effect effect;

    public CursedBlade(State holder, EffectCore ec) {
        super(holder, ec);
    }

    @Override
    public void affectTarget() {
        if (BattleService.isActiveTurn()) {
            State creep = EnemyService.getCurrentEnemy();
            ((Passive) this.effect).setLevel(this.level);
            effect.setHolder(creep);
            effect.refreshDuration();
            creep.addEffect(this.effect);
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    @Override
    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
