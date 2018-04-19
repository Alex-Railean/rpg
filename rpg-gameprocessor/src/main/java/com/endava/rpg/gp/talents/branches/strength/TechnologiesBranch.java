package com.endava.rpg.gp.talents.branches.strength;

import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.TECHNOLOGIES;

@Component
public class TechnologiesBranch extends Branch {

    @Autowired
    public TechnologiesBranch(PersistenceService ps, TalentService talentService) {
        super(ps, talentService, TECHNOLOGIES.NAME, TECHNOLOGIES.LINK, TECHNOLOGIES.URL);
    }

    @Override
    public void createFor(Character c) {
        PS.saveBranch(new Technologies().setCharacter(c));
    }

    @Override
    protected BranchEntity getBranchEntity(Character c) {
        return PS.getBranchOf(Technologies.class, c);
    }

    @Override
    protected List<Spell> getBranchSpells() {
        return PS.getBranchSpells(TECHNOLOGIES.NAME);
    }
}
