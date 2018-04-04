package com.endava.rpg.gp.battle.location;

import com.endava.rpg.gp.battle.location.enums.Location;
import com.endava.rpg.gp.battle.location.factories.CreepFactory;
import com.endava.rpg.gp.game.Refresher;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.gp.util.Refreshable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnemyService implements Refreshable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnemyService.class);

    private static List<CreepState> creepGroup;

    private static CreepState currentEnemy;

    private EnemyService() {
        Refresher.addRefreshable(this);
    }

    public static boolean isCurrentEnemyDead() {
        return currentEnemy.getHp().getCurrentValue() <= 0;
    }

    public static List<CreepState> getCreepGroup() {
        return creepGroup;
    }

    public static CreepState getCurrentEnemy() {
        return currentEnemy;
    }

    public Model getRandomCreepGroup(Model model, Location location, int lvl, CreepFactory creepFactory) {
        creepGroup = creepFactory.createCreepGroup(location, lvl);

        model.addAttribute("creepsGroup", creepGroup);
        LOGGER.info("Creep Group was Generated for location -> " + location);

        return model;
    }

    public Model getCurrentEnemyAndGroup(Model m) {
        currentEnemy = creepGroup.get(ProcessorUtil.getRandomInt(0, creepGroup.size() - 1));
        m.addAttribute("currentEnemy", currentEnemy);
        m.addAttribute("creepsGroup", creepGroup);

        return m;
    }

    @Override
    public void refresh() {
        creepGroup = new ArrayList<>();
        currentEnemy = new CreepState();
    }
}
