package com.endava.rpg.gp.talents.talents.aspects;

import com.endava.rpg.gp.talents.branches.strength.AspectsBranch;
import com.endava.rpg.gp.talents.talents.PassiveTalent;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.constants.EffectName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.CURSED_BLADE;

@Component
public class CursedBladeTalent extends PassiveTalent {

    @Autowired
    private CursedBladeTalent(AspectsBranch aspects, PersistenceService ps) {
        super(aspects, CURSED_BLADE.NAME, CURSED_BLADE.LINK, CURSED_BLADE.URL, ps);
    }

    @Override
    public void affect() {
        if (getPoints() > 0) {
            super.addPassive(EffectName.CURSED);
        }

        super.setDescription(CURSED_BLADE.NAME +
                "\nAfter using the attacking spell, its target will be under effect " + EffectName.CURSED +
                ". \n" + EffectName.CURSED + " effect reduces dmg of spells" +
                (super.getPoints() > 0 ? "Next level will be more effective" : ""));
    }

    @Override
    public void define(Character character) {
        super.setPoints(character.getAspects().getCursedBlade());
        super.setLimit(character.getAspects().getCursedBladeLimit());
    }
}
