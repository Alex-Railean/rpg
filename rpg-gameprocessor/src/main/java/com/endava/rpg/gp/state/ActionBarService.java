package com.endava.rpg.gp.state;

import com.endava.rpg.gp.battle.spells.description.DmgDescription;
import com.endava.rpg.gp.statemodels.State;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.services.PersistenceService;
import com.endava.rpg.persistence.services.utils.DescribedSpell;
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

    public Map<Integer, DescribedSpell> getActionBarMap() {
        Map<Integer, DescribedSpell> actionBar = new TreeMap<>();
        State cs = CHAR_STATE.getCharacterState();
        actionBar.put(1, new DmgDescription(cs.getSpell(0)));
        actionBar.put(2, new DmgDescription(cs.getSpell(1)));
        actionBar.put(3, new DmgDescription(cs.getSpell(2)));
        actionBar.put(4, new DmgDescription(cs.getSpell(3)));
        actionBar.put(5, new DmgDescription(cs.getSpell(4)));
        actionBar.put(6, new DmgDescription(cs.getSpell(5)));
        actionBar.put(7, new DmgDescription(cs.getSpell(6)));
        actionBar.put(8, new DmgDescription(cs.getSpell(7)));
        actionBar.put(9, new DmgDescription(cs.getSpell(8)));
        actionBar.put(10, new DmgDescription(cs.getSpell(9)));
        actionBar.put(11, new DmgDescription(cs.getSpell(10)));
        actionBar.put(12, new DmgDescription(cs.getSpell(11)));

        return actionBar;
    }

    public Character setActionBar(Character character, Map<Integer, DescribedSpell> abMap) {
        ActionBar ab = character.getActionBar();
        ab.setSpell_1(abMap.get(1).getSpell())
                .setSpell_2(abMap.get(2).getSpell())
                .setSpell_3(abMap.get(3).getSpell())
                .setSpell_4(abMap.get(4).getSpell())
                .setSpell_5(abMap.get(5).getSpell())
                .setSpell_6(abMap.get(6).getSpell())
                .setSpell_7(abMap.get(7).getSpell())
                .setSpell_8(abMap.get(8).getSpell())
                .setSpell_9(abMap.get(9).getSpell())
                .setSpell_10(abMap.get(10).getSpell())
                .setSpell_11(abMap.get(11).getSpell())
                .setSpell_12(abMap.get(12).getSpell());
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
