package com.endava.rpg.gp.talents.talents.aspects;

import com.endava.rpg.gp.battle.spells.effects.passive.PlagueAura;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.talents.branches.strength.AspectsBranch;
import com.endava.rpg.gp.talents.talents.PassiveTalent;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.PLAGUE_AURA;

@Component
public class PlagueAuraTalent extends PassiveTalent {

    private final float COEFFICIENT = 0.5F;

    @Autowired
    protected PlagueAuraTalent(AspectsBranch aspects, PersistenceService ps) {
        super(aspects, PLAGUE_AURA.NAME, PLAGUE_AURA.LINK, PLAGUE_AURA.URL, ps);
    }

    @Override
    public void affect() {
        super.addPassive(new PlagueAura(CharacterStateService.getCharacter()));

        super.setDescription(PLAGUE_AURA.NAME +
                "\nAll your enemies get damage each turn " +
                "\nDmg -> " + COEFFICIENT + "% of current HP for each level" +
                "\nCurrent damage: " + COEFFICIENT * super.getPoints());
    }

    @Override
    public void define(Character character) {
        super.setPoints(character.getAspects().getPlagueAura());
        super.setLimit(character.getAspects().getPlagueAuraLimit());
    }
}
