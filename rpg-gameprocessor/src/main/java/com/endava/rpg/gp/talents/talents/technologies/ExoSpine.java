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
        technologies.addTalent(this);
        setName(EXO_SPINE.NAME);
        setLinkName(EXO_SPINE.LINK);
        setURL("/resources/img/exo-spine.jpg");
    }

    @Override
    public void affect() {
        CharacterState character = CharacterStateService.getCharacter();
        int hp = character.getHp().getCurrentValue();

        character.getHp().setValue(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        character.getHp().setCurrentValue(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        Double sr = character.getStunResistancePercentage();

        character.setStunResistancePercentage(sr + STUN_RESISTANCE * getPoints());
        setDescription("Exo-spine. " +
                "\nIncrease the character's health by " + HP_COEFFICIENT + "% and stun resistance by " + STUN_RESISTANCE + "%." +
                "\nHealth bonus: " + HP_COEFFICIENT * getPoints() + "%" +
                "\nStung resistance bonus: " + STUN_RESISTANCE * getPoints() + "%");
    }

    @Override
    public void define(Character character) {
        setPoints(character.getTechnologies().getExoSpine());
        setLimit(character.getTechnologies().getExoSpineLimit());
    }
}
