package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.state.ActionBarService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.services.state.SpellBookService;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
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
                .addAttribute("actionBar", ACTION_BAR.getActionBarMap())
                .addAttribute("spellBookContent", SPELL_BOOK.getAvailableSpells(CHARACTER_STATE.getCharacterState().getName()));
        LOGGER.info("Spell Book");
        return Views.SPELL_BOOK;
    }

    @RequestMapping(value = Paths.SPELLBOOK_SPELL, method = RequestMethod.POST)
    public String updateActionBar(@PathVariable("spell") String spell, Integer slot) {
        String charName = CHARACTER_STATE.getCharacterState().getName();
        Spell defaultSpell = PS.getSpellByName("Nospell");
        Character character = PS.getCharacterByName(charName);
        Map<Integer, Spell> ab = ACTION_BAR.getActionBarMap();
        Spell toMove = ab.get(slot);
        Spell newSpell = SPELL_BOOK.getAvailableSpells(charName)
                .stream()
                .filter(s -> s.getSpellName().equals(spell)).findFirst().orElse(defaultSpell);

        ab.keySet().forEach(k -> {
            if (ab.get(k).getSpellName().equals(spell)) {
                ab.put(k, toMove);
            }
        });

        ab.put(slot, newSpell);

        ACTION_BAR.setActionBar(character, ab);
        PS.updateCharacter(character);
        ACTION_BAR.reloadActionBar();

        LOGGER.info("ObjAction bar has been changed");

        return "redirect:" + Paths.SPELLBOOK;
    }

    @RequestMapping(value = Paths.SPELLBOOK_REMOVE, method = RequestMethod.GET)
    public String removeSpell(@PathVariable("slot") Integer slot) {
        String charName = CHARACTER_STATE.getCharacterState().getName();
        Spell defaultSpell = PS.getSpellByName("Nospell");
        Character character = PS.getCharacterByName(charName);
        Map<Integer, Spell> ab = ACTION_BAR.getActionBarMap();
        ab.put(slot, defaultSpell);

        ACTION_BAR.setActionBar(character, ab);
        PS.updateCharacter(character);
        ACTION_BAR.reloadActionBar();

        LOGGER.info("ObjAction bar has been changed");

        return "redirect:" + Paths.SPELLBOOK;
    }
}
