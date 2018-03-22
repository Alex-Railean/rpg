package com.endava.rpg.gp.services.battle.location;

import com.endava.rpg.gp.services.battle.location.factories.CreepFactory;
import com.endava.rpg.gp.services.battle.location.enums.Location;
import com.endava.rpg.gp.services.game.Refresher;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.gp.util.Refreshable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service

public class LocationService implements Refreshable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private List<CreepState> creepGroup;

    private CreepState currentEnemy;

    @Autowired
    private LocationService(Refresher refresher) {
        refresher.addRefreshable(this);
    }

    public Model getRandomCreepGroup(Model model, Location location, int lvl, CreepFactory creepFactory) {
        this.creepGroup = creepFactory.createCreepGroup(location, lvl);

        model.addAttribute("creepsGroup", this.creepGroup);
        LOGGER.info("Creep Group was Generated for location -> " + location);

        return model;
    }

    public Model getCurrentEnemyAndGroup(Model m) {
        currentEnemy = creepGroup.get(ProcessorUtil.getRandomInt(0, creepGroup.size() - 1));
        m.addAttribute("currentEnemy", currentEnemy);
        m.addAttribute("creepsGroup", this.creepGroup);

        return m;
    }

    public boolean isCurrentEnemyDead() {
        return currentEnemy.getHp().getCurrentValue() <= 0;
    }

    public List<CreepState> getCreepGroup() {
        return creepGroup;
    }

    public CreepState getCurrentEnemy() {
        return currentEnemy;
    }

    @Override
    public void refresh() {
        creepGroup = new ArrayList<>();
        currentEnemy = new CreepState();
    }
}
