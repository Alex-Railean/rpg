package com.endava.rpg.gp.services.state;

import com.endava.rpg.persistence.models.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class ActionBarService {

    private final CharacterStateService CHAR_STATE;

    @Autowired
    private ActionBarService(CharacterStateService characterState) {
        this.CHAR_STATE = characterState;
    }

    public Map<Integer, Spell> getActionBarMap() {
        Map<Integer, Spell> actionBar = new TreeMap<>();
        actionBar.put(1, CHAR_STATE.getCharacterState().getSpell_1());
        actionBar.put(2, CHAR_STATE.getCharacterState().getSpell_2());
        actionBar.put(3, CHAR_STATE.getCharacterState().getSpell_3());
        actionBar.put(4, CHAR_STATE.getCharacterState().getSpell_4());
        actionBar.put(5, CHAR_STATE.getCharacterState().getSpell_5());
        actionBar.put(6, CHAR_STATE.getCharacterState().getSpell_6());
        actionBar.put(7, CHAR_STATE.getCharacterState().getSpell_7());
        actionBar.put(8, CHAR_STATE.getCharacterState().getSpell_8());
        actionBar.put(9, CHAR_STATE.getCharacterState().getSpell_9());
        actionBar.put(10, CHAR_STATE.getCharacterState().getSpell_10());
        actionBar.put(11, CHAR_STATE.getCharacterState().getSpell_11());
        actionBar.put(12, CHAR_STATE.getCharacterState().getSpell_12());

        return actionBar;
    }
}
