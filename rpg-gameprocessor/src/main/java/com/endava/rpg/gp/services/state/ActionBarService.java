package com.endava.rpg.gp.services.state;

import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class ActionBarService {

    private final PersistenceService PS;

    private final CharacterStateService CHAR_STATE;

    @Autowired
    private ActionBarService(PersistenceService ps, CharacterStateService characterState) {
        this.PS = ps;
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

    public Character setActionBar(Character character, Map<Integer, Spell> abMap) {
        ActionBar ab = character.getActionBar();
        ab.setSpell_1(abMap.get(1))
                .setSpell_2(abMap.get(2))
                .setSpell_3(abMap.get(3))
                .setSpell_4(abMap.get(4))
                .setSpell_5(abMap.get(5))
                .setSpell_6(abMap.get(6))
                .setSpell_7(abMap.get(7))
                .setSpell_8(abMap.get(8))
                .setSpell_9(abMap.get(9))
                .setSpell_10(abMap.get(10))
                .setSpell_11(abMap.get(11))
                .setSpell_12(abMap.get(12));
        return character;
    }

    public void reloadActionBar() {
        Character character = PS.getCharacterByName(CHAR_STATE.getCharacterState().getName());
        ActionBar actionBar = character.getActionBar();
        CHAR_STATE.getCharacterState().setSpell_4(actionBar.getSpell_4())
                .setSpell_5(actionBar.getSpell_5())
                .setSpell_6(actionBar.getSpell_6())
                .setSpell_7(actionBar.getSpell_7())
                .setSpell_8(actionBar.getSpell_8())
                .setSpell_9(actionBar.getSpell_9())
                .setSpell_10(actionBar.getSpell_10())
                .setSpell_11(actionBar.getSpell_11())
                .setSpell_12(actionBar.getSpell_12())
                .setSpell_1(actionBar.getSpell_1())
                .setSpell_2(actionBar.getSpell_2())
                .setSpell_3(actionBar.getSpell_3());
    }
}
