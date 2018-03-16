package com.endava.rpg.gp.services.battle.location;

import com.endava.rpg.gp.adapters.CreepAdaptor;
import com.endava.rpg.gp.services.game.Refresher;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.gp.util.Refreshable;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.services.PersistenceService;
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

    private final PersistenceService PS;

    private final CharacterStateService CHAR_STATE;

    private final CreepAdaptor CREEP_ADAPTOR;

    private List<CreepState> creepGroup;

    private CreepState currentEnemy;

    @Autowired
    private LocationService(PersistenceService ps, CharacterStateService characterState, CreepAdaptor creepAdaptor, Refresher refresher) {
        refresher.addRefreshable(this);
        this.PS = ps;
        this.CHAR_STATE = characterState;
        this.CREEP_ADAPTOR = creepAdaptor;
    }

    public Model getRandomCreepGroup(Model model, String location) {
        List<Creep> creeps = PS.getCreepsFromLocation(location);
        List<CreepState> creepGroup = new ArrayList<>();
        int lvl = CHAR_STATE.getCharacterState().getCharacterLevel();
        int groupSize = ProcessorUtil.getRandomInt(1, lvl);

        for (int i = 0; i < groupSize; i++) {
            int randomCreepIndex = ProcessorUtil.getRandomInt(0, creeps.size() - 1);
            creepGroup.add(i, CREEP_ADAPTOR.toCreepState(creeps.get(randomCreepIndex)));
        }

        this.creepGroup = creepGroup;

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
