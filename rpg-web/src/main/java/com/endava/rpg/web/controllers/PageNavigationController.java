package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.state.CharacterStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.endava.rpg.web.controllers.paths.Paths.START_PAGE;

@Controller
public class PageNavigationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageNavigationController.class);

    @Autowired
    private CharacterStateService characterStateService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String toStartPage() {
        LOGGER.info("Redirect to Start Page");
        return START_PAGE;
    }

    @RequestMapping(value = "/continue", method = RequestMethod.GET)
    public String continueGame() {
        return "redirect:/" + characterStateService.getLocation();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String toLocation(String characterName) {
        characterStateService.defineCharacter(characterName);
        LOGGER.info("Character Successfully Defined");
        return "redirect:/" + characterStateService.getLocation();
    }
}
