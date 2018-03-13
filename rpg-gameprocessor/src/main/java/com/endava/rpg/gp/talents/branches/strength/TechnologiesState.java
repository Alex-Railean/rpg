package com.endava.rpg.gp.talents.branches.strength;

import com.endava.rpg.gp.talents.branches.Branch;
import com.endava.rpg.gp.talents.linknames.BranchLinks;
import com.endava.rpg.gp.talents.names.BranchNames;
import com.endava.rpg.gp.talents.talents.technologies.ExoSpine;
import com.endava.rpg.gp.talents.talents.technologies.MuscleStimulants;
import com.endava.rpg.persistence.models.Character;
import com.endava.rpg.persistence.models.Technologies;
import com.endava.rpg.persistence.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TechnologiesState extends Branch {

    private final PersistenceService PS;

    private final MuscleStimulants MUSCLE_STIMULANTS;

    private final ExoSpine EXO_SPINE;

    @Autowired
    public TechnologiesState(PersistenceService ps, MuscleStimulants muscleStimulants, ExoSpine exoSpine) {
        super.setName(BranchNames.TECHNOLOGIES);
        super.setLinkName(BranchLinks.TECHNOLOGIES);
        super.setURL("");
        this.PS = ps;
        this.MUSCLE_STIMULANTS = muscleStimulants;
        super.allTalents.add(muscleStimulants);
        this.EXO_SPINE = exoSpine;
        super.allTalents.add(exoSpine);
    }

    @Override
    public void define(Character character) {
        MUSCLE_STIMULANTS.setPoints(character.getTechnologies().getMuscleStimulants());
        MUSCLE_STIMULANTS.setLimit(character.getTechnologies().getMuscleStimulantsLimit());
        EXO_SPINE.setPoints(character.getTechnologies().getExoSpine());
        EXO_SPINE.setLimit(character.getTechnologies().getExoSpineLimit());
    }

    @Override
    public void create(Character character) {
        PS.saveTech(new Technologies().setCharacter(character));
    }
}
