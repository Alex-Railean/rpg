package com.endava.rpg.gp.talents.branches.strength;

import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.Aspects;
import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.ASPECTS;

@Component
public class AspectsBranch extends Branch {

    @Autowired
    public AspectsBranch(PersistenceService ps, TalentService talentService) {
        super(ps, talentService, ASPECTS.NAME, ASPECTS.LINK, ASPECTS.URL);
    }

    @Override
    public void create(Character character) {
        PS.saveBranch(new Aspects().setCharacter(character));
    }

    @Override
    protected BranchEntity getBranchEntity(Character c) {
        return PS.getBranchOf(Aspects.class, c);
    }

    @Override
    protected List<Spell> getBranchSpells() {
        return PS.getBranchSpells(ASPECTS.NAME);
    }
}
