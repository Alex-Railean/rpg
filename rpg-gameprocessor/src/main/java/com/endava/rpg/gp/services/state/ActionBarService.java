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
        actionBar.put(1, CHAR_STATE.getCharacterState().getSpell(0));
        actionBar.put(2, CHAR_STATE.getCharacterState().getSpell(1));
        actionBar.put(3, CHAR_STATE.getCharacterState().getSpell(2));
        actionBar.put(4, CHAR_STATE.getCharacterState().getSpell(3));
        actionBar.put(5, CHAR_STATE.getCharacterState().getSpell(4));
        actionBar.put(6, CHAR_STATE.getCharacterState().getSpell(5));
        actionBar.put(7, CHAR_STATE.getCharacterState().getSpell(6));
        actionBar.put(8, CHAR_STATE.getCharacterState().getSpell(7));
        actionBar.put(9, CHAR_STATE.getCharacterState().getSpell(8));
        actionBar.put(10, CHAR_STATE.getCharacterState().getSpell(9));
        actionBar.put(11, CHAR_STATE.getCharacterState().getSpell(10));
        actionBar.put(12, CHAR_STATE.getCharacterState().getSpell(11));

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
        CHAR_STATE.getCharacterState().setSpell(0, actionBar.getSpell_1())
                .setSpell(1, actionBar.getSpell_2())
                .setSpell(2, actionBar.getSpell_3())
                .setSpell(3, actionBar.getSpell_4())
                .setSpell(4, actionBar.getSpell_5())
                .setSpell(5, actionBar.getSpell_6())
                .setSpell(6, actionBar.getSpell_7())
                .setSpell(7, actionBar.getSpell_8())
                .setSpell(8, actionBar.getSpell_9())
                .setSpell(9, actionBar.getSpell_10())
                .setSpell(10, actionBar.getSpell_11())
                .setSpell(11, actionBar.getSpell_12());
    }
}
