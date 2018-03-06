package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.services.game.FormulaService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ExpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpService.class);

    private final CharacterStateService CHAR_STATE_SERVICE;

    private final FormulaService FORMULA;

    private Integer strengthEpx = 0;

    private Integer agilityExp = 0;

    private Integer intelligenceExp = 0;

    @Autowired
    public ExpService(CharacterStateService characterStateService, FormulaService formulaService) {
        this.CHAR_STATE_SERVICE = characterStateService;
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

    public boolean isThereExp() {
        return !(this.strengthEpx.equals(0) && this.agilityExp.equals(0) && this.intelligenceExp.equals(0));
    }

    public void updateProgresses() {
        CHAR_STATE_SERVICE.updateStrengthProgress(strengthEpx);
        CHAR_STATE_SERVICE.updateAgilityProgress(agilityExp);
        CHAR_STATE_SERVICE.updateIntelligenceProgress(intelligenceExp);
    }

    public Model getExpModel(Model model) {
        model.addAttribute("strengthEpx", strengthEpx);
        model.addAttribute("agilityExp", agilityExp);
        model.addAttribute("intelligenceExp", intelligenceExp);
        flushExp();
        return model;
    }

    private void flushExp() {
        this.strengthEpx = 0;
        this.agilityExp = 0;
        this.intelligenceExp = 0;
    }
}
