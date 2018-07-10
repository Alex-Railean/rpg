package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.EffectContainer;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;

public class Courage extends Passive implements EffectContainer {

    private Effect effect;

    public Courage(State holder, Effect effect) {
        super(holder);
        this.effect = effect;
    }

    @Override
    public void affectTarget() {
        if (BattleService.isActiveTurn() && SpellService.isAttackSpell(SpellService.getLastSpell())) {
            ((Leveled) effect).setLevel(super.getLevel());
            effect.setHolder(CharacterStateService.getCharacter());
            effect.refreshDuration();
            super.getHolder().addEffect(effect);
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
