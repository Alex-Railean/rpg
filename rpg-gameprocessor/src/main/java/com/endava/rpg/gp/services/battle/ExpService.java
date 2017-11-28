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

    @Autowired
    private CharacterStateService characterStateService;

    @Autowired
    private FormulaService formulaService;

    private Integer strengthEpx = 0;

    private Integer agilityExp = 0;

    private Integer intelligenceExp = 0;

    public void addAttributeExp(String attributeName) {
        switch (attributeName) {
            case ("strength"):
                strengthEpx += formulaService.getDeservedExp();
                LOGGER.info("Strength Epx -> " + strengthEpx);
                break;

            case ("agility"):
                agilityExp += formulaService.getDeservedExp();
                LOGGER.info("Agility Exp -> " + agilityExp);
                break;

            case ("intelligence"):
                intelligenceExp += formulaService.getDeservedExp();
                LOGGER.info("Intelligence Exp -> " + intelligenceExp);
                break;

            default:
                throw new IllegalArgumentException("There is no such Attribute");
        }
    }

    public boolean thereIsExp(){
        return !(this.strengthEpx.equals(0) && this.agilityExp.equals(0) && this.intelligenceExp.equals(0));
    }

    public void updateProgresses() {
        characterStateService.updateStrengthProgress(strengthEpx);
        characterStateService.updateAgilityProgress(agilityExp);
        characterStateService.updateIntelligenceProgress(intelligenceExp);
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

    public Integer getStrengthEpx() {
        return strengthEpx;
    }

    public ExpService setStrengthEpx(Integer strengthEpx) {
        this.strengthEpx = strengthEpx;
        return this;
    }

    public Integer getAgilityExp() {
        return agilityExp;
    }

    public ExpService setAgilityExp(Integer agilityExp) {
        this.agilityExp = agilityExp;
        return this;
    }

    public Integer getIntelligenceExp() {
        return intelligenceExp;
    }

    public ExpService setIntelligenceExp(Integer intelligenceExp) {
        this.intelligenceExp = intelligenceExp;
        return this;
    }
}
