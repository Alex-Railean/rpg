package com.endava.rpg.web.controllers;

import com.endava.rpg.gp.battle.location.EnemyService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import static com.endava.rpg.gp.battle.location.constatnts.Location.HUNGRY_FOREST;

@Controller
public class LocationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    private final CharacterStateService CHAR_STATE;

    private final EnemyService ENEMY;

    private final BeastFactory BEAST_FACTORY;

    @Autowired
    public LocationController(CharacterStateService characterStateService, EnemyService enemyService, BeastFactory beastFactory) {
        this.CHAR_STATE = characterStateService;
        this.ENEMY = enemyService;
        this.BEAST_FACTORY = beastFactory;
    }

    @RequestMapping(value = Paths.HUNGRY_FOREST, method = RequestMethod.GET)
    public String toHungryForest(Model model) {
        model = CHAR_STATE.getCharacterModel(model);
        model = ENEMY.getRandomCreepGroup(model, HUNGRY_FOREST, CharacterStateService.getLvl(), BEAST_FACTORY);
        LOGGER.info("Hungry Forest");
        return HUNGRY_FOREST.VIEW;
    }

    // API

    @RequestMapping(value = Paths.API_ROOT + Paths.HUNGRY_FOREST, method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap<String, Object> toHungryForestApi(Model model) {
        model = CHAR_STATE.getCharacterModel(model);
        model = ENEMY.getRandomCreepGroup(model, HUNGRY_FOREST, CharacterStateService.getLvl(), BEAST_FACTORY);
        LOGGER.info("Hungry Forest");
        model.addAttribute("action", HUNGRY_FOREST.VIEW);

        return new HashMap<>(model.asMap());
    }
}
