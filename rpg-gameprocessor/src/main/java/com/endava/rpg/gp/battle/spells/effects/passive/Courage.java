package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.InnerEffected;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public class Courage extends Effect implements InnerEffected {

    private int level;

    private Effect effect;

    public Courage(State holder, EffectCore ec) {
        super(holder, ec);
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
            ((Leveled) effect).setLevel(level);
            effect.setHolder(CharacterStateService.getCharacter());
            effect.refreshDuration();
            super.getHolder().addEffect(effect);
            CombatTextService.createEffectMessage(super.getHolder(), effect);
        }
    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    @Override
    public Courage setInnerEffect(Effect effect) {
        this.effect = effect;
        return this;
    }
}
