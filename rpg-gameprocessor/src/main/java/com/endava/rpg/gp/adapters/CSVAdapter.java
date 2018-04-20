package com.endava.rpg.gp.adapters;

import com.endava.rpg.persistence.models.Creep;
import com.endava.rpg.persistence.models.EffectCore;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVAdapter.class);

    private final PersistenceService PS;

    @Autowired
    private CSVAdapter(PersistenceService ps) {
        this.PS = ps;
    }

    public Spell toSpell(String[] csvSpell) {
        LOGGER.info("CSV row was converted to Spell Object");
        return new Spell().setSpellName(csvSpell[0])
                .setCooldown(Integer.parseInt(csvSpell[1]))
                .setEffectCore(PS.getEffect(csvSpell[2]))
                .setCoefficient(Integer.parseInt(csvSpell[3]))
                .setSpellURL(csvSpell[4])
                .setCost(Integer.parseInt(csvSpell[5]))
                .setSchool(csvSpell[6])
                .setAttribute(csvSpell[7])
                .setSpellType(csvSpell[8])
                .setRequired(Integer.parseInt(csvSpell[9]))
                .setBranch(csvSpell[10]);
    }

    public Creep toCreep(String[] csvCreep) {
        LOGGER.info("CSV row was converted to Creep Object");
        return new Creep().setCreepName(csvCreep[0])
                .setHpFactor(Integer.parseInt(csvCreep[1]))
                .setHpRegeneration(Integer.parseInt(csvCreep[2]))
                .setMpFactor(Integer.parseInt(csvCreep[3]))
                .setMpRegeneration(Integer.parseInt(csvCreep[4]))
                .setEnergy(Integer.parseInt(csvCreep[5]))
                .setEnergyRegeneration(Integer.parseInt(csvCreep[6]))
                .setSpell_1(csvCreep[7].equals("null") ? PS.getSpellByName("No spell") : PS.getSpellByName(csvCreep[7]))
                .setSpell_2(csvCreep[8].equals("null") ? PS.getSpellByName("No spell") : PS.getSpellByName(csvCreep[8]))
                .setSpell_3(csvCreep[9].equals("null") ? PS.getSpellByName("No spell") : PS.getSpellByName(csvCreep[9]))
                .setCreepType(csvCreep[10])
                .setCreepLocation(csvCreep[11]);
    }

    public EffectCore toEffectCore(String[] csvEffect) {
        LOGGER.info("CSV row was converted to EffectCore Object");
        return new EffectCore().setName(csvEffect[0])
                .setDuration(Integer.parseInt(csvEffect[1]))
                .setURL(csvEffect[2])
                .setDescription(csvEffect[3]);
    }
}
