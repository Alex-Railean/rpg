package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.web.controllers.utils.Paths;
import com.endava.rpg.web.controllers.utils.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageNavigationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageNavigationController.class);

    private final CharacterStateService CHAR_STATE;

    private final Refresher REFRESHER;

    @Autowired
    public PageNavigationController(CharacterStateService characterStateService, Refresher refresher) {
        this.CHAR_STATE = characterStateService;
        this.REFRESHER = refresher;
    }

    @RequestMapping(value = Paths.ROOT, method = RequestMethod.GET)
    public String toStartPage() {
        LOGGER.info("Start page");
        return Views.START_PAGE;
    }

    @RequestMapping(value = Paths.ROOT, method = RequestMethod.POST)
    public String toLocation(String characterName) {
        REFRESHER.refresh();
        CHAR_STATE.defineCharacter(characterName);
        LOGGER.info("Current Location");
        return "redirect:/" + CHAR_STATE.getLocation();
    }

    @RequestMapping(value = Paths.CONTINUE, method = RequestMethod.GET)
    public String continueGame() {
        LOGGER.info("Current Location");
        return "redirect:/" + CHAR_STATE.getLocation();
    }

    @RequestMapping(value = Paths.OUTSIDE, method = RequestMethod.GET)
    public String toOutside() {
        LOGGER.info("Current Location");
        return "redirect:/" + CHAR_STATE.getLocation();
    }
}
