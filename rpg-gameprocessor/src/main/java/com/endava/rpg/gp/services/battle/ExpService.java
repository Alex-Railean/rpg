package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.util.Refreshable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service

public class ExpService implements Refreshable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpService.class);

    private final CharacterStateService CHAR_STATE;

    private final FormulaService FORMULA;

    private Integer strengthEpx = 0;

    private Integer agilityExp = 0;

    private Integer intelligenceExp = 0;

    @Autowired
    private ExpService(CharacterStateService characterStateService, FormulaService formulaService) {
        this.CHAR_STATE = characterStateService;
        this.FORMULA = formulaService;
    }

    public void addAttributeExp(String attributeName) {
        switch (attributeName) {
            case ("strength"):
                strengthEpx += FORMULA.getDeservedExp();
                LOGGER.info("Strength Epx -> " + strengthEpx);
                break;

            case ("agility"):
                agilityExp += FORMULA.getDeservedExp();
                LOGGER.info("Agility Exp -> " + agilityExp);
                break;

            case ("intelligence"):
                intelligenceExp += FORMULA.getDeservedExp();
                LOGGER.info("Intelligence Exp -> " + intelligenceExp);
                break;

            case ("creep"):
                break;

            case ("none"):
                break;

            default:
                throw new IllegalArgumentException("There is no such Attribute");
        }
    }

    public void updateProgresses() {
        CHAR_STATE.updateStrengthProgress(getDeservedExp(strengthEpx));
        CHAR_STATE.updateAgilityProgress(getDeservedExp(agilityExp));
        CHAR_STATE.updateIntelligenceProgress(getDeservedExp(intelligenceExp));
    }

    private int getDeservedExp(int exp) {
        return CHAR_STATE.isCharacterDead() ? exp / 4 : exp;
    }

    public Model getExpModel(Model model) {
        model.addAttribute("strengthEpx", getDeservedExp(strengthEpx));
        model.addAttribute("agilityExp", getDeservedExp(agilityExp));
        model.addAttribute("intelligenceExp", getDeservedExp(intelligenceExp));
        refresh();
        return model;
    }

    public void refresh() {
        this.strengthEpx = 0;
        this.agilityExp = 0;
        this.intelligenceExp = 0;
    }
}
