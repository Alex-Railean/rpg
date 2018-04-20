package com.endava.rpg.gp.game;

import com.endava.rpg.gp.adapters.CSVAdapter;
import com.endava.rpg.gp.state.SpellBookService;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DeployService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployService.class);

    private final PersistenceService PS;

    private final SpellBookService SPELLBOOK;

    private final CSVAdapter ADAPTER;

    @Autowired
    private DeployService(PersistenceService ps, SpellBookService sbs, CSVAdapter adapter) {
        this.PS = ps;
        this.SPELLBOOK = sbs;
        this.ADAPTER = adapter;
    }

    @PostConstruct
    private void injectAll() {
        injectAllEffects();
        injectAllSpells();
        injectAllCreeps();
        SPELLBOOK.getDefault();
    }

    private void injectAllEffects() {
        List<String[]> effects = new CSVReader("effects.csv").getData();
        if (PS.getAllEffects().size() < effects.size()) {
            effects.forEach(e -> PS.saveEffect(ADAPTER.toEffectCore(e)));
        }
    }

    private void injectAllSpells() {
        List<String[]> spells = new CSVReader("spells.csv").getData();
        if (PS.getAllSpells().size() < spells.size()) {
            spells.forEach(spell -> PS.saveSpell(ADAPTER.toSpell(spell)));
        }

        LOGGER.info("All Spells from CSV are saved in DB");
    }

    private void injectAllCreeps() {
        List<String[]> creeps = new CSVReader("creeps.csv").getData();
        if (PS.getAllCreeps().size() < creeps.size()) {
            creeps.forEach(creep -> PS.saveCreep(ADAPTER.toCreep(creep)));
        }

        LOGGER.info("All Creeps from CSV are saved in DB");
    }
}
