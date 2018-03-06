package com.endava.rpg.gp.services.battle;

import com.endava.rpg.gp.statemodels.CreepState;
import com.endava.rpg.gp.services.state.CharacterStateService;
import com.endava.rpg.gp.adapters.CreepAdaptor;
import com.endava.rpg.gp.util.ProcessorUtil;
import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CreepLocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreepLocationService.class);

    private final PersistenceService PS;

    private final CharacterStateService CHAR_STATE_SERVICE;

    private final CreepAdaptor CREEP_ADAPTOR;

    private List<CreepState> creepGroup;

    private CreepState currentEnemy;

    @Autowired
    public CreepLocationService(PersistenceService ps, CharacterStateService characterState, CreepAdaptor creepAdaptor) {
        this.PS = ps;
        this.CHAR_STATE_SERVICE = characterState;
        this.CREEP_ADAPTOR = creepAdaptor;
    }

    public Model getRandomCreepGroup(Model model, String location) {
        List<Creep> creeps = PS.getCreepsFromLocation(location);
        List<CreepState> creepGroup = new ArrayList<>();
        int lvl = CHAR_STATE_SERVICE.getCharacterState().getCharacterLevel();
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

    public Model getCurrentEnemyAndGroup(Model model) {
        currentEnemy = creepGroup.get(creepGroup.size() == 1 ?
                0 : ThreadLocalRandom.current().nextInt(0, creepGroup.size()));
        model.addAttribute("currentEnemy", currentEnemy);
        model.addAttribute("creepsGroup", this.creepGroup);

        return model;
    }

    public boolean isCurrentEnemyDead() {
        return currentEnemy.getCurrentHp() <= 0;
    }

    public List<CreepState> getCreepGroup() {
        return creepGroup;
    }

    public CreepState getCurrentEnemy() {
        return currentEnemy;
    }

    public List<Spell> getEnemyProtectionSpells(CreepState creep) {
        return getEnemySpells(creep).stream()
                .filter(spell -> spell.getSpellType().equals("Protection"))
                .collect(Collectors.toList());
    }

    public List<Spell> getEnemyAttackSpells(CreepState creep) {
        return getEnemySpells(creep).stream()
                .filter(spell -> spell.getSpellType().equals("Attack"))
                .collect(Collectors.toList());
    }

    public List<Spell> getEnemySpells(CreepState creep) {
        return new ArrayList<>(Stream.of(creep.getSpell_1(), creep.getSpell_2(), creep.getSpell_3())
                .filter(spell -> !spell.getAttribute().equals("none"))
                .collect(Collectors.toList()));
    }
}
