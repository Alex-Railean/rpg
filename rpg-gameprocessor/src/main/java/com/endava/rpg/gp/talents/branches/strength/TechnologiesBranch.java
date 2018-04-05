package com.endava.rpg.gp.talents.branches.strength;

import com.endava.rpg.gp.state.TalentService;
import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.TECHNOLOGIES;

@Component
public class TechnologiesBranch extends Branch {

    private final PersistenceService PS;

    @Autowired
    public TechnologiesBranch(PersistenceService ps, TalentService talentService) {
        talentService.getBranches().add(this);
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

    @Override
    public void addAvailableSpells(Character character) {
        Technologies tech = PS.getTechOf(character);
        List<Spell> techSpells = PS.getTechSpells();
        List<Spell> available = character.getAvailableSpells();

        techSpells = techSpells.stream()
                .filter(t -> t.getRequired() <= tech.getTotalPoints())
                .filter(t -> available.stream().noneMatch(a -> a.getSpellId().equals(t.getSpellId())))
                .collect(Collectors.toList());

        available.addAll(techSpells);

        PS.updateCharacter(character);
    }
}
