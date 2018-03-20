package com.endava.rpg.gp.services.state;

import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpellBookService {

    private final PersistenceService PS;

    private final Map<String, List<Spell>> AVAILABLE_SPELLS = new HashMap<>();

    private List<Spell> defaultSpells;

    @Autowired
    public SpellBookService(PersistenceService PS) {
        this.PS = PS;
    }

    public List<Spell> getAvailableSpells(String characterName) {
        if (AVAILABLE_SPELLS.get(characterName) == null) {
            List<Spell> currentAvailableSpells = new ArrayList<>();
            currentAvailableSpells.addAll(defaultSpells);
            currentAvailableSpells.addAll(PS.getCharacterByName(characterName).getAvailableSpells());
            AVAILABLE_SPELLS.put(characterName, currentAvailableSpells);
            return currentAvailableSpells;
        } else {
            if (PS.getCharacterByName(characterName).getAvailableSpells().size() ==
                    AVAILABLE_SPELLS.get(characterName).size() - defaultSpells.size()) {
                return AVAILABLE_SPELLS.get(characterName);
            } else {
                AVAILABLE_SPELLS.remove(characterName);
                return getAvailableSpells(characterName);
            }
        }
    }

    public List<Spell> getDefault() {
        return defaultSpells = new ArrayList<>(
                Arrays.asList(PS.getSpellByName("Sword Attack"),
                        PS.getSpellByName("Bow Attack"),
                        PS.getSpellByName("Fire Ball")));
    }
}
