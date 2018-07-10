package com.endava.rpg.gp.battle;

import com.endava.rpg.gp.battle.spells.constants.AttributeType;
import com.endava.rpg.gp.game.FormulaService;
import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.util.Refreshable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ExpService implements Refreshable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpService.class);

    private static Integer strengthEpx = 0;

    private static Integer agilityExp = 0;

    private static Integer intellectExp = 0;

    private final CharacterStateService CHAR_STATE;

    @Autowired
    private ExpService(CharacterStateService characterStateService) {
        Refresher.addRefreshable(this);
        this.CHAR_STATE = characterStateService;
    }

    // TODO: investigate -> if there is a better approach
    public static void addAttributeExp(String attributeName) {
        switch (attributeName) {
            case AttributeType.STRENGTH:
                strengthEpx += FormulaService.getDeservedExp();
                LOGGER.info("Strength Epx -> " + strengthEpx);
                break;

            case AttributeType.AGILITY:
                agilityExp += FormulaService.getDeservedExp();
                LOGGER.info("Agility Exp -> " + agilityExp);
                break;

            case AttributeType.INTELLECT:
                intellectExp += FormulaService.getDeservedExp();
                LOGGER.info("Intellect Exp -> " + intellectExp);
                break;

            case AttributeType.CREEP:
                break;

            case AttributeType.NONE:
                break;

            default:
                throw new IllegalArgumentException("There is no such Attribute");
        }
    }

    public static void addAttributeExp(String attributeName, Integer movePoints) {
        switch (attributeName) {
            case AttributeType.STRENGTH:
                strengthEpx += FormulaService.getDeservedExp(movePoints);
                LOGGER.info("Strength Epx -> " + strengthEpx);
                break;

            case AttributeType.AGILITY:
                agilityExp += FormulaService.getDeservedExp(movePoints);
                LOGGER.info("Agility Exp -> " + agilityExp);
                break;

            case AttributeType.INTELLECT:
                intellectExp += FormulaService.getDeservedExp(movePoints);
                LOGGER.info("Intellect Exp -> " + intellectExp);
                break;

            case AttributeType.CREEP:
                break;

            case AttributeType.NONE:
                break;

            default:
                throw new IllegalArgumentException("There is no such Attribute");
        }
    }

    // TODO: Bug -> to many levels, doesn't refresh exp value after updating
    public void updateProgresses() {
        CHAR_STATE.updateProgress(getDeservedExp(strengthEpx),
                CharacterStateService.getCharacter().getStrength(),
                AttributeType.STRENGTH);

        CHAR_STATE.updateProgress(getDeservedExp(agilityExp),
                CharacterStateService.getCharacter().getAgility(),
                AttributeType.AGILITY);

        CHAR_STATE.updateProgress(getDeservedExp(intellectExp),
                CharacterStateService.getCharacter().getIntellect(),
                AttributeType.INTELLECT);
    }

    private int getDeservedExp(int exp) {
        return CharacterStateService.isCharDead() ? exp * 4 : exp;
    }

    public Model getExpModel(Model model) {
        model.addAttribute("strengthEpx", getDeservedExp(strengthEpx));
        model.addAttribute("agilityExp", getDeservedExp(agilityExp));
        model.addAttribute("intellectExp", getDeservedExp(intellectExp));
        refresh();
        return model;
    }

    public void refresh() {
        strengthEpx = 0;
        agilityExp = 0;
        intellectExp = 0;
    }
}
