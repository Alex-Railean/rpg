package com.endava.rpg.gp.battle.spells.effects.targeted;

import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.battle.spells.effects.subtypes.Leveled;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public class Cursed extends Effect implements Leveled {

    private int level;

    private boolean affected = false;

    public Cursed(State holder, EffectCore ec) {
        super(holder, ec);
    }

    @Override
    public void affectTarget() {
        if (!affected) {
            getHolder().getSpells()
                    .forEach(s -> {
                        if (SpellService.isAttackSpell(s)) {
                            s.subtractCoefficient(level);
                        }
                    });
        }

        affected = true;
    }

    @Override
    public void remove() {
        if (affected) {
            getHolder().getSpells()
                    .forEach(s -> {
                        if (SpellService.isAttackSpell(s)) {
                            s.addCoefficient(level);
                        }
                    });
        }

        affected = false;
        super.remove();
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Cursed setLevel(int level) {
        this.level = level;
        return this;
    }
}
