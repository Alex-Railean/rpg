package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.battle.spells.description.DescriptionService;
import com.endava.rpg.gp.state.ActionBarService;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.gp.state.SpellBookService;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.DescribedSpell;
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

import java.util.HashMap;
import java.util.Map;

@Controller
public class SpellBookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpellBookController.class);

    private final CharacterStateService CHARACTER_STATE;

    private final SpellBookService SPELL_BOOK;

    private final PersistenceService PS;

    private final ActionBarService ACTION_BAR;

    @Autowired
    public SpellBookController(CharacterStateService characterStateService,
                               SpellBookService spellBookService,
                               PersistenceService persistenceService, ActionBarService ACTION_BAR) {
        this.CHARACTER_STATE = characterStateService;
        this.SPELL_BOOK = spellBookService;
        this.PS = persistenceService;
        this.ACTION_BAR = ACTION_BAR;
    }

    @RequestMapping(value = Paths.SPELLBOOK, method = RequestMethod.GET)
    public String toSpellBook(Model model) {
        model = CHARACTER_STATE.getHeaderData(model)
                .addAttribute("actionBar", ActionBarService.getActionBarMap())
                .addAttribute("spellBookContent",
                        DescriptionService.addFull(SPELL_BOOK.getAvailableSpells()));
        LOGGER.info("Spell Book");
        return Views.SPELLBOOK;
    }

    @RequestMapping(value = Paths.SPELLBOOK_SPELL, method = RequestMethod.POST)
    public String updateActionBar(@PathVariable("spell") String spell, Integer slot) {
        String charName = CharacterStateService.getCharacter().getName();
        Character character = PS.getCharacterByName(charName);

        Spell noSpell = PS.getSpellByName("No spell");

        Map<Integer, DescribedSpell> ab = ActionBarService.getActionBarMap();
        Spell toMove = ab.get(slot).getSpell();

        DescribedSpell newSpell = SPELL_BOOK.getAvailableSpells()
                .stream()
                .filter(s -> s.getSpell().getSpellName().equals(spell)).findFirst().orElse(noSpell);

        ab.keySet().forEach(k -> {
            if (ab.get(k).getSpell().getSpellName().equals(spell)) {
                ab.put(k, toMove);
            }
        });

        ab.put(slot, newSpell);

        ACTION_BAR.setActionBar(character, ab);
        PS.updateCharacter(character);
        ACTION_BAR.reloadActionBar();

        LOGGER.info("Action bar has been changed");

        return "redirect:" + Paths.SPELLBOOK;
    }

    @RequestMapping(value = Paths.SPELLBOOK_REMOVE, method = RequestMethod.GET)
    public String removeSpell(@PathVariable("slot") Integer slot) {
        String charName = CharacterStateService.getCharacter().getName();
        Spell defaultSpell = PS.getSpellByName("No spell");
        Character character = PS.getCharacterByName(charName);
        Map<Integer, DescribedSpell> ab = ActionBarService.getActionBarMap();
        ab.put(slot, defaultSpell);

        ACTION_BAR.setActionBar(character, ab);
        PS.updateCharacter(character);
        ACTION_BAR.reloadActionBar();

        LOGGER.info("Action bar has been changed");

        return "redirect:" + Paths.SPELLBOOK;
    }

    // API

    @RequestMapping(value = Paths.API_ROOT + Paths.SPELLBOOK, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> toSpellBookApi(Model model) {
        model = CHARACTER_STATE.getHeaderData(model)
                .addAttribute("actionBar", ActionBarService.getActionBarMap())
                .addAttribute("spellBookContent",
                        DescriptionService.addFull(SPELL_BOOK.getAvailableSpells()));
        LOGGER.info("Spell Book");
        model.addAttribute("action", Paths.SPELLBOOK);

        return new HashMap<>(model.asMap());
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.SPELLBOOK_SPELL, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String updateActionBarApi(@PathVariable("spell") String spell, Integer slot) {
        return updateActionBar(spell, slot);
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.SPELLBOOK_REMOVE, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String removeSpellApi(@PathVariable("slot") Integer slot) {
        return removeSpell(slot);
    }
}
