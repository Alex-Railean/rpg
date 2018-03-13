package com.endava.rpg.gp.talents.talents.technologies;

import com.endava.rpg.gp.statemodels.CharacterState;
import com.endava.rpg.gp.talents.linknames.TalentLinks;
import com.endava.rpg.gp.talents.names.TalentNames;
import com.endava.rpg.gp.talents.talents.Talent;
import org.springframework.stereotype.Component;

@Component
public class ExoSpine extends Talent {
    private final int HP_COEFFICIENT = 1;

    private final int STUN_RESISTANCE = 2;

    private ExoSpine() {
        setName(TalentNames.EXO_SPINE);
        setLinkName(TalentLinks.EXO_SPINE);
        setURL("");
    }

    @Override
    public void affect() {
        CharacterState character = characterState.getCharacterState();
        int hp = character.getCurrentHp();
        character.setHp(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        character.setCurrentHp(hp + hp / 100 * HP_COEFFICIENT * getPoints());
        Double sr = character.getStunResistancePercentage();
        character.setStunResistancePercentage(sr + STUN_RESISTANCE * getPoints());
        setDescription("Increase your character's health by " + HP_COEFFICIENT + "% and stun resistance by " + STUN_RESISTANCE + "%." +
                "\nHealth bonus: " + HP_COEFFICIENT * getPoints() +
                "\nStung resistance bonus: " + STUN_RESISTANCE * getPoints());
    }
}
