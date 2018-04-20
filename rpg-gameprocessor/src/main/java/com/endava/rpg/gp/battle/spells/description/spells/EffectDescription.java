package com.endava.rpg.gp.battle.spells.description.spells;

import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.utils.DescribedSpell;

import java.util.List;
import java.util.stream.Collectors;

public class EffectDescription implements DescribedSpell {

    private final DescribedSpell describedSpell;

    public EffectDescription(DescribedSpell d) {
        this.describedSpell = d;
    }

    public static List<DescribedSpell> toEffectDescription(List<DescribedSpell> spells) {
        return spells.stream().map(EffectDescription::new).collect(Collectors.toList());
    }

    @Override
    public Spell getSpell() {
        return describedSpell.getSpell();
    }

    @Override
    public String getDescription() {
        if (getSpell().getEffectCore() != null) {
            return describedSpell.getDescription() + "\n" + getSpell().getEffectCore().getDescription();
        } else {
            return describedSpell.getDescription();
        }
    }
}
