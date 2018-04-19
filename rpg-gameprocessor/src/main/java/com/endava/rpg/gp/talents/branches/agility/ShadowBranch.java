package com.endava.rpg.gp.talents.branches.agility;

import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Shadow;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.SHADOW;

@Component
public class ShadowBranch extends Branch {

    @Autowired
    protected ShadowBranch(PersistenceService ps, TalentService talentService) {
        super(ps, talentService, SHADOW.NAME, SHADOW.LINK, SHADOW.URL);
    }

    @Override
    protected BranchEntity getBranchEntity(Character c) {
        return PS.getBranchOf(Shadow.class, c);
    }

    @Override
    protected List<Spell> getBranchSpells() {
        return PS.getBranchSpells(SHADOW.NAME);
    }

    @Override
    public void createFor(Character character) {
        PS.saveBranch(new Shadow().setCharacter(character));
    }
}
