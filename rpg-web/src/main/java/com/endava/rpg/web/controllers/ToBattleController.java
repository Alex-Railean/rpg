package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.battle.LocationCreepService;
import com.endava.rpg.gp.services.battle.BattleService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

import static com.endava.rpg.web.controllers.paths.Paths.*;

@Controller
public class ToBattleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToBattleController.class);

    @Autowired
    private CharacterStateService characterStateService;

    @Autowired
    private LocationCreepService locationCreepService;

    @Autowired
    private BattleService battleService;

    @Autowired
    private ExpService expService;

    @Autowired
    private SpellService spellService;

    private Long battleId;

    @RequestMapping(value = "/toBattle", method = RequestMethod.GET)
    public String toBattle() {
        this.battleId = new Date().getTime();
        characterStateService.setNewBattle(this.battleId);
        LOGGER.info("A New Battle has begun -> " + this.battleId);
        return "redirect:/" + BATTLE + "/" + this.battleId;
    }

    @RequestMapping(value = BATTLE + "/{battleId}", method = RequestMethod.GET)
    public String currentBattle(Model model, @PathVariable("battleId") Long battleId) {
        if (characterStateService.getBattle().equals(battleId)) {
            if (!battleService.isEndOfBattle()) {
                LOGGER.info("The battle continues");
                model = characterStateService.getCharacterModel(model);
                model = locationCreepService.getRandomCreepModelAndFullGroup(model);
                return BATTLE;
            }
            return "redirect:/" + EXP;
        }
        LOGGER.warn("Could not find battle with id: " + battleId);
        return "redirect:/" + characterStateService.getLocation();
    }

    @RequestMapping(value = BATTLE + "/use-spell/{actionBarId}", method = RequestMethod.GET)
    public String useSpell(RedirectAttributes redirectAttributes, @PathVariable("actionBarId") Integer actionBarId) {
        if (spellService.manaIsEnough(actionBarId)) {
            battleService.makeATurn(actionBarId, locationCreepService.getCurrentEnemy());
            return "redirect:/" + BATTLE + "/" + this.battleId;
        }

        redirectAttributes.addFlashAttribute("warningMessage", "Not Enough Mana or Energy");

        return "redirect:/" + BATTLE + "/" + this.battleId;
    }

    @RequestMapping(value = BATTLE + "/wait", method = RequestMethod.GET)
    public String waitATurn() {
        battleService.waitATurn();
        return "redirect:/" + BATTLE + "/" + this.battleId;
    }

    @RequestMapping(value = EXP, method = RequestMethod.GET)
    public String getExp(Model model) {
        if (expService.thereIsExp()) {
            expService.updateProgresses();
            model = characterStateService.getCharacterModel(model);
            model = expService.getExpModel(model);
            return EXP;
        }
        return "redirect:/" + characterStateService.getLocation();
    }
}
