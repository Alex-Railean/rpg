package com.endava.rpg.gp.services.game;

import com.endava.rpg.gp.adapters.CSVAdapter;
import com.endava.rpg.persistence.services.DBService;
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

    @Autowired
    private DBService dbs;

    @PostConstruct
    private void injectAll(){
        injectAllSpells();
        injectAllCreeps();
    }


    private void injectAllSpells() {
        List<String[]> spells = new CSVReader("spells.csv").getAsListOfArrays();
        if (dbs.getAllSpells().size() < spells.size()) {
            for (String[] currentSpell : spells) {
                dbs.saveSpell(CSVAdapter.arrayToSpell(currentSpell));
            }
            LOGGER.info("All Spells from CSV are saved in DB");
        }
    }

    private void injectAllCreeps() {
        List<String[]> creeps = new CSVReader("creeps.csv").getAsListOfArrays();
        if (dbs.getAllCreeps().size() < creeps.size()) {
            for (String[] currentCreep : creeps) {
                dbs.saveCreep(CSVAdapter.arrayToCreep(currentCreep));
            }
            LOGGER.info("All Creeps from CSV are saved in DB");
        }
    }
}
