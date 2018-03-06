package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.battle.CreepLocationService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.endava.rpg.web.controllers.utils.Views.*;

@Controller
public class LocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private final CharacterStateService CHAR_STATE_SERVICE;

    private final CreepLocationService CREEP_LOCATION;

    @Autowired
    public LocationController(CharacterStateService characterStateService, CreepLocationService creepLocationService) {
        this.CHAR_STATE_SERVICE = characterStateService;
        this.CREEP_LOCATION = creepLocationService;
    }

    @RequestMapping(value = "/hungry-forest", method = RequestMethod.GET)
    public String toHungryForest(Model model) {
        model = CHAR_STATE_SERVICE.getCharacterModel(model);
        model = CREEP_LOCATION.getRandomCreepGroup(model, HUNGRY_FOREST);
        LOGGER.info("Redirect to Hungry Forest");
        return HUNGRY_FOREST;
    }
}
