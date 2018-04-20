package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;

public class Courage extends Passive implements Leveled {

    private int level;

    public Courage(State holder, Effect effect) {
        super(holder, effect);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Courage setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public void affectTarget() {
        if (BattleService.isActiveTurn() && SpellService.isAttackSpell(SpellService.getLastSpell())) {
            Effect innerEffect = super.getInnerEffect();
            ((Leveled) innerEffect).setLevel(level);
            innerEffect.setHolder(CharacterStateService.getCharacter());
            innerEffect.refreshDuration();
            super.getHolder().addEffect(innerEffect);
        }
    }
}
