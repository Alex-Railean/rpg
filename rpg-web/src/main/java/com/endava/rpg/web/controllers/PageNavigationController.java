package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.game.Refresher;
import com.endava.rpg.gp.services.state.CharacterStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.endava.rpg.web.controllers.utils.Views.START_PAGE;

@Controller
public class PageNavigationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageNavigationController.class);

    private final CharacterStateService CHAR_STATE_SERVICE;

    private final Refresher REFRESHER;

    @Autowired
    public PageNavigationController(CharacterStateService characterStateService, Refresher REFRESHER) {
        this.CHAR_STATE_SERVICE = characterStateService;
        this.REFRESHER = REFRESHER;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String toStartPage() {
        LOGGER.info("Redirect to Start Page");
        return START_PAGE;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String toLocation(String characterName) {
        REFRESHER.refresh();
        CHAR_STATE_SERVICE.defineCharacter(characterName);
        LOGGER.info("Character was Successfully Defined");
        return "redirect:/" + CHAR_STATE_SERVICE.getLocation();
    }

    @RequestMapping(value = "/continue", method = RequestMethod.GET)
    public String continueGame() {
        return "redirect:/" + CHAR_STATE_SERVICE.getLocation();
    }
}
