package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.battle.BattleService;
import com.endava.rpg.gp.services.battle.CreepLocationService;
import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellService;
import com.endava.rpg.web.controllers.utils.Views;
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

@Controller
public class ToBattleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToBattleController.class);

    private final CharacterStateService CHAR_STATE_SERVICE;

    private final CreepLocationService CREEP_LOCATION;

    private final BattleService BATTLE;

    private final ExpService EXP;

    private final SpellService SPELL_SERVICE;

    private Long battleId;

    @Autowired
    public ToBattleController(CharacterStateService characterStateService,
                              CreepLocationService creepLocationService,
                              BattleService battleService,
                              ExpService expService,
                              SpellService spellService) {
        this.CHAR_STATE_SERVICE = characterStateService;
        this.CREEP_LOCATION = creepLocationService;
        this.BATTLE = battleService;
        this.EXP = expService;
        this.SPELL_SERVICE = spellService;
    }

    @RequestMapping(value = "/toBattle", method = RequestMethod.GET)
    public String toBattle() {
        this.battleId = new Date().getTime();
        CHAR_STATE_SERVICE.setNewBattle(this.battleId);
        LOGGER.info("A New Battle has begun -> " + this.battleId);
        return "redirect:/" + Views.BATTLE + "/" + this.battleId;
    }

    @RequestMapping(value = Views.BATTLE + "/{battleId}", method = RequestMethod.GET)
    public String currentBattle(Model model, @PathVariable("battleId") Long battleId) {
        if (CHAR_STATE_SERVICE.getBattle().equals(battleId)) {
            if (!BATTLE.isEndOfBattle()) {
                LOGGER.info("The battle continues");
                model = CHAR_STATE_SERVICE.getCharacterModel(model);
                model = CREEP_LOCATION.getCurrentEnemyAndGroup(model);
                return Views.BATTLE;
            }
            return "redirect:/" + Views.EXP;
        }
        LOGGER.warn("Could not find battle with id: " + battleId);
        return "redirect:/" + CHAR_STATE_SERVICE.getLocation();
    }

    @RequestMapping(value = Views.BATTLE + "/use-spell/{actionBarId}", method = RequestMethod.GET)
    public String useSpell(RedirectAttributes redirectAttributes, @PathVariable("actionBarId") Integer actionBarId) {
        if (SPELL_SERVICE.doesHaveEnoughMana(actionBarId)) {
            BATTLE.makeATurn(actionBarId, CREEP_LOCATION.getCurrentEnemy());
            return "redirect:/" + Views.BATTLE + "/" + this.battleId;
        }

        redirectAttributes.addFlashAttribute("warningMessage", "Not Enough Mana or Energy");

        return "redirect:/" + Views.BATTLE + "/" + this.battleId;
    }

    @RequestMapping(value = Views.BATTLE + "/wait", method = RequestMethod.GET)
    public String waitATurn() {
        BATTLE.waitATurn();
        return "redirect:/" + Views.BATTLE + "/" + this.battleId;
    }

    @RequestMapping(value = Views.EXP, method = RequestMethod.GET)
    public String getExp(Model model) {
        if (EXP.isThereExp()) {
            EXP.updateProgresses();
            model = CHAR_STATE_SERVICE.getCharacterModel(model);
            model = EXP.getExpModel(model);
            return Views.EXP;
        }
        return "redirect:/" + CHAR_STATE_SERVICE.getLocation();
    }
}
