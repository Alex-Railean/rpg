package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.adapters.CreepStateAdaptor;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.services.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LocationCreepService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationCreepService.class);

    @Autowired
    private DBService dbs;

    @Autowired
    private CharacterStateService characterStateService;

    @Autowired
    private CreepStateAdaptor creepStateAdaptor;

    private List<CreepState> creepGroup;

    private CreepState currentEnemy;

    public Model getRandomCreepGroupFromLocationAsModel(Model model, String location) {
        List<Creep> allCreeps = dbs.getAllCreepsFromLocation(location);
        List<CreepState> creepGroup = new ArrayList<>();
        int groupSize = characterStateService.getCharacterState().getCharacterLevel() == 1
                ? 1 : ThreadLocalRandom.current()
                .nextInt(1, characterStateService.getCharacterState().getCharacterLevel());

        for (int i = 0; i < groupSize; i++) {
            int randomCreepIndex = allCreeps.size() == 1 ?
                    0 : ThreadLocalRandom.current().nextInt(0, allCreeps.size());
            creepGroup.add(i, creepStateAdaptor.toCreepState(allCreeps.get(randomCreepIndex)));
        }

        this.creepGroup = creepGroup;

        model.addAttribute("creepsGroup", this.creepGroup);

        LOGGER.info("Creep Group is Generated for location -> " + location);
        return model;
    }

    public Model getRandomCreepModelAndFullGroup(Model model) {
        currentEnemy = creepGroup.get(creepGroup.size() == 1 ?
                0 : ThreadLocalRandom.current().nextInt(0, creepGroup.size()));
        model.addAttribute("currentEnemy", currentEnemy);
        model.addAttribute("creepsGroup", this.creepGroup);

        return model;
    }

    public boolean currentEnemyIsDead(){
        return currentEnemy.getCurrentHp() <= 0;
    }

    public List<CreepState> getCreepGroup() {
        return creepGroup;
    }

    public void setCreepGroup(List<CreepState> creepGroup) {
        this.creepGroup = creepGroup;
    }

    public CreepState getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(CreepState currentEnemy) {
        this.currentEnemy = currentEnemy;
    }
}
