package com.endava.rpg.gp.state;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.gp.talents.branches.strength.TechnologiesBranch;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.endava.rpg.gp.talents.constants.BranchAttribute.TECHNOLOGIES;
import static com.endava.rpg.gp.talents.constants.TalentAttribute.EXO_SPINE;
import static com.endava.rpg.gp.talents.constants.TalentAttribute.MUSCLE_STIMULANTS;

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

    public void updateCharacterTalent(Character character, String branch, String talent, int points) {
        if (branch.equals(TECHNOLOGIES.LINK)) {
            Technologies technologies = character.getTechnologies();
            updateBranch(talent, points, technologies);
        } else {

            throw new IllegalArgumentException("There is no such talent branch");
        }
    }

    private void updateBranch(String talent, int points, Technologies technologies) {
        if (EXO_SPINE.LINK.equals(talent)) {
            if (technologies.getExoSpine() + points <= technologies.getExoSpineLimit()) {
                PS.updateBranch(technologies.setExoSpine(technologies.getExoSpine() + points));
            }
        } else if (MUSCLE_STIMULANTS.LINK.equals(talent)) {
            if (technologies.getMuscleStimulants() + points <= technologies.getMuscleStimulantsLimit()) {
                PS.updateBranch(technologies.setMuscleStimulants(technologies.getMuscleStimulants() + points));
            }

        } else {

            throw new IllegalArgumentException("There is no such talent");
        }
    }
}
