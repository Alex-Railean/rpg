package com.endava.rpg.gp.talents.talents.technologies;

import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.talents.branches.strength.TechnologiesBranch;
import com.endava.rpg.gp.talents.constants.linknames.TalentLinks;
import com.endava.rpg.gp.talents.constants.names.TalentNames;
import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExoSpine extends Talent {
    private final int HP_COEFFICIENT = 1;

    private final int STUN_RESISTANCE = 2;

    @Autowired
    private ExoSpine(TechnologiesBranch technologies) {
        technologies.addTalent(this);
        setName(TalentNames.EXO_SPINE);
        setLinkName(TalentLinks.EXO_SPINE);
        setURL("");
    }

    @Override
    public void affect() {
        CharacterState character = characterState.getCharacterState();
        int hp = character.getHp().getCurrentValue();

        character.getHp().setValue(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        character.getHp().setCurrentValue(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        Double sr = character.getStunResistancePercentage();

        character.setStunResistancePercentage(sr + STUN_RESISTANCE * getPoints());
        setDescription("Increase your character's health by " + HP_COEFFICIENT + "% and stun resistance by " + STUN_RESISTANCE + "%." +
                "\nHealth bonus: " + HP_COEFFICIENT * getPoints() +
                "\nStung resistance bonus: " + STUN_RESISTANCE * getPoints());
    }

    @Override
    public void define(Character character) {
        setPoints(character.getTechnologies().getExoSpine());
        setLimit(character.getTechnologies().getExoSpineLimit());
    }
}
