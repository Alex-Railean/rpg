package com.endava.rpg.gp.battle.location.factories;

import com.endava.rpg.gp.adapters.CreepAdaptor;
import com.endava.rpg.gp.battle.location.enums.CreepType;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HumanoidFactory extends CreepFactory {

    @Autowired
    private HumanoidFactory(PersistenceService ps, CreepAdaptor creepAdaptor) {
        super(ps, creepAdaptor);
    }

    @Override
    protected List<Creep> filterCreep(List<Creep> creeps) {
        return creeps.stream()
                .filter(c -> c.getCreepType().equalsIgnoreCase(CreepType.HUMANOID))
                .collect(Collectors.toList());
    }
}
