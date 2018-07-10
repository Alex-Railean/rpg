package com.endava.rpg.gp.talents.talents.technologies;

import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.talents.branches.strength.TechnologiesBranch;
import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.EXO_SPINE;

@Component
public class ExoSpine extends Talent {

    private final int HP_COEFFICIENT = 1;

    private final int STUN_RESISTANCE = 2;

    @Autowired
    private ExoSpine(TechnologiesBranch technologies) {
        super(technologies, EXO_SPINE.NAME, EXO_SPINE.LINK, EXO_SPINE.URL);
    }

    @Override
    public void affect() {
        CharacterState character = CharacterStateService.getCharacter();
        int hp = character.getHp().getCurrentValue();

        character.getHp().setValue(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        character.getHp().setCurrentValue(character.getHp().getValue());
        Double sr = character.getStunResistance();

        character.setStunResistance(sr + STUN_RESISTANCE * getPoints());

        super.setDescription(EXO_SPINE.NAME +
                "\nIncrease the character's health by " + HP_COEFFICIENT + "% and stun resistance by " + STUN_RESISTANCE + "%" +
                "\nHealth bonus: " + HP_COEFFICIENT * getPoints() + "%" +
                "\nStun resistance bonus: " + STUN_RESISTANCE * getPoints() + "%");
    }

    @Override
    public void define(Character character) {
        super.setPoints(character.getTechnologies().getExoSpine());
        super.setLimit(character.getTechnologies().getExoSpineLimit());
    }
}
