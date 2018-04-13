package com.endava.rpg.gp.battle.spells.effects.passive;

import com.endava.rpg.gp.battle.spells.constants.SpellType;
import com.endava.rpg.gp.battle.spells.effects.Effect;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.EffectCore;

public class Cursed extends Effect implements Passive {

    private int level;

    private Effect effect;

    private boolean affected = false;

    public Cursed(State holder, EffectCore ec) {
        super(holder, ec);
    }

    @Override
    public void affectTarget() {
        if (!affected) {
            getHolder().getSpells()
                    .forEach(s -> {
                        if (s.getSpellType().equals(SpellType.ATTACK)) {
                            s.subtractCoefficient(this.level);
                        }
                    });
        }
        affected = true;
    }

    @Override
    public boolean remove() {
        if (affected) {
            getHolder().getSpells()
                    .forEach(s -> {
                        if (s.getSpellType().equals(SpellType.ATTACK)) {
                            s.addCoefficient(this.level);
                        }
                    });
        }

        affected = false;
        return super.remove();
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
