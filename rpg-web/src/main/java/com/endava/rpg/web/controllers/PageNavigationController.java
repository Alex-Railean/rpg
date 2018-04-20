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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageNavigationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageNavigationController.class);

    private final CharacterStateService CHAR_STATE;

    @Autowired
    public PageNavigationController(CharacterStateService characterStateService) {
        this.CHAR_STATE = characterStateService;
    }

    @RequestMapping(value = Paths.ROOT, method = RequestMethod.GET)
    public String toStartPage() {
        LOGGER.info("Start page");
        return Views.START_PAGE;
    }

    @RequestMapping(value = Paths.ROOT, method = RequestMethod.POST)
    public String toLocation(String characterName) {
        Refresher.refresh();
        CHAR_STATE.defineCharacter(characterName);
        LOGGER.info("Current Location");
        return "redirect:/" + CHAR_STATE.getLocation();
    }

    @RequestMapping(value = Paths.OUTSIDE, method = RequestMethod.GET)
    public String toOutside() {
        LOGGER.info("Current Location");
        return "redirect:/" + CHAR_STATE.getLocation();
    }

    // API

    @RequestMapping(value = Paths.API_ROOT, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String toLocationApi(String characterName) {
        return toLocation(characterName);
    }

    @RequestMapping(value = Paths.API_ROOT + Paths.OUTSIDE, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String toOutsideApi() {
        return toOutside();
    }
}
