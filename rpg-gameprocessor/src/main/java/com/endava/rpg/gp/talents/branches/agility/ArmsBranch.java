package com.endava.rpg.gp.talents.branches.agility;

import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.Arms;
import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.ARMS;

@Component
public class ArmsBranch extends Branch {

    @Autowired
    protected ArmsBranch(PersistenceService ps, TalentService talentService) {
        super(ps, talentService, ARMS.NAME, ARMS.LINK, ARMS.URL);
    }

    @Override
    protected BranchEntity getBranchEntity(Character c) {
        return PS.getBranchOf(Arms.class, c);
    }

    @Override
    protected List<Spell> getBranchSpells() {
        return PS.getBranchSpells(ARMS.NAME);
    }

    @Override
    public void create(Character character) {
        PS.saveBranch(new Arms().setCharacter(character));
    }
}
