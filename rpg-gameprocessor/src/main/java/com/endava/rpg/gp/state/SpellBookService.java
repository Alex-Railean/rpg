package com.endava.rpg.gp.state;

import com.endava.rpg.gp.battle.spells.constants.DefaultSpells;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.DescribedSpell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpellBookService {

    private final PersistenceService PS;

    private final Map<String, List<DescribedSpell>> AVAILABLE_SPELLS = new HashMap<>();

    private List<DescribedSpell> defaultSpells;

    @Autowired
    public SpellBookService(PersistenceService PS) {
        this.PS = PS;
    }

    public List<DescribedSpell> getAvailableSpells() {
        String characterName = CharacterStateService.getCharName();
        if (AVAILABLE_SPELLS.get(characterName) == null) {
            List<DescribedSpell> currentAvailableSpells = new ArrayList<>();
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
                return getAvailableSpells();
            }
        }
    }

    public List<DescribedSpell> getDefault() {
        return defaultSpells = new ArrayList<>(
                Arrays.asList(PS.getSpellByName(DefaultSpells.SWORD_ATTACK),
                        PS.getSpellByName(DefaultSpells.BOW_ATTACK),
                        PS.getSpellByName(DefaultSpells.FIRE_BALL)));
    }
}
