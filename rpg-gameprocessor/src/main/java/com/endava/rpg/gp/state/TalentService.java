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
        BRANCHES.forEach(b -> b.createFor(character));
    }

    public List<Branch> getBranches() {
        return BRANCHES;
    }

    public void addBranch(Branch b) {
        BRANCHES.add(b);
    }

    public Branch getBranch(String branch) {
        return BRANCHES.stream()
                .filter(b -> b.getLinkName().equals(branch))
                .findFirst()
                .orElse(null);
    }

    // TODO: Investigate better approach
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

    private void updateBranch(String talent, int points, Technologies branch) {
        if (EXO_SPINE.LINK.equals(talent)) {
            if (branch.getExoSpine() + points <= branch.getExoSpineLimit()) {
                PS.updateBranch(branch.addExoSpine(points));
            }
        } else if (MUSCLE_STIMULANTS.LINK.equals(talent)) {
            if (branch.getMuscleStimulants() + points <= branch.getMuscleStimulantsLimit()) {
                PS.updateBranch(branch.addMuscleStimulants(points));
            }

        } else {
            throw new IllegalArgumentException("There is no such talent");
        }
    }

    private void updateBranch(String talent, int points, Aspects branch) {
        if (CURSED_BLADE.LINK.equals(talent)) {
            if (branch.getCursedBlade() + points <= branch.getCursedBladeLimit()) {
                PS.updateBranch(branch.addCursedBlade(points));
            }
        } else if (COURAGE.LINK.equals(talent)) {
            if (branch.getCourage() + points <= branch.getCourageLimit()) {
                PS.updateBranch(branch.addCourage(points));
            }
        } else if (PLAGUE_AURA.LINK.equals(talent)) {
            if (branch.getPlagueAura() + points <= branch.getPlagueAuraLimit()) {
                PS.updateBranch(branch.addPlagueAura(points));
            }
        } else {

            throw new IllegalArgumentException("There is no such talent");
        }
    }
}
