package com.endava.rpg.gp.services.game;

import com.endava.rpg.gp.adapters.CSVAdapter;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PostConstructService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostConstructService.class);

    private final PersistenceService PS;

    @Autowired
    private PostConstructService(PersistenceService ps) {
        this.PS = ps;
    }

    @PostConstruct
    private void injectAll() {
        injectAllSpells();
        injectAllCreeps();
    }

    private void injectAllSpells() {
        List<String[]> spells = new CSVReader("spells.csv").getAsListOfArrays();
        if (PS.getAllSpells().size() < spells.size()) {

            spells.forEach(spell -> PS.saveSpell(CSVAdapter.arrayToSpell(spell)));

            LOGGER.info("All Spells from CSV are saved in DB");
        }
    }

    private void injectAllCreeps() {
        List<String[]> creeps = new CSVReader("creeps.csv").getAsListOfArrays();
        if (PS.getAllCreeps().size() < creeps.size()) {

            creeps.forEach(creep -> PS.saveCreep(CSVAdapter.arrayToCreep(creep)));

            LOGGER.info("All Creeps from CSV are saved in DB");
        }
    }
}
