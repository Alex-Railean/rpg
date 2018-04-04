package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.web.controllers.utils.Paths;
import com.endava.rpg.web.controllers.utils.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TalentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TalentController.class);

    private final TalentService TALENT;

    private final CharacterStateService CHARACTER_STATE;

    private final PersistenceService PS;

    @Autowired
    public TalentController(TalentService talent, CharacterStateService characterState, PersistenceService ps) {
        this.TALENT = talent;
        this.CHARACTER_STATE = characterState;
        this.PS = ps;
    }

    @RequestMapping(value = Paths.TALENTS, method = RequestMethod.GET)
    public String toTalents(Model model) {
        model = CHARACTER_STATE.getHeaderData(model)
                .addAttribute("branches", TALENT.getBranches());
        LOGGER.info("Talent page");
        return Views.TALENTS;
    }

    @RequestMapping(value = Paths.TALENTS_BRANCH, method = RequestMethod.GET)
    public String toTalent(Model model, @PathVariable("branch") String branch) {
        model = CHARACTER_STATE.getHeaderData(model)
                .addAttribute("freePoints", CharacterStateService.getCharacter().getFreePoints())
                .addAttribute("branch", TALENT.getBranch(branch));
        LOGGER.info("Branch page");
        return Views.BRANCH;
    }

    @RequestMapping(value = Paths.TALENTS_UPDATE, method = RequestMethod.POST)
    public String addTalents(@PathVariable("branch") String branch, @PathVariable("talent") String talent, int points) {

        if (points == 0 || CharacterStateService.getCharacter().getFreePoints() - points < 0) {
            return "redirect:" + Paths.TALENTS_BRANCH;
        }

        Character character = PS.getCharacterByName(CharacterStateService.getCharName());
        TALENT.updateCharacterTalent(character, branch, talent, points);
        character.removeFreePoints(points);
        PS.updateCharacter(character);
        CHARACTER_STATE.refreshCharacter();
        LOGGER.info("Talents have been updated");

        return "redirect:" + Paths.TALENTS_BRANCH;
    }
}
