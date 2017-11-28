package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.services.battle.LocationCreepService;
import com.endava.rpg.gp.services.state.CharacterStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.endava.rpg.web.controllers.paths.Paths.*;

@Controller
public class LocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private CharacterStateService characterStateService;

    @Autowired
    private LocationCreepService locationCreepService;

    @RequestMapping(value = "/hungry-forest", method = RequestMethod.GET)
    public String toHungryForest(Model model) {
        model = characterStateService.getCharacterModel(model);
        model = locationCreepService.getRandomCreepGroupFromLocationAsModel(model, HUNGRY_FOREST);
        LOGGER.info("Redirect to Hungry Forest");
        return HUNGRY_FOREST;
    }
}
