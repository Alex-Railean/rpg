package com.endava.rpg.gp.talents.talents.aspects;

import com.endava.rpg.gp.talents.branches.strength.AspectsBranch;
import com.endava.rpg.gp.talents.talents.PassiveTalent;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.constants.EffectName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.COURAGE;

@Component
public class CourageTalent extends PassiveTalent {

    private final int COEFICIENT = 2;

    @Autowired
    private CourageTalent(AspectsBranch aspects, PersistenceService ps) {
        super(ps);
        aspects.addTalent(this);
        setName(COURAGE.NAME);
        setLinkName(COURAGE.LINK);
        setURL("/resources/img/courage.jpg");
    }

    @Override
    public void affect() {
        if (getPoints() > 0) {
            super.addPassive(EffectName.GRACE_OF_COURAGE);
        }

        super.setDescription(COURAGE.NAME +
                "\nAfter using the attacking spell you will be under effect " + EffectName.GRACE_OF_COURAGE + "." +
                "\n" + EffectName.GRACE_OF_COURAGE + " gives shield in amount 2% of your maximum health" +
                "\nCurrent shield: " + COEFICIENT * super.getPoints() + "%");
    }

    @Override
    public void define(Character character) {
        setPoints(character.getAspects().getCourage());
        setLimit(character.getAspects().getCourageLimit());
    }
}
