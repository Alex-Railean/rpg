package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.InnerEffected;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public class CursedBlade extends Effect implements InnerEffected {

    private int level;

    private Effect effect;

    public CursedBlade(State holder, EffectCore ec) {
        super(holder, ec);
    }

    @Override
    public void affectTarget() {
        if (BattleService.isActiveTurn()) {
            State creep = EnemyService.getCurrentEnemy();
            ((Leveled) effect).setLevel(level);
            effect.setHolder(creep);
            effect.refreshDuration();
            creep.addEffect(effect);
            CombatTextService.createEffectMessage(creep, effect);
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

    @Override
    public Effect getEffect() {
        return effect;
    }

    @Override
    public CursedBlade setInnerEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
