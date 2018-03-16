package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.gp.talents.branches.strength.TechnologiesBranch;
import com.endava.rpg.gp.talents.constants.linknames.BranchLinks;
import com.endava.rpg.gp.talents.constants.linknames.TalentLinks;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TalentService {
    private final List<Branch> BRANCHES = new ArrayList<>();

    private final PersistenceService PS;

    @Autowired
    public TalentService(TechnologiesBranch tech, PersistenceService ps) {
        this.PS = ps;
        this.BRANCHES.add(tech);
    }

    public void affect() {
        BRANCHES.forEach(Branch::affect);
    }

    public void defineTalents(Character character) {
        BRANCHES.forEach(b -> b.define(character));
    }

    public void createAll(Character character) {
        BRANCHES.forEach(b -> b.create(character));
    }

    public List<Branch> getBranches() {
        return BRANCHES;
    }

    public Branch getBranch(String branch) {
        return BRANCHES.stream().filter(b -> b.getName().equalsIgnoreCase(branch)).findFirst().orElse(null);
    }

    public boolean updateCharacterTalent(Character character, String branch, String talent, int points) {
        switch (branch) {
            case BranchLinks.TECHNOLOGIES:
                Technologies technologies = character.getTechnologies();
                return updateBranch(talent, points, technologies);
            default:
                throw new IllegalArgumentException("There is no such talent branch");
        }
    }

    private boolean updateBranch(String talent, int points, Technologies technologies) {
        switch (talent) {
            case TalentLinks.EXO_SPINE:
                if (technologies.getExoSpine() + points <= technologies.getExoSpineLimit()) {
                    PS.updateTech(technologies.setExoSpine(technologies.getExoSpine() + points));
                    return true;
                } else {
                    return false;
                }
            case TalentLinks.MUSCLE_STIMULANTS:
                if (technologies.getMuscleStimulants() + points <= technologies.getMuscleStimulantsLimit()) {
                    PS.updateTech(technologies.setMuscleStimulants(technologies.getMuscleStimulants() + points));
                    return true;
                } else {
                    return false;
                }
            default:
                throw new IllegalArgumentException("There is no such talent");
        }
    }
}