package com.endava.rpg.gp.battle.location.factories;

import com.endava.rpg.gp.adapters.CreepAdaptor;
import com.endava.rpg.gp.battle.location.enums.Location;
import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.services.PersistenceService;

import java.util.ArrayList;
import java.util.List;

public abstract class CreepFactory {

    private final PersistenceService PS;

    private final CreepAdaptor CREEP_ADAPTOR;

    protected CreepFactory(PersistenceService ps, CreepAdaptor creep_adaptor) {
        this.PS = ps;
        this.CREEP_ADAPTOR = creep_adaptor;
    }

    public List<CreepState> createCreepGroup(Location location, int lvl) {
        List<Creep> creeps = PS.getCreepsFromLocation(location.NAME);
        creeps = filterCreep(creeps);
        List<CreepState> creepGroup = new ArrayList<>();
        int groupSize = ProcessorUtil.getRandomInt(1, lvl / 2 + 2);

        for (int i = 0; i < groupSize; i++) {
            int randomCreepIndex = ProcessorUtil.getRandomInt(0, creeps.size());
            creepGroup.add(i, CREEP_ADAPTOR.toCreepState(creeps.get(randomCreepIndex)));
        }

        return creepGroup;
    }

    protected abstract List<Creep> filterCreep(List<Creep> creeps);
}
