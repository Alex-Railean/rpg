package com.endava.rpg.gp.talents.talents.technologies;

import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.talents.branches.strength.TechnologiesBranch;
import com.endava.rpg.gp.talents.talents.Talent;
import com.endava.rpg.persistence.models.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.gp.talents.constants.TalentAttribute.MUSCLE_STIMULANTS;

@Component
public class MuscleStimulants extends Talent {
    private final int COEFFICIENT = 5;

    @Autowired
    private MuscleStimulants(TechnologiesBranch technologies) {
        technologies.addTalent(this);
        setName(MUSCLE_STIMULANTS.NAME);
        setLinkName(MUSCLE_STIMULANTS.LINK);
        setURL("");
    }

    @Override
    public void affect() {
        CharacterState character = characterState.getCharacterState();
        character.getEnergy().setValue(character.getDefaultEnergy() + COEFFICIENT * getPoints());
        character.getEnergy().setCurrentValue(character.getDefaultEnergy() + COEFFICIENT * getPoints());
        setDescription("Increase the character's energy by " + COEFFICIENT + "." +
                "\nEnergy bonus: " + COEFFICIENT * getPoints());
    }

    @Override
    public void define(Character character) {
        setPoints(character.getTechnologies().getMuscleStimulants());
        setLimit(character.getTechnologies().getMuscleStimulantsLimit());
    }
}
