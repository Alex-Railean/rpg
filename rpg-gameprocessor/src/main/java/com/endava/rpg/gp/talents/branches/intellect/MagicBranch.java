package com.endava.rpg.gp.talents.branches.intellect;

import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.BranchEntity;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Magic;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.MAGIC;

@Component
public class MagicBranch extends Branch {

    protected MagicBranch(PersistenceService ps, TalentService talentService) {
        super(ps, talentService, MAGIC.NAME, MAGIC.LINK, MAGIC.URL);
    }

    @Override
    protected BranchEntity getBranchEntity(Character c) {
        return PS.getBranchOf(Magic.class, c);
    }

    @Override
    protected List<Spell> getBranchSpells() {
        return PS.getBranchSpells(MAGIC.NAME);
    }

    @Override
    public void createFor(Character character) {
        PS.saveBranch(new Magic().setCharacter(character));
    }
}
