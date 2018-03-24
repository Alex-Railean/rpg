package com.endava.rpg.gp.battle.spells.description;

import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.utils.DescribedSpell;

public class EffectDescription implements DescribedSpell {
    private final DescribedSpell describedSpell;

    public EffectDescription(DescribedSpell d) {
        this.describedSpell = d;
    }

    @Override
    public Spell getSpell() {
        return describedSpell.getSpell();
    }

    // TODO: After effects implementation -> Add effect description
    @Override
    public String getDescription() {
        return describedSpell.getDescription() + "\n" + getSpell().getEffectId();
    }
}
