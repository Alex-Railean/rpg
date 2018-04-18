package com.endava.rpg.gp.state;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.persistence.models.Aspects;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.ASPECTS;
import static com.endava.rpg.persistence.services.utils.constants.BranchAttribute.TECHNOLOGIES;
import static com.endava.rpg.persistence.services.utils.constants.TalentAttribute.*;

@Service
public class TalentService {
    private static final List<Branch> BRANCHES = new ArrayList<>();

    private final PersistenceService PS;

    @Autowired
    public TalentService(PersistenceService ps) {
        this.PS = ps;
    }

    public static void affect() {
        BRANCHES.forEach(Branch::affect);
    }

    public static void defineTalents(Character character) {
        BRANCHES.forEach(b -> b.define(character));
        BRANCHES.forEach(b -> b.addAvailableSpells(character));
    }

    public static void createAll(Character character) {
        BRANCHES.forEach(b -> b.create(character));
    }

    public List<Branch> getBranches() {
        return BRANCHES;
    }

    public void addBranch(Branch b) {
        BRANCHES.add(b);
    }

    public Branch getBranch(String branch) {
        return BRANCHES.stream().filter(b -> b.getName().equalsIgnoreCase(branch)).findFirst().orElse(null);
    }

    public void updateCharacterTalent(Character character, String branch, String talent, int points) {
        if (branch.equals(TECHNOLOGIES.LINK)) {
            Technologies technologies = character.getTechnologies();
            updateBranch(talent, points, technologies);
        } else if (branch.equals(ASPECTS.LINK)) {
            Aspects aspects = character.getAspects();
            updateBranch(talent, points, aspects);
        } else {

            throw new IllegalArgumentException("There is no such talent branch");
        }
    }

    private void updateBranch(String talent, int points, Technologies branchEntity) {
        if (EXO_SPINE.LINK.equals(talent)) {
            if (branchEntity.getExoSpine() + points <= branchEntity.getExoSpineLimit()) {
                PS.updateBranch(branchEntity.addExoSpine(points));
            }
        } else if (MUSCLE_STIMULANTS.LINK.equals(talent)) {
            if (branchEntity.getMuscleStimulants() + points <= branchEntity.getMuscleStimulantsLimit()) {
                PS.updateBranch(branchEntity.addMuscleStimulants(points));
            }

        } else {

            throw new IllegalArgumentException("There is no such talent");
        }
    }

    private void updateBranch(String talent, int points, Aspects branchEntity) {
        if (CURSED_BLADE.LINK.equals(talent)) {
            if (branchEntity.getCursedBlade() + points <= branchEntity.getCursedBladeLimit()) {
                PS.updateBranch(branchEntity.addCursedBlade(points));
            }
        } else if (COURAGE.LINK.equals(talent)) {
            if (branchEntity.getCourage() + points <= branchEntity.getCourageLimit()) {
                PS.updateBranch(branchEntity.addCourage(points));
            }
        } else {

            throw new IllegalArgumentException("There is no such talent");
        }
    }
}
