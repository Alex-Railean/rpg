package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.battle.BattleService;
import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.battle.LocationService;
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

    private final LocationService LOCATION;

    private final BattleService BATTLE;

    private final ExpService EXP;

    private final SpellService SPELL_SERVICE;

    @Autowired
    public ToBattleController(CharacterStateService characterStateService,
                              LocationService locationService,
                              BattleService battleService,
                              ExpService expService,
                              SpellService spellService) {
        this.CHAR_STATE_SERVICE = characterStateService;
        this.LOCATION = locationService;
        this.BATTLE = battleService;
        this.EXP = expService;
        this.SPELL_SERVICE = spellService;
    }

    @RequestMapping(value = "/toBattle", method = RequestMethod.GET)
    public String toBattle() {
        EXP.refresh();
        BATTLE.setBattleId(new Date().getTime());
        Long battleId = BATTLE.getBattleId();
        CHAR_STATE_SERVICE.setNewBattle(battleId);
        LOGGER.info("A New Battle has begun -> " + battleId);
        return "redirect:/" + Views.BATTLE + "/" + battleId;
    }

    @RequestMapping(value = Views.BATTLE + "/{battleId}", method = RequestMethod.GET)
    public String currentBattle(Model model, @PathVariable("battleId") Long battleId) {
        if (CHAR_STATE_SERVICE.getBattle().equals(battleId)) {
            if (!BATTLE.isEndOfBattle()) {
                LOGGER.info("The battle continues");
                model = CHAR_STATE_SERVICE.getCharacterModel(model);
                model = LOCATION.getCurrentEnemyAndGroup(model);
                return Views.BATTLE;
            }

            CHAR_STATE_SERVICE.setNewBattle(0L);
            return "redirect:/" + Views.EXP;
        }
        LOGGER.warn("Could not find battle with id: " + battleId);
        return "redirect:/" + CHAR_STATE_SERVICE.getLocation();
    }

    @RequestMapping(value = Views.BATTLE + "/use-spell/{actionBarId}", method = RequestMethod.GET)
    public String useSpell(RedirectAttributes redirectAttributes, @PathVariable("actionBarId") Integer actionBarId) {
        if (CHAR_STATE_SERVICE.getBattle() != null && CHAR_STATE_SERVICE.getBattle() != 0) {
            if (SPELL_SERVICE.doesHaveEnoughMana(actionBarId)) {
                BATTLE.makeATurn(actionBarId, LOCATION.getCurrentEnemy());
                return "redirect:/" + Views.BATTLE + "/" + BATTLE.getBattleId();
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "Not Enough Mana or Energy");
                return "redirect:/" + Views.BATTLE + "/" + BATTLE.getBattleId();
            }
        } else {
            return "redirect:/" + CHAR_STATE_SERVICE.getLocation();
        }
    }

    @RequestMapping(value = Views.BATTLE + "/wait", method = RequestMethod.GET)
    public String waitATurn() {
        BATTLE.waitATurn();
        return "redirect:/" + Views.BATTLE + "/" + BATTLE.getBattleId();
    }

    @RequestMapping(value = Views.EXP, method = RequestMethod.GET)
    public String getExp(Model model) {
            EXP.updateProgresses();
            model = CHAR_STATE_SERVICE.getCharacterModel(model);
            model = EXP.getExpModel(model);
            return Views.EXP;
    }
}
