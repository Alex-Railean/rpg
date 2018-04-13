package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.battle.BattleService;
import com.endava.rpg.gp.battle.ExpService;
import com.endava.rpg.gp.battle.location.EnemyService;
import com.endava.rpg.gp.battle.spells.SpellService;
import com.endava.rpg.gp.combattext.CombatTextService;
import com.endava.rpg.gp.state.CharacterStateService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;

@Controller
public class BattleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleController.class);

    private final CharacterStateService CHAR_STATE;

    private final EnemyService ENEMY;

    private final BattleService BATTLE;

    private final ExpService EXP;

    private final SpellService SPELL_SERVICE;

    @Autowired
    public BattleController(CharacterStateService characterStateService,
                            EnemyService enemyService,
                            BattleService battleService,
                            ExpService expService,
                            SpellService spellService) {
        this.CHAR_STATE = characterStateService;
        this.ENEMY = enemyService;
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
                model = ENEMY.getCurrentEnemyAndGroup(model);
                model.addAttribute("combatText", CombatTextService.getCombatText());
                return Views.BATTLE;
            }

            LOGGER.info("End of Battle");

            CHAR_STATE.resetBattle();
            CharacterStateService.dispelEffects();
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

        if (SpellService.getSpellFromActionBar(actionBarId).getCurrentCooldown() == 0) {
            if (SPELL_SERVICE.doesHaveEnoughMana(actionBarId)) {
                BATTLE.makeATurn(actionBarId, EnemyService.getCurrentEnemy());
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "Not Enough Mana or Energy");
            }
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "This spell isn't ready");
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
        model = CHAR_STATE.getHeaderData(model);
        model = EXP.getExpModel(model);
        CombatTextService.clearCombatText();
        return Views.EXP;
    }

    // API
    @RequestMapping(value = Paths.API_ROOT + Paths.BATTLE, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String toBattleApi() {
        return toBattle();
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.TO_BATTLE, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> currentBattleApi(Model model, @PathVariable("battleId") Long battleId) {
        if (CHAR_STATE.getBattle().equals(battleId)) {
            if (!BATTLE.isEndOfBattle()) {
                model = CHAR_STATE.getCharacterModel(model);
                model = ENEMY.getCurrentEnemyAndGroup(model);
                model.addAttribute("combatText", CombatTextService.getCombatText());
                model.addAttribute("action", Paths.BATTLE);
                return new HashMap<>(model.asMap());
            }

            LOGGER.info("End of Battle");

            CHAR_STATE.resetBattle();
            CharacterStateService.dispelEffects();
            model.addAttribute("action", "redirect:" + Paths.EXP);
            return new HashMap<>(model.asMap());
        }
        LOGGER.warn("Could not find battle with id: " + battleId);
        model.addAttribute("action", "redirect:/" + CHAR_STATE.getLocation());
        return new HashMap<>(model.asMap());
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.BATTLE_USE_SPELL, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> useSpellApi(Model model, @PathVariable("actionBarId") Integer actionBarId) {
        if (CHAR_STATE.getBattle() == 0) {
            model.addAttribute("action", "redirect:" + Paths.SPELLBOOK);
            return new HashMap<>(model.asMap());
        } else if (actionBarId == 0) {
            model.addAttribute("action", "redirect:" + Paths.BATTLE + "/" + BATTLE.getBattleId());
            return new HashMap<>(model.asMap());
        }

        if (SpellService.getSpellFromActionBar(actionBarId).getCurrentCooldown() == 0) {
            if (SPELL_SERVICE.doesHaveEnoughMana(actionBarId)) {
                BATTLE.makeATurn(actionBarId, EnemyService.getCurrentEnemy());
            } else {
                model.addAttribute("warningMessage", "Not Enough Mana or Energy");
            }
        } else {
            model.addAttribute("warningMessage", "This spell isn't ready");
        }

        model.addAttribute("action", "redirect:" + Paths.BATTLE + "/" + BATTLE.getBattleId());
        return new HashMap<>(model.asMap());
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.BATTLE_WAIT, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String waitATurnApi() {
        return waitATurn();
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.EXP, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> getExpApi(Model model) {
        EXP.updateProgresses();
        model = CHAR_STATE.getHeaderData(model);
        model = EXP.getExpModel(model);
        CombatTextService.clearCombatText();
        model.addAttribute("action", Paths.EXP);
        return new HashMap<>(model.asMap());
    }
}
