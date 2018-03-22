package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.battle.BattleService;
import com.endava.rpg.gp.services.battle.ExpService;
import com.endava.rpg.gp.services.battle.location.LocationService;
import com.endava.rpg.gp.services.battle.spells.SpellService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.util.CombatTextService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class BattleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleController.class);

    private final CharacterStateService CHAR_STATE;

    private final LocationService LOCATION;

    private final BattleService BATTLE;

    private final ExpService EXP;

    private final SpellService SPELL_SERVICE;

    @Autowired
    public BattleController(CharacterStateService characterStateService,
                            LocationService locationService,
                            BattleService battleService,
                            ExpService expService,
                            SpellService spellService) {
        this.CHAR_STATE = characterStateService;
        this.LOCATION = locationService;
        this.BATTLE = battleService;
        this.EXP = expService;
        this.SPELL_SERVICE = spellService;
    }

    @RequestMapping(value = Paths.BATTLE, method = RequestMethod.GET)
    public String toBattle() {
        EXP.refresh();
        BATTLE.setBattleId(new Date().getTime());
        Long battleId = BATTLE.getBattleId();
        CHAR_STATE.setNewBattle(battleId);
        LOGGER.info("A New Battle has begun -> " + battleId);
        return "redirect:" + Paths.BATTLE + "/" + battleId;
    }

    @RequestMapping(value = Paths.TO_BATTLE, method = RequestMethod.GET)
    public String currentBattle(Model model, @PathVariable("battleId") Long battleId) {
        if (CHAR_STATE.getBattle().equals(battleId)) {
            if (!BATTLE.isEndOfBattle()) {
                LOGGER.info("The Battle continues");
                model = CHAR_STATE.getCharacterModel(model);
                model = LOCATION.getCurrentEnemyAndGroup(model);
                model.addAttribute("combatText", CombatTextService.getCombatText());
                return Views.BATTLE;
            }

            LOGGER.info("End of Battle");

            CHAR_STATE.resetBattle();
            return "redirect:" + Paths.EXP;
        }
        LOGGER.warn("Could not find battle with id: " + battleId);
        return "redirect:/" + CHAR_STATE.getLocation();
    }

    @RequestMapping(value = Paths.BATTLE_USE_SPELL, method = RequestMethod.GET)
    public String useSpell(RedirectAttributes redirectAttributes, @PathVariable("actionBarId") Integer actionBarId) {
        if (CHAR_STATE.getBattle() == 0) {
            return "redirect:" + Paths.SPELLBOOK;
        } else if (actionBarId == 0) {
            return "redirect:" + Paths.BATTLE + "/" + BATTLE.getBattleId();
        }

        if (SPELL_SERVICE.doesHaveEnoughMana(actionBarId)) {
            BATTLE.makeATurn(actionBarId, LOCATION.getCurrentEnemy());
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "Not Enough Mana or Energy");
        }

        return "redirect:" + Paths.BATTLE + "/" + BATTLE.getBattleId();
    }

    @RequestMapping(value = Paths.BATTLE_WAIT, method = RequestMethod.GET)
    public String waitATurn() {
        if (CHAR_STATE.getBattle() == null || CHAR_STATE.getBattle() == 0) {
            return "redirect:" + Paths.SPELLBOOK;
        }

        BATTLE.waitATurn();
        return "redirect:" + Paths.BATTLE + "/" + BATTLE.getBattleId();
    }

    @RequestMapping(value = Paths.EXP, method = RequestMethod.GET)
    public String getExp(Model model) {
        EXP.updateProgresses();
        model = CHAR_STATE.getCharacterModel(model);
        model = EXP.getExpModel(model);
        CombatTextService.clearCombatText();
        return Views.EXP;
    }
}
