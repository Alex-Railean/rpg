package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.battle.location.LocationService;
import com.endava.rpg.gp.battle.location.factories.BeastFactory;
import com.endava.rpg.gp.state.CharacterStateService;
import com.endava.rpg.web.controllers.utils.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.endava.rpg.gp.battle.location.enums.Location.HUNGRY_FOREST;

@Controller
public class LocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private final CharacterStateService CHAR_STATE;

    private final LocationService LOCATION;

    private final BeastFactory BEAST_FACTORY;

    @Autowired
    public LocationController(CharacterStateService characterStateService, LocationService locationService, BeastFactory beastFactory) {
        this.CHAR_STATE = characterStateService;
        this.LOCATION = locationService;
        this.BEAST_FACTORY = beastFactory;
    }

    @RequestMapping(value = Paths.HUNGRY_FOREST, method = RequestMethod.GET)
    public String toHungryForest(Model model) {
        model = CHAR_STATE.getCharacterModel(model);
        model = LOCATION.getRandomCreepGroup(model, HUNGRY_FOREST, CHAR_STATE.getCharacterLevel(), BEAST_FACTORY);
        LOGGER.info("Hungry Forest");
        return HUNGRY_FOREST.VIEW;
    }
}
