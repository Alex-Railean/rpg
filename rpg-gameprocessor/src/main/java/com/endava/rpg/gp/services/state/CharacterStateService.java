package com.endava.rpg.gp.services.state;

import com.endava.rpg.gp.statemodels.CurrentCharacter;
import com.endava.rpg.persistence.models.ActionBar;
import com.endava.rpg.persistence.models.Progress;
import com.endava.rpg.persistence.models.Spell;
import com.endava.rpg.persistence.services.DBService;
import com.endava.rpg.persistence.models.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class CharacterStateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterStateService.class);

    @Autowired
    private DBService dbs;

    @Autowired
    private CurrentCharacter currentCharacter;

    @Autowired
    private ActionBarService actionBarService;

    @PostConstruct
    public Character saveTestCharacter() {
        if ((dbs.getCharacterByName("Admin") == null)) {
            LOGGER.info("Test Admin User is Created");
            return dbs.saveCharacter(new Character(
                    "Admin",
                    new Progress(),
                    new ActionBar()));

        }
        return dbs.getCharacterByName("Admin");
    }

    public CurrentCharacter defineCharacter(String characterName) {
        if (dbs.getCharacterByName(characterName) != null) {
            return currentCharacter.defineCharacter(characterName);
        }
        return currentCharacter.defineNewCharacter(characterName);

    }

    public CurrentCharacter getCharacterState() {
        return currentCharacter;
    }

    public CurrentCharacter setNewBattle(Long battleId) {
        LOGGER.info("New Battle is Defined");
        return currentCharacter.setCurrentBattle(battleId);
    }

    public Long getBattle() {
        return currentCharacter.getCurrentBattle();
    }

    public CurrentCharacter setNewLocation(String location) {
        LOGGER.info("New Location -> " + location);
        dbs.updateCharacter(dbs.getCharacterByName(currentCharacter.getCharacterName()).setLocation(location));
        return currentCharacter.setLocation(location);
    }

    public String getLocation() {
        return currentCharacter.getLocation();
    }

    public Model getCharacterModel(Model model) {
        Map<Integer, Spell> actionBar = actionBarService.getActionBarMap();
        model.addAttribute("characterName", currentCharacter.getCharacterName());
        model.addAttribute("characterLevel", currentCharacter.getCharacterLevel());
        model.addAttribute("hp", currentCharacter.getHp());
        model.addAttribute("currentHp", currentCharacter.getCurrentHp());
        model.addAttribute("mp", currentCharacter.getMp());
        model.addAttribute("currentMp", currentCharacter.getCurrentMp());
        model.addAttribute("energy", currentCharacter.getEnergy());
        model.addAttribute("currentEnergy", currentCharacter.getCurrentEnergy());
        model.addAttribute("actionBar", actionBar);
        model.addAttribute("strengthLevel", currentCharacter.getStrengthProgressLevel());
        model.addAttribute("strength", currentCharacter.getStrengthProgress());
        model.addAttribute("strengthNextLevel", currentCharacter.getStrengthNextLevel());
        model.addAttribute("agilityLevel", currentCharacter.getAgilityProgressLevel());
        model.addAttribute("agility", currentCharacter.getAgilityProgress());
        model.addAttribute("agilityNextLevel", currentCharacter.getAgilityNextLevel());
        model.addAttribute("intelligenceLevel", currentCharacter.getIntelligenceProgressLevel());
        model.addAttribute("intelligence", currentCharacter.getIntelligenceProgress());
        model.addAttribute("intelligenceNextLevel", currentCharacter.getIntelligenceNextLevel());

        return model;
    }

    public boolean characterIsDead() {
        return currentCharacter.getCurrentHp() <= 0;
    }

    public void updateStrengthProgress(Integer strengthEpx) {
        Progress progress = dbs.getCharacterByName(currentCharacter.getCharacterName()).getProgressId();
        if (currentCharacter.getStrengthProgress() + strengthEpx < currentCharacter.getStrengthNextLevel()) {
            dbs.updateProgress(progress.setStrengthProgress(progress.getStrengthProgress() + strengthEpx));
            currentCharacter.setStrengthProgress(progress.getStrengthProgress());
        } else {
            dbs.updateProgress(progress
                    .setStrengthProgressLevel(progress.getStrengthProgressLevel() + 1)
                    .setStrengthProgress(progress.getStrengthProgress() + strengthEpx - currentCharacter.getStrengthNextLevel()));
            defineCharacter(currentCharacter.getCharacterName());
            LOGGER.info("Level Up!");
        }
    }

    public void updateAgilityProgress(Integer agilityExp) {
        Progress progress = dbs.getCharacterByName(currentCharacter.getCharacterName()).getProgressId();
        if (currentCharacter.getAgilityProgress() + agilityExp < currentCharacter.getAgilityNextLevel()) {
            dbs.updateProgress(progress.setAgilityProgress(progress.getAgilityProgress() + agilityExp));
            currentCharacter.setAgilityProgress(progress.getAgilityProgress());
        } else {
            dbs.updateProgress(progress
                    .setAgilityProgressLevel(progress.getAgilityProgressLevel() + 1)
                    .setAgilityProgress(progress.getAgilityProgress() + agilityExp - currentCharacter.getAgilityNextLevel()));
            defineCharacter(currentCharacter.getCharacterName());
            LOGGER.info("Level Up!");
        }
    }

    public void updateIntelligenceProgress(Integer intelligenceExp) {
        Progress progress = dbs.getCharacterByName(currentCharacter.getCharacterName()).getProgressId();
        if (currentCharacter.getIntelligenceProgress() + intelligenceExp < currentCharacter.getIntelligenceNextLevel()) {
            dbs.updateProgress(progress.setIntelligenceProgress(progress.getIntelligenceProgress() + intelligenceExp));
            currentCharacter.setIntelligenceProgress(progress.getIntelligenceProgress());
        } else {
            dbs.updateProgress(progress
                    .setIntelligenceProgressLevel(progress.getIntelligenceProgressLevel() + 1)
                    .setIntelligenceProgress(progress.getIntelligenceProgress() + intelligenceExp - currentCharacter.getIntelligenceNextLevel()));
            defineCharacter(currentCharacter.getCharacterName());
            LOGGER.info("Level Up!");
        }

    }
}
