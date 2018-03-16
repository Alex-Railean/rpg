package com.endava.rpg.gp.talents.branches.strength;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.gp.talents.constants.linknames.BranchLinks;
import com.endava.rpg.gp.talents.constants.names.BranchNames;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TechnologiesBranch extends Branch {

    private final PersistenceService PS;

    @Autowired
    public TechnologiesBranch(PersistenceService ps) {
        super.setName(BranchNames.TECHNOLOGIES);
        super.setLinkName(BranchLinks.TECHNOLOGIES);
        super.setURL("");
        this.PS = ps;
    }

    @Override
    public void define(Character character) {
        talentsOfBranch.forEach(t -> t.define(character));
    }

    @Override
    public void create(Character character) {
        PS.saveTech(new Technologies().setCharacter(character));
    }
}
