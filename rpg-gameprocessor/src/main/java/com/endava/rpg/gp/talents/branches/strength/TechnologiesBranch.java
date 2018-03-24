package com.endava.rpg.gp.talents.branches.strength;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.endava.rpg.gp.talents.constants.BranchAttribute.TECHNOLOGIES;

@Component
public class TechnologiesBranch extends Branch {

    private final PersistenceService PS;

    @Autowired
    public TechnologiesBranch(PersistenceService ps) {
        super.setName(TECHNOLOGIES.NAME);
        super.setLinkName(TECHNOLOGIES.LINK);
        super.setURL("/resources/img/technologies.jpg");
        this.PS = ps;
    }

    @Override
    public void define(Character character) {
        talentsOfBranch.forEach(t -> t.define(character));
    }

    @Override
    public void create(Character character) {
        PS.saveBranch(new Technologies().setCharacter(character));
    }
}
