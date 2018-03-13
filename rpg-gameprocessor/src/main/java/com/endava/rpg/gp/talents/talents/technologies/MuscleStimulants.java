package com.endava.rpg.gp.talents.talents.technologies;

import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.talents.linknames.TalentLinks;
import com.endava.rpg.gp.talents.names.TalentNames;
import com.endava.rpg.gp.talents.talents.Talent;
import org.springframework.stereotype.Component;

@Component
public class MuscleStimulants extends Talent {
    private final int COEFFICIENT = 5;

    private MuscleStimulants(){
        setName(TalentNames.MUSCLE_STIMULANTS);
        setLinkName(TalentLinks.MUSCLE_STIMULANTS);
        setURL("");
    }

    @Override
    public void affect() {
        CharacterState character = characterState.getCharacterState();
        character.setEnergy(character.getDefaultEnergy() + COEFFICIENT * getPoints());
        character.setCurrentEnergy(character.getDefaultEnergy() + COEFFICIENT * getPoints());
        setDescription("Increase the character's energy by " + COEFFICIENT + "." +
                "\nEnergy bonus: " + COEFFICIENT * getPoints());
    }
}
