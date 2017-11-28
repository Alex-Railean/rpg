package com.endava.rpg.gp.adapters;

import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVAdapter.class);

    private static DBService dbs;

    @Autowired
    CSVAdapter(DBService dbs){
        CSVAdapter.dbs = dbs;
    }

    public static Spell arrayToSpell(String[] csvSpell){
        LOGGER.info("CSV row is Converted to Spell Object");
        return new Spell().setSpellName(csvSpell[0])
                .setCooldown(Integer.parseInt(csvSpell[1]))
                .setEffectId(Integer.parseInt(csvSpell[2]))
                .setDmgCoefficient(Integer.parseInt(csvSpell[3]))
                .setSpellURL(csvSpell[4])
                .setCost(Integer.parseInt(csvSpell[5]))
                .setSpellType(csvSpell[6])
                .setAttribute(csvSpell[7]);
    }

    public static Creep arrayToCreep(String[] csvCreep){
        LOGGER.info("CSV row is Converted to Creep Object");
        return new Creep().setCreepName(csvCreep[0])
                .setHpFactor(Integer.parseInt(csvCreep[1]))
                .setHpRegeneration(Integer.parseInt(csvCreep[2]))
                .setMpFactor(Integer.parseInt(csvCreep[3]))
                .setMpRegeneration(Integer.parseInt(csvCreep[4]))
                .setEnergy(Integer.parseInt(csvCreep[5]))
                .setEnergyRegeneration(Integer.parseInt(csvCreep[6]))
                .setSpell_1(csvCreep[7] == null ? dbs.getSpellByName("Nospell") : dbs.getSpellByName(csvCreep[7]))
                .setSpell_2(csvCreep[8] == null ? dbs.getSpellByName("Nospell") : dbs.getSpellByName(csvCreep[8]))
                .setSpell_3(csvCreep[9] == null ? dbs.getSpellByName("Nospell") : dbs.getSpellByName(csvCreep[9]))
                .setCreepType(csvCreep[10])
                .setCreepLocation(csvCreep[11]);
    }
}
