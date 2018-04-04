package com.endava.rpg.gp.state;

import com.endava.rpg.gp.battle.spells.constants.SpellType;
import com.endava.rpg.gp.battle.spells.description.DescrService;
import com.endava.rpg.gp.battle.spells.description.spells.EffectDescription;
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

    @Autowired
    private ActionBarService(PersistenceService ps) {
        this.PS = ps;
    }

    public static Map<Integer, DescribedSpell> getActionBarMap() {
        Map<Integer, DescribedSpell> actionBar = new TreeMap<>();
        State cs = CharacterStateService.getCharacter();
        actionBar.put(1, addDescription(cs.getSpell(0)));
        actionBar.put(2, addDescription(cs.getSpell(1)));
        actionBar.put(3, addDescription(cs.getSpell(2)));
        actionBar.put(4, addDescription(cs.getSpell(3)));
        actionBar.put(5, addDescription(cs.getSpell(4)));
        actionBar.put(6, addDescription(cs.getSpell(5)));
        actionBar.put(7, addDescription(cs.getSpell(6)));
        actionBar.put(8, addDescription(cs.getSpell(7)));
        actionBar.put(9, addDescription(cs.getSpell(8)));
        actionBar.put(10, addDescription(cs.getSpell(9)));
        actionBar.put(11, addDescription(cs.getSpell(10)));
        actionBar.put(12, addDescription(cs.getSpell(11)));

        return actionBar;
    }

    private static DescribedSpell addDescription(DescribedSpell s) {
        return s.getSpell().getSpellType().equals(SpellType.ATTACK) ?
                DescrService.addFull(s) :
                new EffectDescription(s);
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
        Character character = PS.getCharacterByName(CharacterStateService.getCharacter().getName());
        ActionBar actionBar = character.getActionBar();
        CharacterStateService.getCharacter().setSpell(0, actionBar.getSpell_1())
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
