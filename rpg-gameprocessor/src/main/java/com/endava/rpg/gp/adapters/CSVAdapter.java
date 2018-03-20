package com.endava.rpg.gp.adapters;

import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVAdapter.class);

    private static PersistenceService ps;

    @Autowired
    private CSVAdapter(PersistenceService ps) {
        CSVAdapter.ps = ps;
    }

    public static Spell toSpell(String[] csvSpell) {
        LOGGER.info("CSV row was Converted to Spell Object");
        return new Spell().setSpellName(csvSpell[0])
                .setCooldown(Integer.parseInt(csvSpell[1]))
                .setEffectId(Integer.parseInt(csvSpell[2]))
                .setCoefficient(Integer.parseInt(csvSpell[3]))
                .setSpellURL(csvSpell[4])
                .setCost(Integer.parseInt(csvSpell[5]))
                .setSchool(csvSpell[6])
                .setAttribute(csvSpell[7])
                .setSpellType(csvSpell[8]);
    }

    public static Creep toCreep(String[] csvCreep) {
        LOGGER.info("CSV row was Converted to Creep Object");
        return new Creep().setCreepName(csvCreep[0])
                .setHpFactor(Integer.parseInt(csvCreep[1]))
                .setHpRegeneration(Integer.parseInt(csvCreep[2]))
                .setMpFactor(Integer.parseInt(csvCreep[3]))
                .setMpRegeneration(Integer.parseInt(csvCreep[4]))
                .setEnergy(Integer.parseInt(csvCreep[5]))
                .setEnergyRegeneration(Integer.parseInt(csvCreep[6]))
                .setSpell_1(csvCreep[7].equals("null") ? ps.getSpellByName("No spell") : ps.getSpellByName(csvCreep[7]))
                .setSpell_2(csvCreep[8].equals("null") ? ps.getSpellByName("No spell") : ps.getSpellByName(csvCreep[8]))
                .setSpell_3(csvCreep[9].equals("null") ? ps.getSpellByName("No spell") : ps.getSpellByName(csvCreep[9]))
                .setCreepType(csvCreep[10])
                .setCreepLocation(csvCreep[11]);
    }
}
