package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.statemodels.CurrentCharacter;
import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class ActionBarService {

    @Autowired
    private CurrentCharacter currentCharacter;

    public Map<Integer, Spell> getActionBarMap(){
        Map<Integer, Spell> actionBar = new TreeMap<>();
        actionBar.put(1, currentCharacter.getSpell_1());
        actionBar.put(2, currentCharacter.getSpell_2());
        actionBar.put(3, currentCharacter.getSpell_3());
        actionBar.put(4, currentCharacter.getSpell_4());
        actionBar.put(5, currentCharacter.getSpell_5());
        actionBar.put(6, currentCharacter.getSpell_6());
        actionBar.put(7, currentCharacter.getSpell_7());
        actionBar.put(8, currentCharacter.getSpell_8());
        actionBar.put(9, currentCharacter.getSpell_9());
        actionBar.put(10, currentCharacter.getSpell_10());
        actionBar.put(11, currentCharacter.getSpell_11());
        actionBar.put(12, currentCharacter.getSpell_12());

        return actionBar;
    }
}
